package ru.practicum.ewmmain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "compilation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "pinned")
    boolean pinned;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Event> events;
}
