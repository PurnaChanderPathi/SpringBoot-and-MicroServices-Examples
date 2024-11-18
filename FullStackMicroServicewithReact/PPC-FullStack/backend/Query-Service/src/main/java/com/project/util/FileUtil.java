package com.project.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static void saveExcelFile(byte[] excelFile) throws IOException {
        // Define the path where you want to save the file
        Path path = Paths.get("output.xlsx");  // You can specify the full path here if needed (e.g., "C:/files/output.xlsx")

        // Write the byte array to the file
        Files.write(path, excelFile);

        System.out.println("Excel file saved at: " + path.toAbsolutePath());
    }

    public static void main(String[] args) throws IOException {
        // Sample byte[] representing the Excel file (e.g., the output from your exportToExcel method)
        byte[] excelFile = getSampleExcelFile();  // Assume this method returns a valid byte array of Excel data

        // Call the method to save the file
        saveExcelFile(excelFile);
    }

    private static byte[] getSampleExcelFile() {
        // This is just a placeholder method that you should replace with your actual Excel file creation logic.
        return new byte[0];  // Replace with actual byte array
    }
}
