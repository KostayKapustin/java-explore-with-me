package ru.practicum.ewmmain.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.mapper.CommentMapper;
import ru.practicum.ewmmain.model.Comment;
import ru.practicum.ewmmain.repository.CommentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceCommentImpl implements AdminServiceComment {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAllComments(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        return commentRepository.findAllComments(pageRequest).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentByAdmin(Long eventId, Long commentId) {
        findCommentByIdAndEventId(commentId, eventId);
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentByIdAndEventId(Long commentId, Long eventId) {
        return commentRepository.findCommentByIdAndEventId(commentId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "В базе данных нет комментария с id=%d от события с id=%d.", commentId, eventId)));
    }
}
