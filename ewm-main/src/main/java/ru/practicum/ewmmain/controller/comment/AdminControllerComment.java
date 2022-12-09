package ru.practicum.ewmmain.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.controller.RequestBuilder;
import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.service.comment.AdminServiceComment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminControllerComment {

    private final AdminServiceComment adminServiceComment;

    @GetMapping("/comments")
    public List<CommentDto> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        return adminServiceComment.getAllComments(from, size);
    }

    @DeleteMapping("/events/{eventId}/comments/{commId}")
    public void deleteCommentByAdmin(
            @PathVariable @PositiveOrZero Long eventId,
            @PathVariable(name = "commId") @PositiveOrZero Long commentId,
            HttpServletRequest request) {
        log.info("Получить новый запрос: {}", RequestBuilder.getStringFromRequest(request));
        adminServiceComment.deleteCommentByAdmin(eventId, commentId);
    }
}
