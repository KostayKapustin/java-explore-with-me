package ru.practicum.ewmmain.dto.users;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Validated
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @NotNull(message = "Name не может быть Null!")
    String name;

    @Email (message = "Email не подходит по требованиям!")
    @NotNull(message = "Email не может быть Null!")
    String email;
}
