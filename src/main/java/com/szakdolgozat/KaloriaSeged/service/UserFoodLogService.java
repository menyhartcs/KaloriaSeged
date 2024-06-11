package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;

import java.util.List;

public interface UserFoodLogService {
    UserFoodLogDto createUserFoodLog(UserFoodLogDto userFoodLogDto);
    UserFoodLogDto getUserFoodLogById(Long userFoodLogId);
    List<UserFoodLogDto> getAllUserFoodLogs();
    UserFoodLogDto updateUserFoodLog(Long userFoodLogId, UserFoodLogDto updatedUserFoodLog);
    void deleteUserFoodLog(Long userFoodLogId);
}
