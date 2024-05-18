package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UserDto updatedUser);
    void deleteUser(Long userId);
}
