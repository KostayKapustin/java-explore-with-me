package ru.practicum.ewmmain.service.comment;

import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.dto.comment.NewCommentDto;
import ru.practicum.ewmmain.dto.comment.UpdateCommentRequest;

import java.util.List;

public interface UserServiceComment {
    CommentDto addComment(Long eventId, NewCommentDto dto);

    CommentDto getComment(Long eventId, Long commentId);

    List<CommentDto> getAllCommentsByEvent(Long eventId, int from, int size);

    List<CommentDto> getAllCommentsByUser(Long userId, int from, int size);

    CommentDto updateComment(Long eventId, UpdateCommentRequest updateCommentRequest);

    void deleteComment(Long eventId, Long userId, Long commentId);
}
