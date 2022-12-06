package ru.practicum.ewmmain.dto.events;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.dto.categories.CategoryDto;
import ru.practicum.ewmmain.dto.users.UserShortDto;
import ru.practicum.ewmmain.model.Location;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto implements EventDto {
    Long id;

    String annotation;

    CategoryDto category;

    int confirmedRequests;

    String createdOn;

    String description;

    String eventDate;

    UserShortDto initiator;

    Location location;

    boolean paid;

    int participantLimit;

    String publishedOn;

    boolean requestModeration;

    String state;

    String title;

    int views;
}
