package ru.practicum.ewmmain.service.user;

import ru.practicum.ewmmain.dto.users.NewUsersDto;
import ru.practicum.ewmmain.dto.users.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto addUser(NewUsersDto newUsersDto);

    void deleteUser(Long userId);
}
