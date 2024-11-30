package com.szakdolgozat.KaloriaSeged.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents the {@link UserExerciseLog} entity in the database.
 * It contains the food logs for the user.
 * It has a relation with the {@link User} and the {@link Exercise} objects.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_exercise_log")
public class UserExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "duration")
    private Integer duration;
}
