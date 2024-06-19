package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Holiday;
import com.example.repo.HolidayRepository;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    private final String UPLOAD_DIR = "C:\\Purna\\ImageUpload";

    public HolidayService() {
        // Ensure the upload directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
    }

    public Holiday saveHoliday(LocalDate fullDate, String description, String holidayType, MultipartFile file) throws Exception {
        String fileName = fullDate.toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        Holiday holiday = new Holiday();
        holiday.setFullDate(fullDate);
        holiday.setDescription(description);
        holiday.setHolidayType(holidayType);
        holiday.setFilePath(filePath.toString());

        return holidayRepository.save(holiday);
    }


    public List<Holiday> getAllHolidays() {
        List<Holiday> holidays = holidayRepository.findAll();
        for (Holiday holiday : holidays) {
            String filePath = holiday.getFilePath();
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                holiday.setFileBase64(encodedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return holidays;
    }

    public Optional<Holiday> getHolidayById(Long id) {
        return holidayRepository.findById(id);
    }
}
