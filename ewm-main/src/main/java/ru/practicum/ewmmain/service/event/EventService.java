package ru.practicum.ewmmain.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.events.*;
import ru.practicum.ewmmain.model.EventSort;

import java.util.List;

@Service
public interface EventService {
    EventFullDto addEvent(Long userId, NewEventsDto newEventDto);

    List<EventShortDto> getEvents(Long userId, int from, int size);

    EventFullDto updateEvent(Long userId, UpdateEventRequestDto updateEventRequestDto);

    EventFullDto updateEvent(Long eventId, AdminUpdateEventRequestDto requestDto);

    EventFullDto getEvent(Long userId, Long eventId);

    EventFullDto getEvent(Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<EventShortDto> getEventsWithFilter(String text, List<Long> categories, Boolean paid, String rangeStart,
                                            String rangeEnd, Boolean onlyAvailable, EventSort sort, Integer from,
                                            Integer size);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, int from, int size);
}
