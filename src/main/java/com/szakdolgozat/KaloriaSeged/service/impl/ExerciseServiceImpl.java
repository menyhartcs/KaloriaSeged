package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.mapper.ExerciseMapper;
import com.szakdolgozat.KaloriaSeged.repository.ExerciseRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserExerciseLogRepository;
import com.szakdolgozat.KaloriaSeged.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation for {@link ExerciseService} interface. Implements CRUD operations.
 */
@AllArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseRepository exerciseRepository;
    private final UserExerciseLogRepository userExerciseLogRepository;

    @Override
    public ExerciseDto createExercise(ExerciseDto exerciseDto) {
        Exercise exercise = ExerciseMapper.mapToExercise(exerciseDto);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.mapToExerciseDto(savedExercise);
    }

    @Override
    public ExerciseDto getExerciseById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with given id: " + exerciseId));
        return ExerciseMapper.mapToExerciseDto(exercise);
    }

    @Override
    public ExerciseDto getExerciseByName(String name) {
        Exercise exercise = exerciseRepository.findByName(name);
        if (exercise == null) {
            return null;
        }
        return ExerciseMapper.mapToExerciseDto(exercise);
    }

    @Override
    public List<ExerciseDto> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(ExerciseMapper::mapToExerciseDto).collect(Collectors.toList());
    }

    @Override
    public ExerciseDto updateExercise(Long exerciseId, ExerciseDto updatedExercise) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with given id: " + exerciseId));

        exercise.setName(updatedExercise.getName());
        exercise.setCalorie(updatedExercise.getCalorie());

        Exercise updatedExerciseObj = exerciseRepository.save(exercise);

        List<UserExerciseLog> userExerciseLogs = userExerciseLogRepository.findByExerciseId(exerciseId);
        userExerciseLogs.forEach(userExerciseLog -> {
                    userExerciseLog.setExercise(updatedExerciseObj);
                    userExerciseLogRepository.save(userExerciseLog);
                });

        return ExerciseMapper.mapToExerciseDto(updatedExerciseObj);
    }

    @Override
    public void deleteExercise(Long exerciseId) {
        exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with given id: " + exerciseId));
        exerciseRepository.deleteById(exerciseId);
    }
}
