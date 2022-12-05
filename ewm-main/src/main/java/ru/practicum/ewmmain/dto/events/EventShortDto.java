package ru.practicum.ewmmain.dto.events;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.dto.categories.CategoryDto;
import ru.practicum.ewmmain.dto.users.UserShortDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto implements EventDto {
    Long id;

    String annotation;

    CategoryDto category;

    int confirmedRequests;

    String eventDate;

    UserShortDto initiator;

    boolean paid;

    String title;

    int views;
}
