package ru.practicum.ewmmain.dto.events;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventRequestDto implements UpdateEventDto {
    @NotNull
    Long eventId;

    @Size(min = 20, max = 2000, message = "Annotation  должна содержать от 20 до 2000 символов!")
    String annotation;

    Long category;

    @Size(min = 20, max = 7000, message = "description  должна содержать от 20 до 7000 символов!")
    String description;

    String eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Size(min = 3, max = 120, message = "title  должна содержать от 3 до 120 символов!")
    String title;
}
