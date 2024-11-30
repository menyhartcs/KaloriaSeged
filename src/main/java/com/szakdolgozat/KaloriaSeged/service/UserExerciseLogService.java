package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the {@link UserExerciseLogDto} object CRUD operations.
 */
public interface UserExerciseLogService {
    UserExerciseLogDto createUserExerciseLog(UserExerciseLogDto userExerciseLogDto);
    UserExerciseLogDto getUserExerciseLogById(Long userExerciseLogId);
    List<UserExerciseLogDto> getAllUserExerciseLogs();
    List<UserExerciseLogDto> getUserExerciseLogsByUserIdAndDate(Long userId, LocalDate date);
    List<UserExerciseLogDto> getUserExerciseLogsByDate(LocalDate date);
    List<UserExerciseLogDto> getUserExerciseLogsByUserId(Long userId);
    UserExerciseLogDto updateUserExerciseLog(Long userExerciseLogId, UserExerciseLogDto updatedUserExerciseLog);
    void deleteUserExerciseLog(Long userExerciseLogId);
}
