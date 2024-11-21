package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for the {@link UserFoodLog} object.
 */
@Repository
public interface UserFoodLogRepository extends JpaRepository<UserFoodLog, Long> {
    List<UserFoodLog> findByFoodId(Long foodId);
    List<UserFoodLog> findByUserId(Long userId);
    List<UserFoodLog> findByUserIdAndDate(Long userId, LocalDate date);
    List<UserFoodLog> findByDate(LocalDate date);
}
