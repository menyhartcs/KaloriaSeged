package com.szakdolgozat.KaloriaSeged.dto;

import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data transfer object for {@link UserExerciseLog}.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserExerciseLogDto {

    private Long id;
    private UserDto user;
    private ExerciseDto exercise;
    private LocalDate date;
    private Integer duration;
}
