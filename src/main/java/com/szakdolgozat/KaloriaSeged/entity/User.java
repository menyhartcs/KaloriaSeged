package com.szakdolgozat.KaloriaSeged.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the {@link User} entity in the database.
 * It contains user data.
 * It has a relation with the {@link UserFoodLog} object.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "gender")
    private String gender;
    @Column(name = "height")
    private Integer height;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "age")
    private Integer age;
    @Column(name = "calorie")
    private Integer calorie;
    @Column(name = "protein")
    private Integer protein;
    @Column(name = "carbohydrate")
    private Integer carbohydrate;
    @Column(name = "fat")
    private Integer fat;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFoodLog> foodLogs = new ArrayList<>();
}
