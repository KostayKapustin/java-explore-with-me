package ru.practicum.ewmmain.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.dto.comment.NewCommentDto;
import ru.practicum.ewmmain.dto.comment.UpdateCommentRequest;
import ru.practicum.ewmmain.exception.EventNotFoundException;
import ru.practicum.ewmmain.exception.ForbiddenException;
import ru.practicum.ewmmain.exception.UserNotFoundException;
import ru.practicum.ewmmain.mapper.CommentMapper;
import ru.practicum.ewmmain.model.Comment;
import ru.practicum.ewmmain.model.Event;
import ru.practicum.ewmmain.model.User;
import ru.practicum.ewmmain.model.constant.EventState;
import ru.practicum.ewmmain.repository.CommentRepository;
import ru.practicum.ewmmain.repository.EventRepository;
import ru.practicum.ewmmain.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceCommentImpl implements UserServiceComment {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto addComment(Long eventId, NewCommentDto dto) {
        User author = checkUser(dto.getAuthorId());
        Event event = checkEvent(eventId);
        Comment comment = commentRepository.save(CommentMapper.fromNewDtoToComment(dto, author, event));
        return CommentMapper.toDto(comment);
    }

    @Override
    public CommentDto getComment(Long eventId, Long commentId) {
        final Comment comment = commentRepository.findCommentByIdAndEventId(commentId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "В этой базе данных нет комментария с id=%d от события с id=%d.", commentId, eventId)));
        checkEventPrivate(comment.getEvent());
        return CommentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Event event = checkEvent(eventId);
        return commentRepository.findCommentsByEventIdOrderByCreatedDesc(event.getId(), pageRequest).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByUser(Long userId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return commentRepository.findCommentsByAuthorIdAndAndEventStateOrderByCreatedDesc(
                        userId, EventState.PUBLISHED, pageRequest).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long eventId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findCommentByIdAndEventIdAndAuthorId(
                        updateCommentRequest.getId(), eventId, updateCommentRequest.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Комментарий с id=%d от пользователя с id=%d к событию с id=%d не существует.",
                        updateCommentRequest.getId(), updateCommentRequest.getAuthorId(), eventId)
                ));
        checkEventPrivate(comment.getEvent());
        comment.setComment(updateCommentRequest.getComment());
        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.updateToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long eventId, Long userId, Long commentId) {
        commentRepository.findCommentByIdAndEventIdAndAuthorId(commentId, eventId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Комментария с id=%d от пользователя с id=%d к событию с id=%d не существует.",
                        commentId, userId, eventId)));
        commentRepository.deleteById(commentId);
    }

    private User checkUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    private Event checkEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return checkEventPrivate(event);
    }

    private Event checkEventPrivate(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException(String.format("Вы не можете добавить комментарий к событию со статусом %s.",
                    event.getState()));
    }
        return event;
    }
}

