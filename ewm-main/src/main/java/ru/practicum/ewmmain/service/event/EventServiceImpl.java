package ru.practicum.ewmmain.service.event;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.client.ViewStats;
import ru.practicum.ewmmain.client.StatService;
import ru.practicum.ewmmain.dto.events.*;
import ru.practicum.ewmmain.exception.CategoryNotFoundException;
import ru.practicum.ewmmain.exception.EventNotFoundException;
import ru.practicum.ewmmain.exception.ForbiddenException;
import ru.practicum.ewmmain.exception.UserNotFoundException;
import ru.practicum.ewmmain.mapper.DateTimeMapper;
import ru.practicum.ewmmain.mapper.EventMapper;
import ru.practicum.ewmmain.model.*;
import ru.practicum.ewmmain.model.constant.EventState;
import ru.practicum.ewmmain.model.constant.RequestStatus;
import ru.practicum.ewmmain.repository.CategoryRepository;
import ru.practicum.ewmmain.repository.EventRepository;
import ru.practicum.ewmmain.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    @Value("${events.path}")
    private String eventsPath;
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final StatService statisticClient;


    @Override
    public EventFullDto addEvent(Long userId, NewEventsDto newEventDto) {
        User initiator = findUser(userId);
        Categories category = findCategory(newEventDto.getCategory());
        Event event = EventMapper.toEvent(newEventDto);
        event.setCategory(category);
        event.setInitiator(initiator);
        eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        eventFullDto.setConfirmedRequests(0);
        eventFullDto.setViews(0);
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getEvents(Long userId, int from, int size) {
        findUser(userId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        List<Event> events = eventRepository.findByInitiator_Id(userId, pageable);
        List<EventShortDto> eventShortDtoList =
                events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
        setViews(eventShortDtoList);
        setConfirmedRequests(eventShortDtoList);
        return eventShortDtoList;
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequestDto updateEventRequestDto) {
        findUser(userId);
        Event event = findEvent(updateEventRequestDto.getEventId());
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Пользователь не может обновить событие");
        }
        if (event.getState() != EventState.CANCELED && event.getState() != EventState.PENDING) {
            throw new ForbiddenException("Состояние события для обновления должно быть  CANCELED или PENDING");
        }
        if (Duration.between(LocalDateTime.now(), event.getEventDate()).toHours() < 2) {
            throw new ValidationException("До событий осталось меньше двух часов");
        }

        updateEvent(event, updateEventRequestDto);

        if (updateEventRequestDto.getEventDate() != null) {
            LocalDateTime eventDate = DateTimeMapper.toLocalDateTime(updateEventRequestDto.getEventDate());
            if (Duration.between(LocalDateTime.now(), eventDate).toHours() < 2) {
                throw new ValidationException("До события обновления осталось менее двух часов");
            }
            event.setEventDate(eventDate);
        }
        event.setState(EventState.PENDING);
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequestDto requestDto) {
        Event event = findEvent(eventId);
        updateEvent(event, requestDto);
        if (requestDto.getEventDate() != null) {
            LocalDateTime eventDate = DateTimeMapper.toLocalDateTime(requestDto.getEventDate());
            event.setEventDate(eventDate);
        }
        if (requestDto.getLocation() != null) {
            event.setLocationLat(requestDto.getLocation().getLat());
            event.setLocationLon(requestDto.getLocation().getLon());
        }
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        findUser(userId);
        Event event = findEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new EventNotFoundException(eventId);
        }
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto getEvent(Long eventId) {
        Event event = findEvent(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new EventNotFoundException(eventId);
        }
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        findUser(userId);
        Event event = findEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Пользователь не может обновить событие");
        }
        if (event.getState() != EventState.PENDING) {
            throw new ForbiddenException("Могут быть изменены только ожидающие или отмененные события");
        }
        event.setState(EventState.CANCELED);
        eventRepository.save(event);
        return getEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsWithFilter(String text, List<Long> categories, Boolean paid,
                                                   String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                                   EventSort sort, Integer from, Integer size) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QEvent qEvent = QEvent.event;
        QRequest qRequest = QRequest.request;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (text != null) {
            booleanBuilder.and((qEvent.annotation.likeIgnoreCase(text))
                    .or(qEvent.description.likeIgnoreCase(text)));
        }
        if (categories != null) {
            booleanBuilder.and(qEvent.category.id.in(categories));
        }
        if (paid != null) {
            booleanBuilder.and(qEvent.paid.eq(paid));
        }
        if (rangeStart == null && rangeEnd == null) {
            booleanBuilder.and(qEvent.eventDate.after(LocalDateTime.now()));
        } else {
            if (rangeStart != null) {
                booleanBuilder.and(qEvent.eventDate.after(DateTimeMapper.toLocalDateTime(rangeStart)));
            }
            if (rangeEnd != null) {
                booleanBuilder.and(qEvent.eventDate.before(DateTimeMapper.toLocalDateTime(rangeEnd)));
            }
        }
        booleanBuilder.and(qEvent.state.eq(EventState.PUBLISHED));
        booleanBuilder.and((qRequest.status.eq(RequestStatus.CONFIRMED).or(qRequest.isNull())));

        List<EventShortDto> eventsDto;
        if (sort == EventSort.EVENT_DATE) {
            List<Tuple> events = getEventsWithEventsDateSort(onlyAvailable, queryFactory, qEvent, qRequest,
                    booleanBuilder, from, size);
            eventsDto = mapListEventsToEventShortDto(events, qEvent, qRequest);
            setViews(eventsDto);
        } else {
            eventsDto = getEventsWithViewsSort(onlyAvailable, queryFactory, qEvent, qRequest, booleanBuilder, from, size);
        }
        return eventsDto;
    }


    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = findEvent(eventId);
        if (Duration.between(LocalDateTime.now(), event.getEventDate()).toHours() < 1) {
            throw new ForbiddenException("До мероприятия осталось меньше часа");
        }
        if (event.getState() != EventState.PENDING) {
            throw new ForbiddenException("Событие должно находиться в состоянии PENDING");
        }
        event.setState(EventState.PUBLISHED);
        eventRepository.save(event);
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = findEvent(eventId);
        if (event.getState() == EventState.PUBLISHED) {
            throw new ForbiddenException("Событие не должно находиться в состоянии PUBLISHED");
        }
        event.setState(EventState.CANCELED);
        eventRepository.save(event);
        return getEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        QEvent qEvent = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (users != null) {
            booleanBuilder.and(qEvent.initiator.id.in(users));
        }
        if (states != null) {
            booleanBuilder.and(qEvent.state.in(states.stream().map(EventState::valueOf).collect(Collectors.toList())));
        }
        if (categories != null) {
            booleanBuilder.and(qEvent.category.id.in(categories));
        }
        if (rangeStart != null) {
            booleanBuilder.and(qEvent.eventDate.after(DateTimeMapper.toLocalDateTime(rangeStart)));
        }
        if (rangeEnd != null) {
            booleanBuilder.and(qEvent.eventDate.before(DateTimeMapper.toLocalDateTime(rangeEnd)));
        }
        Iterable<Event> events = eventRepository.findAll(booleanBuilder, pageable);
        List<EventFullDto> eventFullDtoList = StreamSupport
                .stream(events.spliterator(), false)
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
        setViews(eventFullDtoList);
        setConfirmedRequests(eventFullDtoList);
        return eventFullDtoList;
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    private Categories findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private Event findEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    private void setViews(List<? extends EventDto> eventDtoList) {
        List<String> urisList = eventDtoList
                .stream()
                .map(e -> String.format(eventsPath + "%d", e.getId()))
                .collect(Collectors.toList());

        List<ViewStats> viewStatsList = statisticClient.getStatsByUris(urisList);

        for (EventDto eventsDto : eventDtoList) {
            eventsDto.setViews(
                    viewStatsList.stream()
                            .filter(v -> v.getUri().equals(
                                    String.format(eventsPath + "%d", eventsDto.getId())))
                            .findFirst()
                            .orElse(new ViewStats())
                            .getHits());
        }
    }

    private void setConfirmedRequests(List<? extends EventDto> eventDtoList) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QRequest qRequest = QRequest.request;

        List<Long> idList = eventDtoList
                .stream()
                .map(EventDto::getId)
                .collect(Collectors.toList());

        List<Tuple> tupleList = queryFactory.select(qRequest.event.id, qRequest.count())
                .from(qRequest)
                .where(qRequest.event.id.in(idList)
                        .and(qRequest.status.eq(RequestStatus.CONFIRMED)))
                .groupBy(qRequest.event)
                .fetch();

        for (EventDto eventDto : eventDtoList) {
            Optional<Tuple> tupleOptional = tupleList
                    .stream()
                    .filter(t -> Objects.equals(t.get(qRequest.event.id), eventDto.getId()))
                    .findFirst();
            if (tupleOptional.isPresent()) {
                eventDto.setConfirmedRequests(Objects.requireNonNull(tupleOptional.get().get(qRequest.count()))
                        .intValue());
            } else {
                eventDto.setConfirmedRequests(0);
            }
        }
    }

    private EventFullDto getEventFullDto(Event event) {
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        setViews(Collections.singletonList(eventFullDto));
        setConfirmedRequests(Collections.singletonList(eventFullDto));
        return eventFullDto;
    }

    private void updateEvent(Event event, UpdateEventRequestDto updateEventDto) {
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategory() != null) {
            Categories category = findCategory(updateEventDto.getCategory());
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
    }

    private void updateEvent(Event event, AdminUpdateEventRequestDto updateEventDto) {
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategory() != null) {
            Categories category = findCategory(updateEventDto.getCategory());
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
    }

    private <T> List<T> getSubList(List<T> list, int from, int size) {
        if (from + size > list.size()) {
            return new ArrayList<>();
        } else {
            return list.subList(from, from + size);
        }
    }

    private List<Tuple> getEventsWithEventsDateSort(Boolean onlyAvailable, JPAQueryFactory queryFactory,
                                                    QEvent qEvent, QRequest qRequest, BooleanBuilder booleanBuilder,
                                                    int from, int size) {
        if (onlyAvailable) {
            return queryFactory.select(qEvent, qRequest.count())
                    .from(qEvent)
                    .leftJoin(qRequest).on(qEvent.eq(qRequest.event))
                    .where(booleanBuilder)
                    .groupBy(qEvent)
                    .having(qEvent.participantLimit.eq(0)
                            .or(qEvent.participantLimit.gt(qRequest.count())))
                    .orderBy(qEvent.eventDate.asc())
                    .limit(size)
                    .offset(from)
                    .fetch();
        } else {
            return queryFactory.select(qEvent, qRequest.count())
                    .from(qEvent)
                    .leftJoin(qRequest).on(qEvent.eq(qRequest.event))
                    .fetchJoin()
                    .where(booleanBuilder)
                    .groupBy(qEvent)
                    .orderBy(qEvent.eventDate.asc())
                    .limit(size)
                    .offset(from)
                    .fetch();
        }
    }

    private List<EventShortDto> getEventsWithViewsSort(Boolean onlyAvailable, JPAQueryFactory queryFactory,
                                                       QEvent qEvent, QRequest qRequest, BooleanBuilder booleanBuilder,
                                                       int from, int size) {
        List<Tuple> tupleList;
        List<EventShortDto> eventsDto;
        if (onlyAvailable) {
            tupleList = queryFactory.select(qEvent, qRequest.count())
                    .from(qEvent)
                    .leftJoin(qRequest).on(qEvent.eq(qRequest.event))
                    .where(booleanBuilder)
                    .groupBy(qEvent)
                    .having(qEvent.participantLimit.eq(0)
                            .or(qEvent.participantLimit.gt(qRequest.count())))
                    .orderBy(qEvent.eventDate.asc())
                    .fetch();
        } else {
            tupleList = queryFactory.select(qEvent, qRequest.count())
                    .from(qEvent)
                    .leftJoin(qRequest).on(qEvent.eq(qRequest.event))
                    .fetchJoin()
                    .where(booleanBuilder)
                    .groupBy(qEvent)
                    .orderBy(qEvent.eventDate.asc())
                    .fetch();
        }
        eventsDto = mapListEventsToEventShortDto(tupleList, qEvent, qRequest);
        setViews(eventsDto);
        eventsDto.sort((o1, o2) -> (-Integer.compare(o1.getViews(), o2.getViews())));
        return getSubList(eventsDto, from, size);
    }

    private List<EventShortDto> mapListEventsToEventShortDto(List<Tuple> tupleList, QEvent qEvent, QRequest qRequest) {
        return tupleList.stream()
                .map(e -> {
                    EventShortDto eventShortDto = EventMapper.toEventShortDto(Objects.requireNonNull(e.get(qEvent)));
                    eventShortDto.setConfirmedRequests(Objects.requireNonNull(e.get(qRequest.count())).intValue());
                    return eventShortDto;
                })
                .collect(Collectors.toList());
    }
}