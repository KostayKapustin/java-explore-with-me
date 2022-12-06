package ru.practicum.ewmmain.dto.users;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUsersDto {

    @NotNull(message = "Name не может быть Null!")
    String name;

    @Email (message = "Email не подходит по требованиям!")
    @NotNull(message = "Email не может быть Null!")
    String email;
}
