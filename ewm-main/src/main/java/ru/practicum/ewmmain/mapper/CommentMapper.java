package ru.practicum.ewmmain.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmain.dto.comment.CommentDto;
import ru.practicum.ewmmain.dto.comment.NewCommentDto;
import ru.practicum.ewmmain.model.Comment;
import ru.practicum.ewmmain.model.Event;
import ru.practicum.ewmmain.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getCreated(),
                null
        );
    }

    public static CommentDto updateToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getCreated(),
                LocalDateTime.now()
        );
    }

    public static Comment fromNewDtoToComment(NewCommentDto dto, User author, Event event) {
        return new Comment(
                null,
                dto.getComment(),
                author,
                event,
                LocalDateTime.now(),
                null
        );
    }
}
