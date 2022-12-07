package ru.practicum.ewmmain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Long id;

    @Size(min = 1, max = 7000)
    @Column(name = "comment_text", length = 7000, nullable = false)
    String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    @Column(name = "comment_created")
    LocalDateTime created;

    @Column(name = "comment_updated")
    LocalDateTime updated;

}
