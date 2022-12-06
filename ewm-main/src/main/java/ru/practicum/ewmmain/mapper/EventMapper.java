package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.dto.events.EventFullDto;
import ru.practicum.ewmmain.dto.events.EventShortDto;
import ru.practicum.ewmmain.dto.events.NewEventsDto;
import ru.practicum.ewmmain.model.Event;
import ru.practicum.ewmmain.model.Location;
import ru.practicum.ewmmain.model.constant.EventState;

import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;

public class EventMapper {

    public static Event toEvent(NewEventsDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription(newEventDto.getDescription());
        LocalDateTime eventDate = DateTimeMapper.toLocalDateTime(newEventDto.getEventDate());
        if (Duration.between(LocalDateTime.now(), eventDate).toHours() < 2) {
            throw new ValidationException("До начала мероприятия осталось менее двух часов!");
        }
        event.setEventDate(eventDate);
        event.setLocationLat(newEventDto.getLocation().getLat());
        event.setLocationLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setState(EventState.PENDING);
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullDto.setCreatedOn(DateTimeMapper.toString(event.getCreatedOn()));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(DateTimeMapper.toString(event.getEventDate()));
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(new Location(event.getLocationLat(), event.getLocationLon()));
        eventFullDto.setPaid(event.isPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() != null) {
            eventFullDto.setPublishedOn(DateTimeMapper.toString(event.getPublishedOn()));
        }
        eventFullDto.setRequestModeration(event.isRequestModeration());
        eventFullDto.setState(event.getState().toString());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setId(event.getId());
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventShortDto.setEventDate(DateTimeMapper.toString(event.getEventDate()));
        eventShortDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventShortDto.setPaid(event.isPaid());
        eventShortDto.setTitle(event.getTitle());
        return eventShortDto;
    }
}
