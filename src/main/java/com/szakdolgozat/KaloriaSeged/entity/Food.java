package com.szakdolgozat.KaloriaSeged.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the {@link Food} entity in the database.
 * It contains food data.
 * It has a relation with the {@link UserFoodLog} object.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "calorie")
    private Integer calorie;
    @Column(name = "fat")
    private Integer fat;
    @Column(name = "carbohydrate")
    private Integer carbohydrate;
    @Column(name = "protein")
    private Integer protein;
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFoodLog> foodLogs = new ArrayList<>();
}
