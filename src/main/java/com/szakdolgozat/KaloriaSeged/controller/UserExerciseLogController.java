package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.service.UserExerciseLogService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for {@link UserExerciseLog}. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/userExerciseLog")
public class UserExerciseLogController {

    private UserExerciseLogService userExerciseLogService;

    // Handles the POST request for create UserExerciseLog.
    @PostMapping
    public ResponseEntity<UserExerciseLogDto> createUserExerciseLog(@RequestBody UserExerciseLogDto userExerciseLogDto) {
        UserExerciseLogDto savedUserExerciseLog = userExerciseLogService.createUserExerciseLog(userExerciseLogDto);
        return new ResponseEntity<>(savedUserExerciseLog, HttpStatus.CREATED);
    }

    // Handles the GET request for find UserExerciseLog by id.
    @GetMapping("{id}")
    public ResponseEntity<UserExerciseLogDto> getUserExerciseLogById(@PathVariable("id") Long userExerciseLogId) {
        UserExerciseLogDto userExerciseLogDto = userExerciseLogService.getUserExerciseLogById(userExerciseLogId);
        return ResponseEntity.ok(userExerciseLogDto);
    }

    // Handles the GET request for find UserExerciseLog by User id.
    @GetMapping("/searchByUserId")
    public ResponseEntity<List<UserExerciseLogDto>> getUserExerciseLogByUserId(@RequestParam Long userId) {
        List<UserExerciseLogDto> userExerciseLogDto = userExerciseLogService.getUserExerciseLogsByUserId(userId);
        return ResponseEntity.ok(userExerciseLogDto);
    }

    // Handles the GET request for find UserExerciseLog by User id and date.
    @GetMapping("/searchByUserIdAndDate")
    public ResponseEntity<List<UserExerciseLogDto>> getUserExerciseLogByDate(@RequestParam Long userId,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserExerciseLogDto> userExerciseLogDto = userExerciseLogService.getUserExerciseLogsByUserIdAndDate(userId, date);
        return ResponseEntity.ok(userExerciseLogDto);
    }

    // Handles the GET request for find UserExerciseLog by date.
    @GetMapping("/searchByDate")
    public ResponseEntity<List<UserExerciseLogDto>> getUserExerciseLogByDate(
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserExerciseLogDto> userExerciseLogDto = userExerciseLogService.getUserExerciseLogsByDate(date);
        return ResponseEntity.ok(userExerciseLogDto);
    }

    // Handles the GET request for find all the UserExerciseLogs.
    @GetMapping
    public ResponseEntity<List<UserExerciseLogDto>> getAllUserExerciseLogs() {
        List<UserExerciseLogDto> userExerciseLogs = userExerciseLogService.getAllUserExerciseLogs();
        return ResponseEntity.ok(userExerciseLogs);
    }

    // Handles the PUT request for update UserExerciseLog.
    @PutMapping("{id}")
    public ResponseEntity<UserExerciseLogDto> updateUserExerciseLog(@PathVariable("id") Long userExerciseLogId,
                                                      @RequestBody UserExerciseLogDto updatedUserExerciseLog) {
        UserExerciseLogDto userExerciseLogDto = userExerciseLogService.updateUserExerciseLog(userExerciseLogId, updatedUserExerciseLog);
        return ResponseEntity.ok(userExerciseLogDto);
    }

    // Handles the DELETE request for delete UserExerciseLog.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserExerciseLog(@PathVariable("id") Long userExerciseLogId) {
        userExerciseLogService.deleteUserExerciseLog(userExerciseLogId);
        return ResponseEntity.ok("UserExerciseLog deleted successfully");
    }
}
