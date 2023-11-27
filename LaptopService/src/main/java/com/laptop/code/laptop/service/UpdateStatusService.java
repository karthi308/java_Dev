package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.repository.CustomerDetailsRepository;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.DateFormatter;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Slf4j
@Service
public class UpdateStatusService {
    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;

    public StandardResponseMessage problemIdentified(String intakeNo, String problemIdentified, long price) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            if (Validators.hasData(intakeNo) && Validators.hasData(problemIdentified) && Validators.hasData(price)) {
                customerDetailsRepository.problemIdentified(intakeNo, problemIdentified, price);
                standardResponseMessage = StandardResposneUtil.successMessage("Successfully Updated");
            } else
                standardResponseMessage = StandardResposneUtil.notFound();
        } catch (Exception e) {
            log.error("Error Occurred in problemIdentified Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }


    public StandardResponseMessage updateStatus(String intakeNo, String status) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            customerDetailsRepository.updateStatus(intakeNo, status);
            standardResponseMessage = StandardResposneUtil.successMessage("Successfully Status Updated");
        } catch (Exception e) {
            log.info("Error Occurred in updateStatus Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage updateApprovedStatus(String intakeNo, long price) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            customerDetailsRepository.updateApprovedStatus(intakeNo, price);
            standardResponseMessage = StandardResposneUtil.successMessage("Successfully Status Updated");
        } catch (Exception e) {
            log.info("Error Occurred in updateStatus Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage updateRejectedStatus(String intakeNo, String rejectedReason) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            customerDetailsRepository.updateRejectedStatus(intakeNo, "Rejected", rejectedReason);
            standardResponseMessage = StandardResposneUtil.successMessage("Successfully Status Updated");
        } catch (Exception e) {
            log.info("Error Occurred in updateRejectedStatus Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage updateHandOutReadyStatus(String intakeNo) {
        try {
            List<BranchStockDetailsEntity> stockList = branchStockDetailsRepository.findAllByIntakeNo(intakeNo);
            if (Validators.hasData(stockList)) {
                customerDetailsRepository.updateHandoutStatus(intakeNo, "Delivered");
                Runnable pdfGenerationTask = () -> {
                    generateInvoicePdf(intakeNo, stockList);
                };
                Thread thread = new Thread(pdfGenerationTask);
                thread.start();
                return StandardResposneUtil.successMessage("Successfully Delivered");
            } else
                return StandardResposneUtil.badRequestResponse();

        } catch (Exception e) {
            log.error("Error in updateHandOutReadyStatus Service : " + e);
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    private void generateInvoicePdf(String intakeNo, List<BranchStockDetailsEntity> stockList) {
        CustomerDetailsEntity customerDetails = customerDetailsRepository.findByIntakeNo(intakeNo);
        String htmlContent = CommonUtil.readHtmlFromFile("pdfTemplate/InvoicePdf.html");
        String branchAddress = "No 1, Ponnurangam Salai Injambakkam";
        String branchPinCode = "chennai-600115";
        String branchMobileNo = "76898062389";
        String outputPdfPath = "C:/Users/E838/Desktop/javapdf/invoice-" + intakeNo + ".pdf";


        htmlContent = getTableData(stockList, htmlContent);
        htmlContent = htmlContent.replace("${branAddress}", branchAddress != null ? branchAddress : "")
                .replace("${branchPincode}", branchPinCode != null ? branchPinCode : "")
                .replace("${branchMobileNo}", branchMobileNo != null ? branchMobileNo : "")
                .replace("${intakeNo}", intakeNo != null ? intakeNo : "")
                .replace("${invoiceNo}", "LSI23100001")
                .replace("${intakeDate}", DateFormatter.getCurrentDate())
                .replace("${customerName}", customerDetails.getName() != null ? customerDetails.getName() : "")
                .replace("${customerMobileNo}", customerDetails.getMobileNumber() != 0 ? customerDetails.getMobileNumber() + "" : "")
                .replace("${alternativeMobileNo}", customerDetails.getAlternativeMobileNumber() != 0 ? customerDetails.getAlternativeMobileNumber() + "" : "")
                .replace("${customerAddress}", customerDetails.getAddress() != null ? customerDetails.getAddress() : "")
                .replace("${customerState}", customerDetails.getState() != null ? customerDetails.getState() : "")
                .replace("${customerPincode}", customerDetails.getPincode() != null ? customerDetails.getPincode() : "")
                .replace("${totalAmount}", "3008")
                .replace("${customerMailId}", customerDetails.getMailId() != null ? customerDetails.getMailId() : "");
        byte[] pdf = CommonUtil.getPdfByte(htmlContent);

        try {
            OutputStream outputStream = new FileOutputStream(new File(outputPdfPath));
            outputStream.write(pdf);
            outputStream.close();
            System.out.println("PDF file saved successfully to: " + outputPdfPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save the PDF file.");
        }
    }

    private List<BranchStockDetailsEntity> getStockList(StatusPojo statusPojo) {
//        List<BranchStockDetailsEntity> stockDetailsList = new ArrayList<>();
//        for (String stockId : statusPojo.getStockId()) {
//            BranchStockDetailsEntity stockDetails = branchStockDetailsRepository.findByStockId(stockId);
//            if (Validators.hasData(stockDetails)) {
//                stockDetailsList.add(stockDetails);
//            }
//        }
        return null;
    }

    private String getTableData(List<BranchStockDetailsEntity> stockList, String htmlContent) {
        String tableData = "<tr>\n" +
                "      <td>{{description}}</td>\n" +
                "      <td>{{qty}}</td>\n" +
                "      <td>{{unitPrice}}</td>\n" +
                "    </tr>";
        StringBuilder dynamicRows = new StringBuilder();
        int finalPrice = 0;
        StringBuilder sb = new StringBuilder();
        for (BranchStockDetailsEntity stockDetails : stockList) {
            dynamicRows.append("<tr>\n");
            dynamicRows.append("<td>").append(stockDetails.getStockName() + " " + stockDetails.getWarranty()).append("</td>\n");
            dynamicRows.append("<td>").append("1").append("</td>\n");
            dynamicRows.append("<td>").append(stockDetails.getPrice()).append("</td>\n");
            dynamicRows.append("</tr>\n");
            finalPrice += stockDetails.getPrice();
            sb.append(dynamicRows);
            branchStockDetailsRepository.updateStockStatus(stockDetails.getStockId(), "Sold Out");
        }
        htmlContent = htmlContent.replace("${tableData}", sb)
                .replace("${totalAmount}", finalPrice + "");
        return htmlContent;
    }

    private void getSpareCount() {
        String[] usernames = {"john", "jane", "john", "jane", "smith", "jane", "john", "doe", "smith", "jane"};
        Map<String, Integer> usernameCount = new HashMap<>();

        for (String username : usernames) {
            if (usernameCount.containsKey(username)) {
                int count = usernameCount.get(username);
                usernameCount.put(username, count + 1);
            } else {
                usernameCount.put(username, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : usernameCount.entrySet()) {
            String username = entry.getKey();
            int count = entry.getValue();
            if (count > 1) {
                System.out.println("Username: " + username + " - Count: " + count);
            }
        }
    }


}
