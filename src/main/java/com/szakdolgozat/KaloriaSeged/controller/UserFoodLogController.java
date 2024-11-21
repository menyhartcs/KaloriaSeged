package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.service.UserFoodLogService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for {@link UserFoodLog}. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/userFoodLog")
public class UserFoodLogController {

    private UserFoodLogService userFoodLogService;

    // Handles the POST request for create UserFoodLog.
    @PostMapping
    public ResponseEntity<UserFoodLogDto> createUserFoodLog(@RequestBody UserFoodLogDto userFoodLogDto) {
        UserFoodLogDto savedUserFoodLog = userFoodLogService.createUserFoodLog(userFoodLogDto);
        return new ResponseEntity<>(savedUserFoodLog, HttpStatus.CREATED);
    }

    // Handles the GET request for find UserFoodLog by id.
    @GetMapping("{id}")
    public ResponseEntity<UserFoodLogDto> getUserFoodLogById(@PathVariable("id") Long userFoodLogId) {
        UserFoodLogDto userFoodLogDto = userFoodLogService.getUserFoodLogById(userFoodLogId);
        return ResponseEntity.ok(userFoodLogDto);
    }

    // Handles the GET request for find UserFoodLog by User id.
    @GetMapping("/searchByUserId")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByUserId(@RequestParam Long userId) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByUserId(userId);
        return ResponseEntity.ok(userFoodLogDto);
    }

    // Handles the GET request for find UserFoodLog by User id and date.
    @GetMapping("/searchByUserIdAndDate")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByDate(@RequestParam Long userId,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByUserIdAndDate(userId, date);
        return ResponseEntity.ok(userFoodLogDto);
    }

    // Handles the GET request for find UserFoodLog by date.
    @GetMapping("/searchByDate")
    public ResponseEntity<List<UserFoodLogDto>> getUserFoodLogByDate(
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserFoodLogDto> userFoodLogDto = userFoodLogService.getUserFoodLogsByDate(date);
        return ResponseEntity.ok(userFoodLogDto);
    }

    // Handles the GET request for find all the UserFoodLogs.
    @GetMapping
    public ResponseEntity<List<UserFoodLogDto>> getAllUserFoodLogs() {
        List<UserFoodLogDto> userFoodLogs = userFoodLogService.getAllUserFoodLogs();
        return ResponseEntity.ok(userFoodLogs);
    }

    // Handles the PUT request for update UserFoodLog.
    @PutMapping("{id}")
    public ResponseEntity<UserFoodLogDto> updateUserFoodLog(@PathVariable("id") Long userFoodLogId,
                                                      @RequestBody UserFoodLogDto updatedUserFoodLog) {
        UserFoodLogDto userFoodLogDto = userFoodLogService.updateUserFoodLog(userFoodLogId, updatedUserFoodLog);
        return ResponseEntity.ok(userFoodLogDto);
    }

    // Handles the DELETE request for delete UserFoodLog.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserFoodLog(@PathVariable("id") Long userFoodLogId) {
        userFoodLogService.deleteUserFoodLog(userFoodLogId);
        return ResponseEntity.ok("UserFoodLog deleted successfully");
    }
}
