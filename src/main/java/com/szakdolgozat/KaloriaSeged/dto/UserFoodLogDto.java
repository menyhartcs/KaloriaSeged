package com.szakdolgozat.KaloriaSeged.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFoodLogDto {

    private Long id;
    private UserDto user;
    private FoodDto food;
    private LocalDate date;
    private Integer amount;
}
