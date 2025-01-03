package com.project.controller;

import com.project.Dto.MultiTableSearch;
import com.project.Dto.QueryMultiSearch;
import com.project.entity.Obligor;
import com.project.entity.QueryDetails;
import com.project.service.MultiTableSearchService;
import com.project.service.ObligorService;
import com.project.service.QueryDetailsService;
import com.project.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/query")
@Slf4j
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private QueryDetailsService queryDetailsService;

    @Autowired
    private MultiTableSearchService multiTableSearchService;

    @Autowired
    private ObligorService obligorService;

    @GetMapping("/generateReviewId")
    public String generateReviewId() {
        return queryService.generateReviewId();
    }

//    @GetMapping("/{reviewId}")
//    public QueryDetails findByReviewId(@PathVariable String reviewId) {
//        return queryService.findByReviewId(reviewId);
//    }

    @GetMapping("/getQueryDetailsByReviewId/{reviewId}")
    public QueryDetails getQueryByReviewId(@PathVariable String reviewId){
        return queryService.findByReviewId(reviewId);
    }

    @GetMapping("/{reviewId}")
    public Map<String, Object> findByReviewId(@PathVariable String reviewId, @RequestParam(required = false) String reviewType) {
        Map<String, Object> response = new HashMap<>();
        log.info("Entered findByReviewId with reviewId : {} && reviewType : {}", reviewId, reviewType);

        if (reviewType == null) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Review type must be provided");
            log.warn("Review type is missing.");
            return response;
        }

        if ("reviewId".equals(reviewType)) {
            QueryDetails result = queryService.findByReviewId(reviewId);
            if (result != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Query Details Fetched Successfully...!");
                response.put("result", result);
                log.info("Query Details Fetched Successfully : {}", result);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Failed to Fetch Query Details with reviewId");
                log.warn("Failed To Fetch Query Details with reviewId");
            }
        } else if ("childReviewId".equals(reviewType)) {
            Obligor result = obligorService.getObligorByChildReviewId(reviewId);
            if (result != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Obligor Details Fetched Successfully...!");
                response.put("result", result);
                log.info("Obligor Details Fetched Successfully : {}", result);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Failed to Fetch Obligor Details with childReviewId");
                log.warn("Failed To Fetch Obligor Details with childReviewId");
            }
        } else {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Invalid reviewType value");
            log.warn("Invalid reviewType value: {}", reviewType);
        }

        return response;
    }

    @GetMapping("getAll")
    public List<QueryDetails> getAll() {
        return queryService.findAll();
    }


//    @GetMapping("/query-details")
//    public ResponseEntity<List<QueryDetails>> getQueryDetails(
//            @RequestParam(required = false) String reviewId) {
//        List<QueryDetails> queryDetails = queryDetailsService.getQueryDetails( reviewId);
//        return ResponseEntity.ok(queryDetails);
//    }

    @GetMapping("/query-details")
    public Map<String,Object> getQueryDetails(
            @RequestParam(value = "reviewId", required = false) String reviewId){
        log.info("Enter search Service with reviewId :{}", reviewId);
        Map<String,Object> response = new HashMap<>();
        List<QueryDetails> result = queryDetailsService.getQueryDetails( reviewId);
        if(result.isEmpty()){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", "Query Details not found..!");
            log.warn("Query Details not found in Search Service");
        }else{
            response.put("status", HttpStatus.OK.value() );
            response.put("message", "Query Details Search Fetched Successfully...!");
            response.put("result",result);
            log.info("Query Details  Fetched Successfully...! : {}",result);
        }
        return response;
    }

    @GetMapping("/multiSearchTable")
    public Map<String,Object> getMultiSearchTable(
            @RequestParam(value = "reviewId", required = false) String reviewId,
    @RequestParam(value = "childReviewId", required = false) String childReviewId){
        log.info("Enter multiSearchTable Service with reviewId :{} & childReviewId : {}", reviewId,childReviewId);
        Map<String,Object> response = new HashMap<>();
        QueryMultiSearch result = queryDetailsService.getMultiSearchTable(reviewId,childReviewId);
        log.info("MultiSearch Result : {}",result);
        if(result == null){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", "multiSearch not found..!");
            log.warn("multiSearch not found in Search Service");
        }else{
            response.put("status", HttpStatus.OK.value() );
            response.put("message", "multiSearch Search Fetched Successfully...!");
            response.put("result",result);
            log.info("multiSearch  Fetched Successfully...! : {}",result);
        }
        return response;
    }

