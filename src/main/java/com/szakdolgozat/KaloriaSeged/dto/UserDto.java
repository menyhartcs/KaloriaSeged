package com.szakdolgozat.KaloriaSeged.dto;

import com.szakdolgozat.KaloriaSeged.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for {@link User}.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private Integer height;
    private Integer weight;
    private Integer age;
    private Integer calorie;
    private Integer protein;
    private Integer carbohydrate;
    private Integer fat;
    private String role;
    private List<UserFoodLogDto> foodLogs;
}
