package com.szakdolgozat.KaloriaSeged.dto;

import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for {@link Exercise}.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {

    private Long id;
    private String name;
    private Integer calorie;
    private List<UserExerciseLogDto> exerciseLogs;
}
