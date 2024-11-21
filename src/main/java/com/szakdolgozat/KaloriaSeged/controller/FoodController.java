package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for {@link Food}. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/food")
public class FoodController {

    private FoodService foodService;

    // Handles the POST request for create Food.
    @PostMapping
    public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto foodDto) {
        FoodDto savedFood = foodService.createFood(foodDto);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    // Handles the GET request for find Food by id.
    @GetMapping("/id/{id}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable("id") Long foodId) {
        FoodDto foodDto = foodService.getFoodById(foodId);
        return ResponseEntity.ok(foodDto);
    }

    // Handles the GET request for find Food by name.
    @GetMapping("/name/{name}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable("name") String name) {
        FoodDto foodDto = foodService.getFoodByName(name);
        return ResponseEntity.ok(foodDto);
    }

    // Handles the GET request for find all Foods.
    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        List<FoodDto> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    // Handles the PUT request for update Food.
    @PutMapping("{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable("id") Long foodId,
                                                      @RequestBody FoodDto updatedFood) {
        FoodDto foodDto = foodService.updateFood(foodId, updatedFood);
        return ResponseEntity.ok(foodDto);
    }

    // Handles the DELETE request for delete Food.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFood(@PathVariable("id") Long foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.ok("Food deleted successfully");
    }
}
