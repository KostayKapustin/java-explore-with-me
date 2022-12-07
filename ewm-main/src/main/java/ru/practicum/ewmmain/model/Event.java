package ru.practicum.ewmmain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.model.constant.EventState;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity()
@NoArgsConstructor
@Table(name = "events")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Long id;

    @Size(max = 2000)
    @Column(name = "annotation", length = 2000)
    String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Categories category;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Size(max = 7000)
    @Column(name = "description",length = 7000)
    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User initiator;

    @Column(name = "location_lat")
    float locationLat;

    @Column(name = "location_lon")
    float locationLon;

    @Column(name = "paid")
    boolean paid;

    @Column(name = "participant_limit")
    int participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    boolean requestModeration;

    @Column(name = "state")
    EventState state;

    @Column(name = "title")
    String title;
}
