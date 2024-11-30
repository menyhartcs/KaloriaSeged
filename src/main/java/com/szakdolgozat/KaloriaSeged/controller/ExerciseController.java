package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for {@link Exercise}. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private ExerciseService exerciseService;

    // Handles the POST request for create Exercise.
    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto) {
        ExerciseDto savedExercise = exerciseService.createExercise(exerciseDto);
        return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
    }

    // Handles the GET request for find Exercise by id.
    @GetMapping("/id/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable("id") Long exerciseId) {
        ExerciseDto exerciseDto = exerciseService.getExerciseById(exerciseId);
        return ResponseEntity.ok(exerciseDto);
    }

    // Handles the GET request for find Exercise by name.
    @GetMapping("/name/{name}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable("name") String name) {
        ExerciseDto exerciseDto = exerciseService.getExerciseByName(name);
        return ResponseEntity.ok(exerciseDto);
    }

    // Handles the GET request for find all Exercises.
    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        List<ExerciseDto> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    // Handles the PUT request for update Exercise.
    @PutMapping("{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable("id") Long exerciseId,
                                                      @RequestBody ExerciseDto updatedExercise) {
        ExerciseDto exerciseDto = exerciseService.updateExercise(exerciseId, updatedExercise);
        return ResponseEntity.ok(exerciseDto);
    }

    // Handles the DELETE request for delete Exercise.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable("id") Long exerciseId) {
        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity.ok("Exercise deleted successfully");
    }
}
