package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.pojo.AddIntakePojoResponse;
import com.laptop.code.laptop.pojo.CustomerDetailsPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.CustomerDetailsRepository;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.DateFormatter;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IntakeService {
    @Autowired
    CustomerDetailsRepository userDetailsRepository;


    private static Logger logger = LoggerFactory.getLogger(IntakeService.class);

    public StandardResponseMessage addIntake(CustomerDetailsPojo customerDetails){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            String branchName="IOTECR";
            CustomerDetailsEntity userDetailsEntity = new CustomerDetailsEntity();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
            String date = simpleDateFormat.format(new Date());
            String intake = "";
            StringBuffer sbfDate = new StringBuffer(date);
            sbfDate.delete(4, 6);
            long dt = 0;
            dt = userDetailsRepository.countDistinctYDByDateBybranch(sbfDate + "%", branchName);
            System.out.println(dt +" date "+sbfDate);
            if (dt <= 9) {
                intake = "000";
            } else if (dt >= 10 && dt <= 99) {
                intake = "00";
            } else {
                intake = "0";
            }
            userDetailsEntity.setYD(date);
            userDetailsEntity.setIntakeNo(branchName + sbfDate + intake + (dt + 1));
            userDetailsEntity.setName(customerDetails.getName());
            userDetailsEntity.setMobileNumber(customerDetails.getMobileNo());
            if (customerDetails.getAlternativeMobileNo() != null) {
                userDetailsEntity.setMobileNumber(customerDetails.getAlternativeMobileNo());
            }
            userDetailsEntity.setAddress(customerDetails.getAddress());
            userDetailsEntity.setCity(customerDetails.getCity());
            userDetailsEntity.setState(customerDetails.getState());
            userDetailsEntity.setPincode(customerDetails.getPincode());
            userDetailsEntity.setMailId(customerDetails.getMailId());
            userDetailsEntity.setMake(customerDetails.getMake());
            userDetailsEntity.setModel(customerDetails.getModel());
            userDetailsEntity.setLaptopSlNo(customerDetails.getSlNo());
            userDetailsEntity.setBatterySlNo(customerDetails.getBatterySlNo());
            userDetailsEntity.setWithAdapter(customerDetails.isWithAdapter());
            userDetailsEntity.setAdapterSlNo(customerDetails.getAdapterSlNo());
            userDetailsEntity.setProblemReported(customerDetails.getProblem());
            userDetailsEntity.setStatus("New");
            userDetailsEntity.setDamages(customerDetails.getDamages());
            userDetailsEntity.setPrice(0);
            userDetailsEntity.setBranch(branchName);
            userDetailsEntity.setOthers(customerDetails.getOthers());
            customerDetails.setIntakeNo(branchName + sbfDate + intake + (dt + 1));
            userDetailsRepository.save(userDetailsEntity);
            List<CustomerDetailsPojo> customerDetailsList=new ArrayList<>();
            customerDetailsList.add(customerDetails);
            generatePdf(userDetailsEntity);
            AddIntakePojoResponse response = new AddIntakePojoResponse();
            List<AddIntakePojoResponse> responseList = new ArrayList<>();
            response.setCustomerDetails(customerDetailsList);
            response.setPdf("");
            responseList.add(response);
            standardResponseMessage= StandardResposneUtil.successResponse(responseList);
        }
        catch (Exception e){
            logger.error("Error Occurred in Add Intake Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getByStatus(String status){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            String branchName="IOTECR";
            List<CustomerDetailsEntity> customerDetails = userDetailsRepository.getNewStatusByBranch(branchName, status);
            if (Validators.hasData(customerDetails)) {
                standardResponseMessage= StandardResposneUtil.successResponse(customerDetails);
            } else {
               standardResponseMessage= StandardResposneUtil.notFound();
            }
        }
        catch (Exception e){
            logger.info("Error Occurred in Get By Status Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage updateStatus(String intakeNo, String status){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            userDetailsRepository.updateStatus(intakeNo,status);
            standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Successfully Status Updated"));
        }
        catch (Exception e){
            logger.info("Error Occurred in updateStatus Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage updateRejectedStatus(String intakeNo, String rejectedReason){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            userDetailsRepository.updateRejectedStatus(intakeNo, "Rejected", rejectedReason);
            standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Successfully Status Updated"));
        }
        catch (Exception e){
            logger.info("Error Occurred in updateRejectedStatus Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }


    /////////////
    public String generatePdf(CustomerDetailsEntity customerDetails) {
        try {
            String htmlContent = CommonUtil.readHtmlFromFile("pdfTemplate/IntakePdf.html");
            String branchAddress="No 1, Ponnurangam Salai Injambakkam";
            String branchPincode="chennai-600115";
            String branchMobileNo="76898062389";
            String outputPdfPath = "C:/Users/E838/Desktop/javapdf/"+customerDetails.getIntakeNo()+".pdf";

            String populatedHtml = htmlContent
                    .replace("${branchPersonSignature}", "Karthikeyan S")
                    .replace("${branAddress}", branchAddress != null ? branchAddress : "")
                    .replace("${branchPincode}", branchPincode != null ? branchPincode : "")
                    .replace("${branchMobileNo}", branchMobileNo != null ? branchMobileNo : "")
                    .replace("${intakeNo}", customerDetails.getIntakeNo() != null ? customerDetails.getIntakeNo() : "")
                    .replace("${intakeDate}", DateFormatter.getCurrentDate())
                    .replace("${customerName}", customerDetails.getName() != null ? customerDetails.getName() : "")
                    .replace("${customerMobileNo}", customerDetails.getMobileNumber() != 0 ? customerDetails.getMobileNumber()+"" : "")
                    .replace("${alternativeMobileNo}", customerDetails.getAlternativeMobileNumber() != 0 ? customerDetails.getAlternativeMobileNumber()+"" : "")
                    .replace("${customerAddress}", customerDetails.getAddress() != null ? customerDetails.getAddress() : "")
                    .replace("${customerState}", customerDetails.getState() != null ? customerDetails.getState() : "")
                    .replace("${customerPincode}", customerDetails.getPincode() != null ? customerDetails.getPincode() : "")
                    .replace("${customerMailId}", customerDetails.getMailId() != null ? customerDetails.getMailId() : "")
                    .replace("${make}", customerDetails.getMake() != null ? customerDetails.getMake() : "")
                    .replace("${model}", customerDetails.getModel() != null ? customerDetails.getModel() : "")
                    .replace("${serialNo}", customerDetails.getLaptopSlNo() != null ? customerDetails.getLaptopSlNo() : "")
                    .replace("${batteryserialNo}", customerDetails.getBatterySlNo() != null ? customerDetails.getBatterySlNo() : "")
                    .replace("${adaptorSerialNo}", customerDetails.getBatterySlNo() != null ? customerDetails.getBatterySlNo() : "")
                    .replace("${problemReported}", customerDetails.getProblemReported() != null ? customerDetails.getProblemReported() : "")
                    .replace("${damages}", customerDetails.getDamages() != null ? customerDetails.getDamages() : "")
                    .replace("${others}", customerDetails.getOthers() != null ? customerDetails.getOthers() : "");

            byte[] pdf = getPdfByte(populatedHtml);
            System.out.println("pdf "+pdf);

            try {
                // Create a FileOutputStream to write the PDF data to the file
                OutputStream outputStream = new FileOutputStream(new File(outputPdfPath));

                // Write the PDF data to the file
                outputStream.write(pdf);

                // Close the output stream
                outputStream.close();

                System.out.println("PDF file saved successfully to: " + outputPdfPath);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to save the PDF file.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "pdf created ";
    }

    private  byte[] getPdfByte(String htmlContent){
        byte[] pdf =null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            pdf = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error Occurred in getPdfByte :"+e.getMessage());
        }
        return pdf;
    }

}
