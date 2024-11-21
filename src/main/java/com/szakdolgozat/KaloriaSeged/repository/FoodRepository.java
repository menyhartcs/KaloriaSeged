package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the {@link Food} object.
 */
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);
}
