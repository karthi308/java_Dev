package com.laptop.code.laptop.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class IntakePdfService {
    public String generatePdf() {
        try {
            String htmlContent = readHtmlFromFile("D:/Intel_Ij/JavaService/27-07-2023/LaptopService/src/main/resources/pdfTemplate/IntakePdf.html");
            String branchAddress="No 1, Ponnurangam Salai Injambakkam";
            String branchPincode="chennai-600115";
            String branchMobileNo="76898062389";
            String outputPdfPath = "C:/Users/E838/Desktop/javapdf/IntakePdf.pdf";
            String intakeNo="IOT2308308";
            String customerName="Karthikeyan S";
            String customerMobileNo="Karthikeyan S";
            String alternativeMobileNo="Karthikeyan S";
            String customerAddress="Karthikeyan S";
            String customerState="Karthikeyan S";
            String customerPincode="Karthikeyan S";
            String customerMailId="Karthikeyan S";
            String intakeDate="Karthikeyan S";
            String make="Karthikeyan S";
            String model="Karthikeyan S";
            String serialNo="Karthikeyan S";
            String batteryserialNo="Karthikeyan S";
            String adaptorSerialNo="Karthikeyan S";
            String problemReported="Karthikeyan S";
            String damages="Karthikeyan S";
            String others="Karthikeyan S";

            String populatedHtml = htmlContent
                    .replace("${branAddress}", branchAddress)
                    .replace("${branchPincode}", branchPincode)
                    .replace("${branchMobileNo}", branchMobileNo)
                    .replace("${intakeNo}", intakeNo)
                    .replace("${intakeDate}", intakeDate)
                    .replace("${customerName}", customerName)
                    .replace("${customerMobileNo}", customerMobileNo)
                    .replace("${alternativeMobileNo}", alternativeMobileNo)
                    .replace("${customerAddress}", customerAddress)
                    .replace("${customerState}", customerState)
                    .replace("${customerPincode}", customerPincode)
                    .replace("${customerMailId}", customerMailId)
                    .replace("${make}", make)
                    .replace("${model}", model)
                    .replace("${serialNo}", serialNo)
                    .replace("${batteryserialNo}", batteryserialNo)
                    .replace("${adaptorSerialNo}", adaptorSerialNo)
                    .replace("${problemReported}", problemReported)
                    .replace("${damages}", damages)
                    .replace("${others}", others)
                    ;
            try (OutputStream outputStream = new FileOutputStream(new File(outputPdfPath))) {
                ITextRenderer  renderer = new ITextRenderer();
                renderer.setDocumentFromString(populatedHtml);
                renderer.layout();
                renderer.createPDF(outputStream);
                System.out.println("HTML to PDF conversion complete.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "pdf created ";
    }
    private static String readHtmlFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
