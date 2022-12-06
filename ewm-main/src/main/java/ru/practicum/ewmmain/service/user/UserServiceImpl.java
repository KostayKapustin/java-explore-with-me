package ru.practicum.ewmmain.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.dto.users.NewUsersDto;
import ru.practicum.ewmmain.dto.users.UserDto;
import ru.practicum.ewmmain.mapper.UserMapper;
import ru.practicum.ewmmain.model.User;
import ru.practicum.ewmmain.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addUser(NewUsersDto newUsersDto) {
        User user = UserMapper.toUser(newUsersDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        List<User> users;
        if (ids != null) {
            users = userRepository.findUserByIdIn(ids, pageable);
        } else {
            users = userRepository.findAll(pageable).stream().collect(Collectors.toList());
        }
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}