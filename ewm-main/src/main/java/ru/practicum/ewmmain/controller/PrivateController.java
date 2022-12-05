package ru.practicum.ewmmain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.dto.events.EventFullDto;
import ru.practicum.ewmmain.dto.events.EventShortDto;
import ru.practicum.ewmmain.dto.events.NewEventsDto;
import ru.practicum.ewmmain.dto.events.UpdateEventRequestDto;
import ru.practicum.ewmmain.service.event.EventService;
import ru.practicum.ewmmain.service.request.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateController {
    private final EventService eventService;

    private final RequestService requestService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.getEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto,
                                    HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.updateEvent(userId, updateEventRequestDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable Long userId,
                                 @Valid @RequestBody NewEventsDto newEventDto,
                                 HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @PathVariable Long reqId,
                                                  HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId,
                                                 HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.rejectRequest(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId,
                                                     HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addRequest(@PathVariable Long userId,
                                              @RequestParam Long eventId,
                                              HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId,
                                                 HttpServletRequest request) {
        log.info("Get new request: {}", RequestBuilder.getStringFromRequest(request));
        return requestService.cancelRequest(userId, requestId);
    }
}
