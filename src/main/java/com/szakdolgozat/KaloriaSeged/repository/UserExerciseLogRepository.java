package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for the {@link UserExerciseLog} object.
 */
@Repository
public interface UserExerciseLogRepository extends JpaRepository<UserExerciseLog, Long> {
    List<UserExerciseLog> findByExerciseId(Long exerciseId);
    List<UserExerciseLog> findByUserId(Long userId);
    List<UserExerciseLog> findByUserIdAndDate(Long userId, LocalDate date);
    List<UserExerciseLog> findByDate(LocalDate date);
}
