package ru.practicum.ewmmain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.dto.categories.CategoryDto;
import ru.practicum.ewmmain.dto.categories.NewCategoriesDto;
import ru.practicum.ewmmain.dto.compilations.CompilationDto;
import ru.practicum.ewmmain.dto.compilations.NewCompilationsDto;
import ru.practicum.ewmmain.dto.events.AdminUpdateEventRequestDto;
import ru.practicum.ewmmain.dto.events.EventFullDto;
import ru.practicum.ewmmain.dto.users.NewUsersDto;
import ru.practicum.ewmmain.dto.users.UserDto;
import ru.practicum.ewmmain.service.categories.CategoriesService;
import ru.practicum.ewmmain.service.compilation.CompilationService;
import ru.practicum.ewmmain.service.event.EventService;
import ru.practicum.ewmmain.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EventService eventService;

    private final CategoriesService categoriesService;

    private final UserService userService;

    private final CompilationService compilationService;

    private static final String EVENT = "/events";
    private static final String CATEGORIES = "/categories";
    private static final String USER = "/users";
    private static final String COMPILATION = "/compilations";

    @GetMapping(EVENT)
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                        @Positive @RequestParam(defaultValue = "10") int size,
                                        HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }


    @PutMapping(EVENT + "/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody AdminUpdateEventRequestDto requestDto,
                                    HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.updateEvent(eventId, requestDto);
    }

    @PatchMapping(EVENT + "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId,
                                     HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.publishEvent(eventId);
    }


    @PatchMapping(EVENT + "/{eventId}/reject")
    public EventFullDto publishReject(@PathVariable Long eventId,
                                      HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return eventService.rejectEvent(eventId);
    }

    @PatchMapping(CATEGORIES)
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                      HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return categoriesService.updateCategory(categoryDto);
    }

    @PostMapping(CATEGORIES)
    public CategoryDto addCategory(@Valid @RequestBody NewCategoriesDto newCategoryDto,
                                   HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return categoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping(CATEGORIES + "/{catId}")
    public void deleteCategory(@PathVariable Long catId,
                               HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        categoriesService.deleteCategory(catId);
    }

    @GetMapping(USER)
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                  @Positive @RequestParam(defaultValue = "10") Integer size,
                                  HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userService.getUsers(ids, from, size);
    }

    @PostMapping(USER)
    public UserDto addUser(@Valid @RequestBody NewUsersDto newUsersDto,
                           HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userService.addUser(newUsersDto);
    }

    @DeleteMapping(USER + "/{userId}")
    public void deleteUser(@PathVariable Long userId,
                           HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        userService.deleteUser(userId);
    }

    @PostMapping(COMPILATION)
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationsDto newCompilationDto,
                                         HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping(COMPILATION + "/{compId}")
    public void deleteCompilation(@PathVariable Long compId,
                                  HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping(COMPILATION + "/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId,
                                           HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping(COMPILATION + "/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId,
                                      HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping(COMPILATION + "/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId,
                                 HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        compilationService.unpinCompilation(compId);
    }
  
    @PatchMapping(COMPILATION + "/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId,
                               HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        compilationService.pinCompilation(compId);
    }
}