package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    List<UserDto> getAll(List<Long> id, int from, int size);

    void delete(long userId);
}
