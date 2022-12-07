package ru.practicum.ewmmain.dto.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCommentDto {

    @NotBlank
    @Size(min = 1, max = 7000, message = "comment  должна содержать от 20 до 7000 символов!")
    String comment;

    @NotNull(message = "authorId не может быть Null!")
    Long authorId;
}
