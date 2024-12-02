package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;

import java.time.LocalDate;

/**
 * Handles the mapping between {@link UserExerciseLog} and {@link UserExerciseLogDto} objects.
 */
public class UserExerciseLogMapper {

    // Maps the UserExerciseLog to UserExerciseLogDto object.
    public static UserExerciseLogDto mapToUserExerciseLogDto(UserExerciseLog userExerciseLog) {
        UserExerciseLogDto userExerciseLogDto = new UserExerciseLogDto();
        userExerciseLogDto.setId(userExerciseLog.getId());
        userExerciseLogDto.setUser(createUserDto(userExerciseLog));
        userExerciseLogDto.setExercise(createExerciseDto(userExerciseLog));
        userExerciseLogDto.setDuration(userExerciseLog.getDuration());
        if (userExerciseLog.getDate() != null) {
            userExerciseLogDto.setDate(userExerciseLog.getDate());
        } else {
            userExerciseLogDto.setDate(LocalDate.now());
        }
        return userExerciseLogDto;
    }

    // INIT the ExerciseDto object for UserExerciseLogDto object.
    private static ExerciseDto createExerciseDto(UserExerciseLog userExerciseLog) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(userExerciseLog.getExercise().getId());
        exerciseDto.setName(userExerciseLog.getExercise().getName());
        exerciseDto.setCalorie(userExerciseLog.getExercise().getCalorie());
        return exerciseDto;
    }

    // INIT the UserDto object for UserExerciseLogDto object.
    private static UserDto createUserDto(UserExerciseLog userExerciseLog) {
        UserDto userDto = new UserDto();
        userDto.setId(userExerciseLog.getUser().getId());
        userDto.setName(userExerciseLog.getUser().getName());
        userDto.setEmail(userExerciseLog.getUser().getEmail());
        userDto.setPassword(userExerciseLog.getUser().getPassword());
        userDto.setGender(userExerciseLog.getUser().getGender());
        userDto.setHeight(userExerciseLog.getUser().getHeight());
        userDto.setWeight(userExerciseLog.getUser().getWeight());
        userDto.setAge(userExerciseLog.getUser().getAge());
        userDto.setCalorie(userExerciseLog.getUser().getCalorie());
        userDto.setProtein(userExerciseLog.getUser().getProtein());
        userDto.setCarbohydrate(userExerciseLog.getUser().getCarbohydrate());
        userDto.setFat(userExerciseLog.getUser().getFat());
        userDto.setRole(userExerciseLog.getUser().getRole());
        return userDto;
    }

    // Maps the UserExerciseLogDto to UserExerciseLog object.
    public static UserExerciseLog mapToUserExerciseLog(UserExerciseLogDto userExerciseLogDto) {
        UserExerciseLog userExerciseLog = new UserExerciseLog();
        userExerciseLog.setId(userExerciseLogDto.getId());
        userExerciseLog.setUser(UserMapper.mapToUser(userExerciseLogDto.getUser()));
        userExerciseLog.setExercise(ExerciseMapper.mapToExercise(userExerciseLogDto.getExercise()));
        userExerciseLog.setDuration(userExerciseLogDto.getDuration());
        if (userExerciseLogDto.getDate() != null) {
            userExerciseLog.setDate(userExerciseLogDto.getDate());
        } else {
            userExerciseLog.setDate(LocalDate.now());
        }
        return userExerciseLog;
    }
}
