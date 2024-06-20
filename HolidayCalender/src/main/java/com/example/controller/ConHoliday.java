package com.example.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.EntityHoliday;
import com.example.service.SrvHoliday;

@RestController
public class ConHoliday {

	@Autowired
    private SrvHoliday srvHoliday;

    @PostMapping("/save")
    public ResponseEntity<String> uploadHoliday(@RequestParam("fullDate") String fullDate,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("holidayType") String holidayType,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            LocalDate parsedDate = LocalDate.parse(fullDate);
            String holiday = srvHoliday.saveHoliday(parsedDate, description, holidayType, file);
            return ResponseEntity.ok(holiday);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAll")
    public List<EntityHoliday> getAllHolidays() {
        return srvHoliday.getAllHolidays();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityHoliday> getHolidayById(@PathVariable Long id) {
        Optional<EntityHoliday> holiday = srvHoliday.getHolidayById(id);
        return holiday.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
