package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;

import java.time.LocalDate;
import java.util.List;

public interface UserFoodLogService {
    UserFoodLogDto createUserFoodLog(UserFoodLogDto userFoodLogDto);
    UserFoodLogDto getUserFoodLogById(Long userFoodLogId);
    List<UserFoodLogDto> getAllUserFoodLogs();
    List<UserFoodLogDto> getUserFoodLogsByUserIdAndDate(Long userId, LocalDate date);
    List<UserFoodLogDto> getUserFoodLogsByDate(LocalDate date);
    List<UserFoodLogDto> getUserFoodLogsByUserId(Long userId);
    UserFoodLogDto updateUserFoodLog(Long userFoodLogId, UserFoodLogDto updatedUserFoodLog);
    void deleteUserFoodLog(Long userFoodLogId);
}
