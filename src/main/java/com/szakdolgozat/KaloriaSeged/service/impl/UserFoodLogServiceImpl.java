package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.mapper.UserFoodLogMapper;
import com.szakdolgozat.KaloriaSeged.repository.FoodRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import com.szakdolgozat.KaloriaSeged.service.UserFoodLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class UserFoodLogServiceImpl implements UserFoodLogService {

    private final UserFoodLogRepository userFoodLogRepository;
    private final FoodRepository foodRepository;

    @Override
    @Transactional
    public UserFoodLogDto createUserFoodLog(UserFoodLogDto userFoodLogDto) {
        UserFoodLog userFoodLog = UserFoodLogMapper.mapToUserFoodLog(userFoodLogDto);

        UserFoodLog savedUserFoodLog = userFoodLogRepository.save(userFoodLog);

        return UserFoodLogMapper.mapToUserFoodLogDto(savedUserFoodLog);
    }

    @Override
    public UserFoodLogDto getUserFoodLogById(Long userFoodLogId) {
        UserFoodLog userFoodLog = userFoodLogRepository.findById(userFoodLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserFoodLog does not exist with given id: " + userFoodLogId));
        return UserFoodLogMapper.mapToUserFoodLogDto(userFoodLog);
    }

    @Override
    public List<UserFoodLogDto> getAllUserFoodLogs() {
        return userFoodLogRepository.findAll().stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
    }

    @Override
    public List<UserFoodLogDto> getUserFoodLogsByUserIdAndDate(Long userId, LocalDate date) {
        return userFoodLogRepository.findByUserIdAndDate(userId, date).stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
    }

    @Override
    public List<UserFoodLogDto> getUserFoodLogsByDate(LocalDate date) {
        return userFoodLogRepository.findByDate(date).stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
    }

    @Override
    public List<UserFoodLogDto> getUserFoodLogsByUserId(Long userId) {
        return userFoodLogRepository.findByUserId(userId).stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
    }

    @Override
    public UserFoodLogDto updateUserFoodLog(Long userFoodLogId, UserFoodLogDto updatedUserFoodLog) {
        UserFoodLog userFoodLog = userFoodLogRepository.findById(userFoodLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserFoodLog does not exist with given id: " + userFoodLogId));

        Long foodId = updatedUserFoodLog.getFood().getId();
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food does not exist with given id: " + foodId));

        userFoodLog.setFood(food);
        userFoodLog.setDate(updatedUserFoodLog.getDate());
        userFoodLog.setAmount(updatedUserFoodLog.getAmount());

        UserFoodLog updatedUserFoodLogObj = userFoodLogRepository.save(userFoodLog);

        return UserFoodLogMapper.mapToUserFoodLogDto(updatedUserFoodLogObj);
    }

    @Override
    public void deleteUserFoodLog(Long userFoodLogId) {
        userFoodLogRepository.findById(userFoodLogId)
                .orElseThrow(() -> new ResourceNotFoundException("UserFoodLog does not exist with given id: " + userFoodLogId));
        userFoodLogRepository.deleteById(userFoodLogId);
    }
}
