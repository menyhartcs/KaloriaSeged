package com.szakdolgozat.KaloriaSeged.dto;

import com.szakdolgozat.KaloriaSeged.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for {@link Food}.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {

    private Long id;
    private String name;
    private Integer calorie;
    private Integer fat;
    private Integer carbohydrate;
    private Integer protein;
    private List<UserFoodLogDto> foodLogs;
}
