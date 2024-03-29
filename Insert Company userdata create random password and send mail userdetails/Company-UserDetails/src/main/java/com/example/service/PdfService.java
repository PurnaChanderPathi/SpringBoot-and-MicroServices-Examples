package com.example.service;

import com.example.model.UserDetails;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generatePDF(List<UserDetails> userDetailsList) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        
        // Add title
        document.add(new Paragraph("User Details"));

        // Add user details
        for (UserDetails userDetails : userDetailsList) {
            document.add(new Paragraph("UserName: " + userDetails.getUserName()));
            document.add(new Paragraph("Email: " + userDetails.getEmail()));
            document.add(new Paragraph("Password: " + userDetails.getPassword()));
            document.add(new Paragraph("MobileNumber: " + userDetails.getMobileNumber()));
            document.add(new Paragraph("Address: " + userDetails.getAddress()));
            document.add(new Paragraph("Status: " + userDetails.getStatus()));
            // Add other user details as needed
            document.add(new Paragraph("\n")); // Add some space between users
        }

        document.close();
        
        return byteArrayOutputStream.toByteArray();
    }
}
