package com.szakdolgozat.KaloriaSeged.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private String height;
    private String weight;
    private String age;
    private List<UserFoodLogDto> foodLogs;
}
