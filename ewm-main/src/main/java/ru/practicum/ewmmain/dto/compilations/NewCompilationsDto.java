package ru.practicum.ewmmain.dto.compilations;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationsDto {

    @NotNull(message = "title не может быть равным Null!")
    String title;
    Boolean pinned = false;
    List<Long> events;
}
