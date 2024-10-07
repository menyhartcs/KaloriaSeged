package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.mapper.UserMapper;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final UserFoodLogRepository userFoodLogRepository;
    private final ValidationService validationService;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        validationService.registerUser(user);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        User updatedUserObj = userRepository.save(user);

        List<UserFoodLog> userFoodLogs = userFoodLogRepository.findByFoodId(userId);
        userFoodLogs.forEach(userFoodLog -> {
            userFoodLog.setUser(updatedUserObj);
            userFoodLogRepository.save(userFoodLog);
        });

        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        userRepository.deleteById(userId);
    }
}
