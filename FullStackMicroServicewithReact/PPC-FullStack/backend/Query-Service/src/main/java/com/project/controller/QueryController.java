package com.project.controller;

import com.project.entity.QueryDetails;
import com.project.service.QueryDetailsService;
import com.project.service.QueryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private QueryDetailsService queryDetailsService;

    @GetMapping("/generateReviewId")
    public String generateReviewId() {
        return queryService.generateReviewId();
    }

    @GetMapping("/{reviewId}")
    public QueryDetails findByReviewId(@PathVariable String reviewId) {
        return queryService.findByReviewId(reviewId);
    }

    @GetMapping("getAll")
    public List<QueryDetails> getAll() {
        return queryService.findAll();
    }


    @GetMapping("/query-details")
    public ResponseEntity<List<QueryDetails>> getQueryDetails(
            @RequestParam(required = false) String childReviewId,
            @RequestParam(required = false) String reviewId) {
        List<QueryDetails> queryDetails = queryDetailsService.getQueryDetails(childReviewId, reviewId);
        return ResponseEntity.ok(queryDetails);
    }

    @GetMapping("/search")
    public List<QueryDetails> fetchQueryDetails(
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "reviewId", required = false) String reviewId,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = (fromDate != null) ? LocalDate.parse(fromDate, formatter) : null;
        LocalDate to = (toDate != null) ? LocalDate.parse(toDate, formatter) : null;

        return queryDetailsService.fetchQueryDetails(groupName, division, reviewId, from, to);
    }

    @GetMapping("/download-query-details")
    public ResponseEntity<byte[]> downloadQueryDetails(
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "reviewId", required = false) String reviewId,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) LocalDate toDate) throws IOException {

        // Step 1: Fetch data using the provided parameters
        List<QueryDetails> queryDetailsList = queryDetailsService.fetchQueryDetails(groupName, division, reviewId, fromDate, toDate);

        // Step 2: Generate Excel file from the data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        createExcelFile(queryDetailsList, byteArrayOutputStream);

        String filename = "querydetails_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);



        return ResponseEntity.ok()
                .headers(headers)
                .body(byteArrayOutputStream.toByteArray());
    }

    private void createExcelFile(List<QueryDetails> queryDetailsList, ByteArrayOutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("QueryDetails");

        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true); // Set font to bold
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setFont(headerFont);

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Review ID");
        headerRow.createCell(1).setCellValue("Division");
        headerRow.createCell(2).setCellValue("Group Name");
        headerRow.createCell(3).setCellValue("Created By");
        headerRow.createCell(4).setCellValue("Created Date");
        headerRow.createCell(5).setCellValue("Role");
        headerRow.createCell(6).setCellValue("Assigned To User");
        headerRow.createCell(7).setCellValue("CurrentStatus");

        for (int i = 0; i < 8; i++) {
            headerRow.getCell(i).setCellStyle(headerCellStyle);
        }

        // Set the column width (optional, you can adjust the width as needed)
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 25 * 256);
        sheet.setColumnWidth(7, 20 * 256);

        // Populate data rows
        int rowNum = 1;
        for (QueryDetails queryDetails : queryDetailsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(queryDetails.getReviewId());
            row.createCell(1).setCellValue(queryDetails.getDivision());
            row.createCell(2).setCellValue(queryDetails.getGroupName());
            row.createCell(3).setCellValue(queryDetails.getCreatedBy());
            row.createCell(4).setCellValue(queryDetails.getCreatedDate().toString());
            row.createCell(5).setCellValue(queryDetails.getRole());
            row.createCell(6).setCellValue(queryDetails.getAssignedToUser());
            row.createCell(7).setCellValue(queryDetails.getCurrentStatus());
        }

        // Write the output to the ByteArrayOutputStream
        workbook.write(outputStream);
        workbook.close();
    }
}
