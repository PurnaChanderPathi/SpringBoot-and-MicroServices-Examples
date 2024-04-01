package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserDetails;
import com.example.repo.UserRepo;

@Service
public class ExcelService {

    @Autowired
    private UserRepo userRepository;

    public byte[] generateExcelFromDatabase() {
        List<UserDetails> users = userRepository.findAll();
        return generateExcel(users);
    }

    private byte[] generateExcel(List<UserDetails> users) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("UserDetails");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("userName");
            headerRow.createCell(1).setCellValue("email");
            headerRow.createCell(2).setCellValue("password");
            headerRow.createCell(3).setCellValue("mobileNumber");
            headerRow.createCell(4).setCellValue("address");
            headerRow.createCell(5).setCellValue("status");
            // Add more headers as needed

            for (UserDetails user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getUserName());
                row.createCell(1).setCellValue(user.getEmail());
                row.createCell(2).setCellValue(user.getPassword());
                row.createCell(3).setCellValue(user.getMobileNumber());
                row.createCell(4).setCellValue(user.getAddress());
                row.createCell(5).setCellValue(user.getStatus());
                // Add more user details as needed
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
            return new byte[0];
        }
    }
}
