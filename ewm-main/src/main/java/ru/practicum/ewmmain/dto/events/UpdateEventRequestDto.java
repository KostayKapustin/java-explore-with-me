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

    @Size(min = 20, max = 2000, message = "Annotation must be between 20 and 2000 characters")
    String annotation;

    Long category;

    @Size(min = 20, max = 7000, message = "Annotation must be between 20 and 7000 characters")
    String description;

    String eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Size(min = 3, max = 120, message = "Annotation must be between 3 and 120 characters")
    String title;
}
