//package com.example.controller;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.entity.Holiday;
//import com.example.service.HolidayService;
//
//@RestController
//@RequestMapping
//public class HolidayController {
//	
//	@Autowired
//	HolidayService holidayService;
//	
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Holiday> getHolidayById(@PathVariable Long id) {
//        Optional<Holiday> holiday = holidayService.getHolidayById(id);
//        return holiday.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    
//    @PostMapping
//    public Holiday uploadHoliday(@RequestParam String holidayDate,@RequestParam String description,@RequestParam MultipartFile file,@RequestParam String holidayType) throws Exception
//    {
//    	LocalDate convertedDate=LocalDate.parse(holidayDate);
//    	return holidayService.saveHoliday(convertedDate, description, holidayType, file);
//    }
//    
//    @GetMapping("/getAll")
//    public List<Holiday> getAllHolidays(){
//    	return holidayService.getAllHolidays();
//    }
//
////    @PutMapping("/{id}")
////    public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday holidayDetails) {
////        Optional<Holiday> optionalHoliday = holidayService.getHolidayById(id);
////        if (optionalHoliday.isPresent()) {
////            Holiday holiday = optionalHoliday.get();
////            holiday.setFulldate(holidayDetails.getFulldate());
////            holiday.setDescription(holidayDetails.getDescription());
////            holiday.setImage(holidayDetails.getImage());
////            holiday.setHolidayType(holidayDetails.getHolidayType());
////            return ResponseEntity.ok(holidayService.saveHoliday(holiday));
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
////        Optional<Holiday> optionalHoliday = holidayService.getHolidayById(id);
////        if (optionalHoliday.isPresent()) {
////            holidayService.deleteHoliday(id);
////            return ResponseEntity.noContent().build();
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//}
