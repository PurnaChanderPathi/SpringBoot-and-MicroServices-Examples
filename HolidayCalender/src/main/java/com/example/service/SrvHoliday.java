package com.example.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.EntityHoliday;
import com.example.repo.RepoHoliday;

@Service
public class SrvHoliday {

	@Autowired
    private RepoHoliday repoHoliday;

    public String saveHoliday(LocalDate fullDate, String description, String holidayType, MultipartFile file) throws IOException {
        if(repoHoliday.isExists(fullDate)) {
        	return "Already Exists";
        }
    	EntityHoliday holiday = new EntityHoliday();
        holiday.setFullDate(fullDate);
        holiday.setDescription(description);
        holiday.setHolidayType(holidayType);
        holiday.setImage(file.getBytes());

        return repoHoliday.save(holiday).toString();
    }

    public List<EntityHoliday> getAllHolidays() {
        List<EntityHoliday> holidays = repoHoliday.findAll();
//        for (EntityHoliday holiday : holidays) {
//            holiday.setImageBase64(Base64.getEncoder().encodeToString(holiday.getImage()));
//        }
        return holidays;
    }

    public Optional<EntityHoliday> getHolidayById(Long id) {
        Optional<EntityHoliday> holiday = repoHoliday.findById(id);
        //holiday.ifPresent(h -> h.setImageBase64(Base64.getEncoder().encodeToString(h.getImage())));
        return holiday;
    }
}
