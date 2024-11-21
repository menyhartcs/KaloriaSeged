package com.szakdolgozat.KaloriaSeged.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents the {@link UserFoodLog} entity in the database.
 * It contains the food logs for the user.
 * It has a relation with the {@link User} and the {@link Food} objects.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_food_log")
public class UserFoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "amount")
    private Integer amount;
}
