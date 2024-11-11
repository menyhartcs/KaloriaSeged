package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);
}
