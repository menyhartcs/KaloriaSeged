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

/**
 * Implementation for {@link UserService} interface. Implements CRUD operations.
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final UserFoodLogRepository userFoodLogRepository;

    // Creates the User.
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    // Finds a User by id.
    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        return UserMapper.mapToUserDto(user);
    }

    // Finds a User by email.
    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return UserMapper.mapToUserDto(user);
    }

    // Find all Users in the table.
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    // Updates User.
    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setGender(updatedUser.getGender());
        user.setHeight(updatedUser.getHeight());
        user.setWeight(updatedUser.getWeight());
        user.setAge(updatedUser.getAge());
        user.setCalorie(updatedUser.getCalorie());
        user.setProtein(updatedUser.getProtein());
        user.setCarbohydrate(updatedUser.getCarbohydrate());
        user.setFat(updatedUser.getFat());
        user.setRole(updatedUser.getRole());

        User updatedUserObj = userRepository.save(user);

        List<UserFoodLog> userFoodLogs = userFoodLogRepository.findByFoodId(userId);
        userFoodLogs.forEach(userFoodLog -> {
            userFoodLog.setUser(updatedUserObj);
            userFoodLogRepository.save(userFoodLog);
        });

        return UserMapper.mapToUserDto(updatedUserObj);
    }

    // Deletes User.
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        userRepository.deleteById(userId);
    }
}
