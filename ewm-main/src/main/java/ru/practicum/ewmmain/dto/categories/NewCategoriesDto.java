package ru.practicum.ewmmain.dto.categories;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCategoriesDto {
    Long id;

    @NotNull(message = "name не может быть равным Null!")
    String name;
}
