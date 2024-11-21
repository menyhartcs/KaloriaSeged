package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.mapper.FoodMapper;
import com.szakdolgozat.KaloriaSeged.repository.FoodRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation for {@link FoodService} interface. Implements CRUD operations.
 */
@AllArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

    private FoodRepository foodRepository;
    private final UserFoodLogRepository userFoodLogRepository;

    @Override
    public FoodDto createFood(FoodDto foodDto) {
        Food food = FoodMapper.mapToFood(foodDto);
        Food savedFood = foodRepository.save(food);
        return FoodMapper.mapToFoodDto(savedFood);
    }

    @Override
    public FoodDto getFoodById(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food does not exist with given id: " + foodId));
        return FoodMapper.mapToFoodDto(food);
    }

    @Override
    public FoodDto getFoodByName(String name) {
        Food food = foodRepository.findByName(name);
        if (food == null) {
            return null;
        }
        return FoodMapper.mapToFoodDto(food);
    }

    @Override
    public List<FoodDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream().map(FoodMapper::mapToFoodDto).collect(Collectors.toList());
    }

    @Override
    public FoodDto updateFood(Long foodId, FoodDto updatedFood) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food does not exist with given id: " + foodId));

        food.setName(updatedFood.getName());
        food.setCalorie(updatedFood.getCalorie());
        food.setFat(updatedFood.getFat());
        food.setCarbohydrate(updatedFood.getCarbohydrate());
        food.setProtein(updatedFood.getProtein());

        Food updatedFoodObj = foodRepository.save(food);

        List<UserFoodLog> userFoodLogs = userFoodLogRepository.findByFoodId(foodId);
        userFoodLogs.forEach(userFoodLog -> {
                    userFoodLog.setFood(updatedFoodObj);
                    userFoodLogRepository.save(userFoodLog);
                });

        return FoodMapper.mapToFoodDto(updatedFoodObj);
    }

    @Override
    public void deleteFood(Long foodId) {
        foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food does not exist with given id: " + foodId));
        foodRepository.deleteById(foodId);
    }
}