//    @GetMapping("/search")
//    public List<QueryDetails> fetchQueryDetails(
//            @RequestParam(value = "groupName", required = false) String groupName,
//            @RequestParam(value = "division", required = false) String division,
//            @RequestParam(value = "reviewId", required = false) String reviewId,
//            @RequestParam(value = "fromDate", required = false) String fromDate,
//            @RequestParam(value = "toDate", required = false) String toDate) {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate from = (fromDate != null) ? LocalDate.parse(fromDate, formatter) : null;
//        LocalDate to = (toDate != null) ? LocalDate.parse(toDate, formatter) : null;
//
//        return queryDetailsService.fetchQueryDetails(groupName, division, reviewId, from, to);
//    }

    @GetMapping("/search")
    public Map<String,Object> fetchQueryDetails(
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "division", required = false) String division,
            @RequestParam(value = "reviewId", required = false) String reviewId,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate) {
        log.info("Enter search Service with reviewId :{}groupName :{}division :{}froDate :{}toDate :{}", reviewId, groupName, division, fromDate, toDate);
        Map<String,Object> response = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = (fromDate != null) ? LocalDate.parse(fromDate, formatter) : null;
        LocalDate to = (toDate != null) ? LocalDate.parse(toDate, formatter) : null;

        List<QueryDetails> result = queryDetailsService.fetchQueryDetails(groupName, division, reviewId, from, to);

        if(result.isEmpty()){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", "Query Details not found..!");
            log.warn("Query Details Search not found in Search Service");
        }else {
            response.put("status", HttpStatus.OK.value() );
            response.put("message", "Query Details Search Fetched Successfully...!");
            response.put("result",result);
            log.info("Query Details Search Fetched Successfully...! : {}",result);
        }
    return response;
    }

