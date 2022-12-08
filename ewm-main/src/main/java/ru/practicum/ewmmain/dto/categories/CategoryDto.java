package ru.practicum.ewmmain.dto.categories;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {

    @NotNull(message = "id не может быть Null!")
    private Long id;

    @Size(max = 50)
    @NotNull(message = "Name не может быть Null!")
    private String name;
}
