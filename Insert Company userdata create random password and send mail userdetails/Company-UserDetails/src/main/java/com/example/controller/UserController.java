package com.example.controller;


import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.UserDetailsDto;
import com.example.model.UserDetails;
import com.example.service.ExcelService;
import com.example.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private ExcelService excelService;
	
	@PostMapping("/create")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetailsDto userDetailsDto){
		UserDetails createdUser = userService.createUser(userDetailsDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		
	}
	
    @GetMapping("/userpdf")  // show PDF in output Screen
    public ResponseEntity<byte[]> getAllUsersAsPDF() { // object containing a byte array representing a PDF file.
        byte[] pdfBytes = userService.getAllUsersAsPDF(); // It invokes the getAllUsersAsPDF method of the userService to retrieve the byte array representing the PDF content.
        
        HttpHeaders headers = new HttpHeaders(); // Creates an instance of HttpHeaders to set response headers.
        headers.setContentType(MediaType.APPLICATION_PDF); //  Sets the content type of the response to indicate that it is a PDF file.
        headers.setContentDispositionFormData("filename", "user_details.pdf"); //  Sets the Content-Disposition header to instruct the browser to prompt the user to download the file with the specified filename ("user_details.pdf").
        headers.setContentLength(pdfBytes.length); //  Sets the Content-Length header to specify the length of the PDF content in bytes.
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/userexcel") // show Excel in output Screen
    public ResponseEntity<byte[]> generateExcel() {
        byte[] excelBytes = excelService.generateExcelFromDatabase();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("filename", "UserDetails.xlsx");
        headers.setContentLength(excelBytes.length);

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
    
    
    
    @GetMapping("/download") //download in excel format
    public ResponseEntity<String> downloadExcel() {
        byte[] excelBytes = excelService.generateExcelFromDatabase();

        String filePath = "C:\\Purna\\Excel\\user_details.xlsx";

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(excelBytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download Excel file.");
        }

        return ResponseEntity.ok().body("Excel file downloaded successfully to: " + filePath);
    }
    
    
    @GetMapping("/downloadPdf")
    public ResponseEntity<String> getAllUsersAsPDFdownload() {
        byte[] pdfBytes = userService.getAllUsersAsPDF();

        String filePath = "C:\\Purna\\Excel\\user_details.pdf";

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download PDF file.");
        }

        return ResponseEntity.ok().body("PDF file downloaded successfully to: " + filePath);
    }

}
