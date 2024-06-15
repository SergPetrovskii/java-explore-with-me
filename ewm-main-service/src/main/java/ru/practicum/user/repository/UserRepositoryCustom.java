package ru.practicum.user.repository;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserRepositoryCustom {

  List<UserDto> getUsers(List<Long> ids, int from, int size);
}
