package ru.practicum.ewmmain.dto.events;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.model.Location;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUpdateEventRequestDto {
    String annotation;

    Long category;

    String description;

    String eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    String title;
}
