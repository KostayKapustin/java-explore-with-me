package ru.practicum.ewmmain.service.comment;

import ru.practicum.ewmmain.dto.comment.CommentDto;

import java.util.List;

public interface AdminServiceComment {
    List<CommentDto> getAllComments(int from, int size);

    void deleteCommentByAdmin(Long eventId, Long commentId);
}
