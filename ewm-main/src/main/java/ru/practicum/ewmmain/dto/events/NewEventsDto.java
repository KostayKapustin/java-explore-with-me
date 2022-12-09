package ru.practicum.ewmmain.dto.events;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmmain.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventsDto {

    @NotNull(message = "Annotation не может быть Null!")
    @Size(min = 20, max = 2000, message = "Annotation  должна содержать от 20 до 2000 символов!")
    String annotation;

    @NotNull(message = "category не может быть Null!")
    Long category;

    @NotNull(message = "description не может быть Null!")
    @Size(min = 20, max = 7000, message = "Description  должна содержать от 20 до 7000 символов!")
    String description;

    @NotNull(message = "eventDate не может быть Null!")
    String eventDate;

    @NotNull(message = "location не может быть Null!")
    Location location;

    boolean paid = false;

    int participantLimit;

    boolean requestModeration = true;

    @NotNull(message = "title не может быть Null!")
    @Size(min = 3, max = 120, message = "Title  должна содержать от 3 до 120 символов!")
    String title;
}