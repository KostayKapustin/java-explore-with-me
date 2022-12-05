package ru.practicum.ewmmain.dto.compilations;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationsDto {
    String title;
    Boolean pinned = false;

    @NotNull(message = "events не может быть равным Null!")
    List<Long> events;
}
