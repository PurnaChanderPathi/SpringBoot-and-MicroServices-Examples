package com.purna.controller;

import com.purna.config.SecurityConfig;
import com.purna.dto.StudentDepartmentDto;
import com.purna.entity.CollageDetails;
import com.purna.service.CollageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/CollageDetails")
public class CollageDetailsController {

    @Autowired
    private CollageDetailsService collageDetailsService;

    @Autowired
    private Function<String, byte[]> convertJsonToBytes;

    @Autowired
    private Function<byte[], String> byteToJson;

    @PostMapping("/saveCollageDetails")
    private ResponseEntity<Map<String,Object>> saveCollageDetails(@RequestBody CollageDetails collageDetails){
        Map<String,Object> response = new HashMap<>();
        CollageDetails saveCollageDetails = collageDetailsService.saveCollageDetails(collageDetails);
        response.put("status", HttpStatus.OK.value());
        response.put("message","CollageDetails saved successfully..!");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/save")
    public ResponseEntity<CollageDetails> saveCollageDetails(@RequestBody StudentDepartmentDto dto){
        CollageDetails collageDetails = collageDetailsService.saveCollageDetails(dto);
        return ResponseEntity.ok().body(collageDetails);
    }

    @GetMapping("/JsonToByte")
    public void someMethod(){

        String json = "{\n" +
                "  \"student\": {\n" +
                "    \"studentName\": \"PurnaChander\",\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"dateOfBirth\": \"2024-10-03\",\n" +
                "    \"fatherName\": \"Raghupathi\",\n" +
                "    \"motherName\": \"Krishnaveni\"\n" +
                "  },\n" +
                "  \"department\": {\n" +
                "    \"departmentName\": \"MPCPS\",\n" +
                "    \"departmentCode\": \"MPCPS3343\"\n" +
                "  }\n" +
                "}";

        byte[] byteArray = convertJsonToBytes.apply(json);

        if(byteArray != null){
            System.out.println("Byte array: " + java.util.Arrays.toString(byteArray));
        }else {
            System.out.println("Error converting JSON to byte array...!");
        }

        String jsonBack = byteToJson.apply(byteArray);

        if(jsonBack != null){
            System.out.println("Converted back to JSON: \n"+jsonBack);
        }else {
            System.out.println("Error converting byte to JSON...!");
        }
    }

}
