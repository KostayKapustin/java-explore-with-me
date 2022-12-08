package ru.practicum.ewmmain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmain.model.Comment;
import ru.practicum.ewmmain.model.constant.EventState;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment as c " +
            "order by c.created desc")
    List<Comment> findAllComments(Pageable pageable);

    Optional<Comment> findCommentByIdAndEventId(Long commentId, Long eventId);


    List<Comment> findCommentsByAuthorIdAndAndEventStateOrderByCreatedDesc(
            Long authorId, EventState state, Pageable pageable);

    List<Comment> findCommentsByEventIdOrderByCreatedDesc(Long eventId, Pageable pageable);

    Optional<Comment> findCommentByIdAndEventIdAndAuthorId(Long commentId, Long eventId, Long authorId);
}