package ru.practicum.ewmmain.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmmain.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.exception.EventNotFoundException;
import ru.practicum.ewmmain.exception.ForbiddenException;
import ru.practicum.ewmmain.exception.RequestNotFoundExceprion;
import ru.practicum.ewmmain.exception.UserNotFoundException;
import ru.practicum.ewmmain.mapper.RequestMapper;
import ru.practicum.ewmmain.model.Event;
import ru.practicum.ewmmain.model.Request;
import ru.practicum.ewmmain.model.User;
import ru.practicum.ewmmain.model.constant.EventState;
import ru.practicum.ewmmain.model.constant.RequestStatus;
import ru.practicum.ewmmain.repository.EventRepository;
import ru.practicum.ewmmain.repository.RequestRepository;
import ru.practicum.ewmmain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements  RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        return requestRepository.findByEvent_Id(eventId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        findUser(userId);
        Event event = findEvent(eventId);
        Request request = findRequest(reqId);
        if (event.getParticipantLimit() == 0 || !event.isRequestModeration()) {
            throw new ForbiddenException("Подтверждение не требуется");
        }
        List<Request> requests = requestRepository.findByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED);
        if (requests.size() >= event.getParticipantLimit()) {
            throw new ForbiddenException("Предел был достигнут");
        }
        request.setStatus(RequestStatus.CONFIRMED);
        if (requests.size() + 1 >= event.getParticipantLimit()) {
            requestRepository.findByEvent_IdAndStatus(eventId, RequestStatus.PENDING)
                    .forEach(r -> r.setStatus(RequestStatus.CANCELED));
        }
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        findUser(userId);
        findEvent(eventId);
        Request request = findRequest(reqId);
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        return requestRepository.findByRequester_Id(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = findUser(userId);
        Event event = findEvent(eventId);
        Request existRequest = requestRepository.findByRequester_IdAndEvent_Id(userId, eventId);
        if (existRequest != null) {
            throw new ForbiddenException(String.format("Запрос с идентификатором пользователя=%d, " +
                    "EventID=%d уже существует", userId, eventId));
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException(String.format("Пользователь с id=%d является инициатором события с id=%d",
                    userId, eventId));
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Событие не публикуется");
        }
        int count = requestRepository.findByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED).size();
        if (count >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ForbiddenException("Лимит участников был достигнут");
        }
        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setRequester(user);
        request.setEvent(event);
        if (!event.isRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        findUser(userId);
        Request request = findRequest(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new ForbiddenException(String.format("Пользователь с id=%d не является инициатором запроса с id=%d",
                    userId, requestId));
        }
        request.setStatus(RequestStatus.CANCELED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private Event findEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    private Request findRequest(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundExceprion(id));
    }
}
