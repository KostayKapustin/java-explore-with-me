package ru.practicum.ewmmain.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.controller.RequestBuilder;
import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.dto.comment.NewCommentDto;
import ru.practicum.ewmmain.dto.comment.UpdateCommentRequest;
import ru.practicum.ewmmain.service.comment.UserServiceComment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserControllerComment {
    private final UserServiceComment userCommentService;

    @PostMapping("/events/{eventId}/comments")
    public CommentDto addComment(@PathVariable @PositiveOrZero Long eventId,
                                 @RequestBody @Valid NewCommentDto dto,
                                 HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userCommentService.addComment(eventId, dto);
    }

    @GetMapping("/events/{eventId}/comments/{commId}")
    public CommentDto getComment(
            @PathVariable @PositiveOrZero Long eventId,
            @PathVariable(name = "commId") Long commentId,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userCommentService.getComment(eventId, commentId);
    }

    @GetMapping("/events/{eventId}/comments/")
    public List<CommentDto> getAllCommentsByEvent(
            @PathVariable @PositiveOrZero Long eventId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userCommentService.getAllCommentsByEvent(eventId, from, size);
    }

    @GetMapping("/users/{userId}/comments/")
    public List<CommentDto> getAllCommentsByUser(
            @PathVariable @PositiveOrZero Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userCommentService.getAllCommentsByUser(userId, from, size);
    }

    @PatchMapping("/events/{eventId}/comments")
    public CommentDto updateComment(
            @PathVariable @PositiveOrZero Long eventId,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return userCommentService.updateComment(eventId, updateCommentRequest);
    }

    @DeleteMapping("/users/{userId}/events/{eventId}/comments/{commId}")
    public void deleteComment(
            @PathVariable @PositiveOrZero Long eventId,
            @PathVariable @PositiveOrZero Long userId,
            @PathVariable(name = "commId") @PositiveOrZero Long commentId,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        userCommentService.deleteComment(eventId, userId, commentId);
    }
}
