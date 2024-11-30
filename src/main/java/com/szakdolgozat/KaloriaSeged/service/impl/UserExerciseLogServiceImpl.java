package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.mapper.UserExerciseLogMapper;
import com.szakdolgozat.KaloriaSeged.repository.ExerciseRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserExerciseLogRepository;
import com.szakdolgozat.KaloriaSeged.service.UserExerciseLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation for {@link UserExerciseLog} interface. Implements CRUD operations.
 */
@AllArgsConstructor
@Service
public class UserExerciseLogServiceImpl implements UserExerciseLogService {

    private final UserExerciseLogRepository userExerciseLogRepository;
    private final ExerciseRepository exerciseRepository;

    // Creates UserExerciseLog.
    @Override
    @Transactional
    public UserExerciseLogDto createUserExerciseLog(UserExerciseLogDto userExerciseLogDto) {
        UserExerciseLog userExerciseLog = UserExerciseLogMapper.mapToUserExerciseLog(userExerciseLogDto);

        UserExerciseLog savedUserExerciseLog = userExerciseLogRepository.save(userExerciseLog);

        return UserExerciseLogMapper.mapToUserExerciseLogDto(savedUserExerciseLog);
    }

    // Finds UserExerciseLog by id.
    @Override
    public UserExerciseLogDto getUserExerciseLogById(Long userExerciseLogId) {
        UserExerciseLog userExerciseLog = userExerciseLogRepository.findById(userExerciseLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserExerciseLog does not exist with given id: " + userExerciseLogId));
        return UserExerciseLogMapper.mapToUserExerciseLogDto(userExerciseLog);
    }

    // Finds all UserExerciseLogs in the table.
    @Override
    public List<UserExerciseLogDto> getAllUserExerciseLogs() {
        return userExerciseLogRepository.findAll().stream()
                .map(UserExerciseLogMapper::mapToUserExerciseLogDto)
                .toList();
    }

    // Finds UserExerciseLogs for specific user id and date.
    @Override
    public List<UserExerciseLogDto> getUserExerciseLogsByUserIdAndDate(Long userId, LocalDate date) {
        return userExerciseLogRepository.findByUserIdAndDate(userId, date).stream()
                .map(UserExerciseLogMapper::mapToUserExerciseLogDto)
                .toList();
    }

    // Finds UserExerciseLogs for specific date.
    @Override
    public List<UserExerciseLogDto> getUserExerciseLogsByDate(LocalDate date) {
        return userExerciseLogRepository.findByDate(date).stream()
                .map(UserExerciseLogMapper::mapToUserExerciseLogDto)
                .toList();
    }

    // Finds UserExerciseLogs for specific user id.
    @Override
    public List<UserExerciseLogDto> getUserExerciseLogsByUserId(Long userId) {
        return userExerciseLogRepository.findByUserId(userId).stream()
                .map(UserExerciseLogMapper::mapToUserExerciseLogDto)
                .toList();
    }

    // Updates UserExerciseLog.
    @Override
    public UserExerciseLogDto updateUserExerciseLog(Long userExerciseLogId, UserExerciseLogDto updatedUserExerciseLog) {
        UserExerciseLog userExerciseLog = userExerciseLogRepository.findById(userExerciseLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserExerciseLog does not exist with given id: " + userExerciseLogId));

        Long exerciseId = updatedUserExerciseLog.getExercise().getId();
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with given id: " + exerciseId));

        userExerciseLog.setExercise(exercise);
        userExerciseLog.setDate(updatedUserExerciseLog.getDate());
        userExerciseLog.setDuration(updatedUserExerciseLog.getDuration());

        UserExerciseLog updatedUserExerciseLogObj = userExerciseLogRepository.save(userExerciseLog);

        return UserExerciseLogMapper.mapToUserExerciseLogDto(updatedUserExerciseLogObj);
    }

    // Delete UserExerciseLog.
    @Override
    public void deleteUserExerciseLog(Long userExerciseLogId) {
        userExerciseLogRepository.findById(userExerciseLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserExerciseLog does not exist with given id: " + userExerciseLogId));
        userExerciseLogRepository.deleteById(userExerciseLogId);
    }
}
