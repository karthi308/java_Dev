package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.pojo.AddIntakePojoResponse;
import com.laptop.code.laptop.pojo.CustomerDetailsPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.CustomerDetailsRepository;
import com.laptop.code.laptop.util.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IntakeService {
    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    CacheUtil cache;

    public StandardResponseMessage addIntake(HttpServletRequest request, CustomerDetailsPojo customerDetails) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = request.getHeader(Constant.USER_ID);
            String branchName = cache.getValue(userId.toUpperCase() + "branch");
            CustomerDetailsEntity userDetailsEntity = new CustomerDetailsEntity();
            String yearAndMonth = DateFormatter.getYearAndMonth();
            String existingId = customerDetailsRepository.getMaxStockId(yearAndMonth);
            String numberPart = "";
            if (Validators.hasData(existingId)) {
                numberPart = existingId.replaceAll("[^0-9]", "");
                if (!yearAndMonth.equals(numberPart.substring(0, 4)))
                    numberPart = yearAndMonth + "0000";

            } else
                numberPart = yearAndMonth + "0000";
            System.out.println("naaa " + numberPart);
            int intakeNo = Integer.parseInt(numberPart) + 1;
            userDetailsEntity.setIntakeNo(branchName + intakeNo);
            userDetailsEntity.setYear(intakeNo);
            userDetailsEntity.setName(customerDetails.getName());
            userDetailsEntity.setMobileNumber(customerDetails.getMobileNo());
            if (customerDetails.getAlternativeMobileNo() != null) {
                userDetailsEntity.setMobileNumber(customerDetails.getAlternativeMobileNo());
            }
            userDetailsEntity.setAddress(customerDetails.getStreet());
            userDetailsEntity.setCity(customerDetails.getCity_village_town());
            userDetailsEntity.setState(customerDetails.getState());
            userDetailsEntity.setPincode(customerDetails.getPincode());
            userDetailsEntity.setMailId(customerDetails.getMailId());
            userDetailsEntity.setMake(customerDetails.getMake());
            userDetailsEntity.setModel(customerDetails.getModel());
            userDetailsEntity.setLaptopSlNo(customerDetails.getLaptopSerialNo());
            userDetailsEntity.setBatterySlNo(customerDetails.getBatterySerialNo());
            userDetailsEntity.setAdapterSlNo(customerDetails.getAdapterSerialNo());
            userDetailsEntity.setProblemReported(customerDetails.getProblemReported());
            userDetailsEntity.setStatus("New");
            userDetailsEntity.setDamages(customerDetails.getDamages());
            userDetailsEntity.setPrice(0);
            userDetailsEntity.setBranch(branchName);
            userDetailsEntity.setOthers(customerDetails.getOthers());
            customerDetails.setIntakeNo(userDetailsEntity.getIntakeNo());
            customerDetailsRepository.save(userDetailsEntity);
            List<CustomerDetailsPojo> customerDetailsList = new ArrayList<>();
            customerDetailsList.add(customerDetails);
            generatePdf(userDetailsEntity);
            AddIntakePojoResponse response = new AddIntakePojoResponse();
            List<AddIntakePojoResponse> responseList = new ArrayList<>();
            response.setCustomerDetails(customerDetailsList);
            response.setPdf("");
            responseList.add(response);
            standardResponseMessage = StandardResposneUtil.successResponse(responseList);
        } catch (Exception e) {
            log.error("Error Occurred in Add Intake Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getAllCustomerDetails(HttpServletRequest request, String number) {
        try {
            if (Validators.hasData(number)) {
                String branch = cache.getValue(request.getHeader(Constant.USER_ID) + "branch");
                List<CustomerDetailsEntity> list = new ArrayList<>();
                if (number.length() == 10)
                    list = customerDetailsRepository.findAllByMobileNumber(Long.parseLong(number));
                else
                    list = customerDetailsRepository.findAllByIntakeNo(number);
                if (Validators.hasData(list))
                    return StandardResposneUtil.successResponse(list);
                else
                    return StandardResposneUtil.noDataResponse();
            } else return StandardResposneUtil.badRequestResponse();
        } catch (Exception e) {
            log.error("Error Occurred in getAllCustomerDetails : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }

    }

    public StandardResponseMessage getCustomerDetailsByMobileNo(long mobileNo) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            List<CustomerDetailsEntity> customerDetails = customerDetailsRepository.findByMobileNumber(mobileNo);
            if (Validators.hasData(customerDetails)) {
                standardResponseMessage = StandardResposneUtil.successResponse(customerDetails);
            } else {
                standardResponseMessage = StandardResposneUtil.notFound();
            }
        } catch (Exception e) {
            log.info("Error Occurred in getCustomerDetailsByMobileNo Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getCustomerDetails(HttpServletRequest request, String status) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = request.getHeader("userId");
            String branchName = cache.getValue(userId + "branch");
            List<CustomerDetailsEntity> customerDetails = customerDetailsRepository.getStatusByBranch(branchName, status);
            if (Validators.hasData(customerDetails)) {
                standardResponseMessage = StandardResposneUtil.successResponse(customerDetails);
            } else {
                standardResponseMessage = StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
            }
        } catch (Exception e) {
            log.info("Error Occurred in getCustomerDetails Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getCustomerDetails(HttpServletRequest request, String searchNo, String status) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = request.getHeader("userId");
            String branchName = cache.getValue(userId + "branch");
            List<CustomerDetailsEntity> customerDetails = customerDetailsRepository.getStatusByBranch(branchName, status);
            if (Validators.hasData(customerDetails)) {
                standardResponseMessage = StandardResposneUtil.successResponse(customerDetails);
            } else {
                standardResponseMessage = StandardResposneUtil.notFound();
            }
        } catch (Exception e) {
            log.info("Error Occurred in getCustomerDetails Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }


    /////////////
    public String generatePdf(CustomerDetailsEntity customerDetails) {
        try {
            String htmlContent = CommonUtil.readHtmlFromFile("pdfTemplate/IntakePdf.html");
            String branchAddress = "No 1, Ponnurangam Salai Injambakkam";
            String branchPincode = "chennai-600115";
            String branchMobileNo = "76898062389";
            String outputPdfPath = "C:/Users/E838/Desktop/javapdf/" + customerDetails.getIntakeNo() + ".pdf";

            String populatedHtml = htmlContent
                    .replace("${branchPersonSignature}", "Karthikeyan S")
                    .replace("${branAddress}", branchAddress != null ? branchAddress : "")
                    .replace("${branchPincode}", branchPincode != null ? branchPincode : "")
                    .replace("${branchMobileNo}", branchMobileNo != null ? branchMobileNo : "")
                    .replace("${intakeNo}", customerDetails.getIntakeNo() != null ? customerDetails.getIntakeNo() : "")
                    .replace("${intakeDate}", DateFormatter.getCurrentDate())
                    .replace("${customerName}", customerDetails.getName() != null ? customerDetails.getName() : "")
                    .replace("${customerMobileNo}", customerDetails.getMobileNumber() != 0 ? customerDetails.getMobileNumber() + "" : "")
                    .replace("${alternativeMobileNo}", customerDetails.getAlternativeMobileNumber() != 0 ? customerDetails.getAlternativeMobileNumber() + "" : "")
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

            byte[] pdf = CommonUtil.getPdfByte(populatedHtml);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pdf created ";
    }


}
