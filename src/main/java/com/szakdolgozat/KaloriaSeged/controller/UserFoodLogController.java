package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.service.UserFoodLogService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/userFoodLog")
public class UserFoodLogController {

    private UserFoodLogService userFoodLogService;

    @PostMapping
    public ResponseEntity<UserFoodLogDto> createUserFoodLog(@RequestBody UserFoodLogDto userFoodLogDto) {
        UserFoodLogDto savedUserFoodLog = userFoodLogService.createUserFoodLog(userFoodLogDto);
        return new ResponseEntity<>(savedUserFoodLog, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserFoodLogDto> getUserFoodLogById(@PathVariable("id") Long userFoodLogId) {
        UserFoodLogDto userFoodLogDto = userFoodLogService.getUserFoodLogById(userFoodLogId);
        return ResponseEntity.ok(userFoodLogDto);
    }

    @GetMapping("/searchByUserId")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByUserId(@RequestParam Long userId) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByUserId(userId);
        return ResponseEntity.ok(userFoodLogDto);
    }

    @GetMapping("/searchByUserIdAndDate")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByUserAndDate(@RequestParam Long userId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByUserIdAndDate(userId, date);
        return ResponseEntity.ok(userFoodLogDto);
    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByUserAndDate(
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByDate(date);
        return ResponseEntity.ok(userFoodLogDto);
    }

    @GetMapping
    public ResponseEntity<List<UserFoodLogDto>> getAllUserFoodLogs() {
        List<UserFoodLogDto> userFoodLogs = userFoodLogService.getAllUserFoodLogs();
        return ResponseEntity.ok(userFoodLogs);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserFoodLogDto> updateUserFoodLog(@PathVariable("id") Long userFoodLogId,
                                                      @RequestBody UserFoodLogDto updatedUserFoodLog) {
        UserFoodLogDto userFoodLogDto = userFoodLogService.updateUserFoodLog(userFoodLogId, updatedUserFoodLog);
        return ResponseEntity.ok(userFoodLogDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserFoodLog(@PathVariable("id") Long userFoodLogId) {
        userFoodLogService.deleteUserFoodLog(userFoodLogId);
        return ResponseEntity.ok("UserFoodLog deleted successfully");
    }
}
