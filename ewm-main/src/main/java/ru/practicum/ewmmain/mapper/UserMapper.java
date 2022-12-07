package ru.practicum.ewmmain.mapper;


import ru.practicum.ewmmain.dto.users.NewUsersDto;
import ru.practicum.ewmmain.dto.users.UserDto;
import ru.practicum.ewmmain.dto.users.UserShortDto;
import ru.practicum.ewmmain.model.User;

public class UserMapper {

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User toUser(NewUsersDto newUserRequestDto) {
        User user = new User();
        user.setName(newUserRequestDto.getName());
        user.setEmail(newUserRequestDto.getEmail());
        return user;
    }
}