//    @GetMapping("/getByRoleAndCreatedBy")
//    public ResponseEntity<Map<String, Object>> findByRoleAndCreatedBy(@RequestParam("role") String role,
//                                                                      @RequestParam("assignedTo") String assignedTo) {
//        log.info("Entered into getByRoleAndCreatedBy method");
//        log.info("role: {}", role);
//
//        List<String> roles = Arrays.asList(role.split(","));
//
//        Map<String, Object> response = new HashMap<>();
//        try {
//            List<QueryDetails> result = queryService.findByRoleAndCreatedBy(roles,assignedTo);
//            log.info("Fetched result: {}", result);
//
//            if (!result.isEmpty()) {
//                response.put("status", HttpStatus.OK.value());
//                response.put("message", "Details Fetched Successfully");
//                response.put("result", result);
//                log.info("Details Fetched Successfully");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                response.put("status", HttpStatus.NOT_FOUND.value());
//                response.put("message", "No Details Found for the given role and createdBy");
//                log.warn("No Details Found for the given role and createdBy");
//                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            log.error("Error occurred while fetching details for role: {} ", role, e);
//            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.put("message", "An error occurred while processing the request");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/getByRoleAndCreatedBy")
    public Map<String,Object> findByRoleAndCreatedBy(@RequestParam("role") String role,
                                                     @RequestParam("assignedTo") String assignedTo) throws Exception {
        log.info("Entered into getByRoleAndCreatedBy method");
        log.info("role: {}", role);
        Map<String,Object> response = new HashMap<>();

        List<String> roles = Arrays.asList(role.split(","));

        try {
            List<QueryDetails> result = queryService.findByRoleAndCreatedBy(roles,assignedTo);
            log.info("Fetched result: {}", result);

            if (!result.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Details Fetched Successfully");
                response.put("result", result);
                log.info("result : {}",result);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "No Details Found for the given role and createdBy");
                log.warn("Failed To Fetch Details with role: {} assignedTo: {}",roles,assignedTo);
            }
            return response;
        } catch (Exception e) {
            throw new Exception("An error occurred while processing the request");
        }

    }

    @GetMapping("/getByRoleAndAssignedTo")
    public Map<String,Object> findByRoleAndAssignedTo(@RequestParam("role") String role,
                                                      @RequestParam("assignedTo") String assignedTo) throws Exception {
        log.info("Entered findByRoleAndAssignedTo");
        log.info("role : {} assignedTo : {}",role,assignedTo);
        Map<String,Object> response = new HashMap<>();

        List<String> roles = Arrays.asList(role.split(","));

        try {
            List<QueryDetails> result = queryService.findByRoleAndAssignedTo(roles,assignedTo);
            log.info("Fetched Details : {}",result);

            if(!result.isEmpty()){
                response.put("status",HttpStatus.OK.value());
                response.put("message","Details Fetched Successfully...!");
                response.put("result",result);
                log.info("result: {}",result);
            }else {
                response.put("status",HttpStatus.NOT_FOUND.value());
                response.put("message","Failed To Fetch Details with roles: "+roles + "assignedTo : "+assignedTo);
                log.warn("Failed To Fetch details with role: {} assignedTo: {}",roles,assignedTo);
            }
            return response;
        }catch (Exception e){
            throw new Exception("An error occurred while processing the request");
        }

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
        headerRow.createCell(6).setCellValue("AssignedTo");
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
            row.createCell(6).setCellValue(queryDetails.getAssignedTo());
            row.createCell(7).setCellValue(queryDetails.getCurrentStatus());
        }

        // Write the output to the ByteArrayOutputStream
        workbook.write(outputStream);
        workbook.close();
    }

    @GetMapping("/multiTableSearch")
    public Map<String,Object>  getCombinedData(@RequestParam String assignedTo)
    {
        log.info("Entered multiTableSearch with body : assignedTo : {} ",assignedTo);
        Map<String,Object> response = new HashMap<>();
        try {
            List<MultiTableSearch> result = multiTableSearchService.getCombinedData(assignedTo);
            if(result.isEmpty()){
                response.put("status",HttpStatus.NOT_FOUND.value());
                response.put("message","Empty Details Fetched ");
                log.warn("Empty Details fetched with assignedTo : {}",assignedTo);
            }else {
                response.put("status",HttpStatus.OK.value());
                response.put("message","MultiTableSearch Fetched Successfully");
                response.put("result",result);
                log.info("MultiTableSearch Fetched Successfully : {}",result);
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

        return response;
    }

    @GetMapping("/multiTableSearchGroupTask")
    public Map<String, Object> getMultiTableSearchGroupTask(@RequestParam String role) {
        log.info("Entered multiTableSearchGroupTask with assignedTo: {}", role);
        Map<String, Object> response = new HashMap<>();
        try {
            List<MultiTableSearch> result = multiTableSearchService.getGroupTasks(role);
            if (result.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "No Details Found for assignedTo: " + role);
                log.warn("No multiTableSearchGroupTask Details fetched for assignedTo: {}", role);
            } else {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "MultiTableSearchGroupTask Fetched Successfully");
                response.put("result", result);
                log.info("multiTableSearchGroupTask Fetched Successfully: {}", result);
            }
        } catch (RuntimeException e) {
            log.error("Error fetching data for assignedTo: {}", role, e);
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
        return response;
    }

}



