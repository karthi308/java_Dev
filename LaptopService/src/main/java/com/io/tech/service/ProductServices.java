package com.io.tech.service;

import com.io.tech.entity.BranchDetailsEntity;
import com.io.tech.entity.CompanyDetailsEntity;
import com.io.tech.entity.ProductEntity;
import com.io.tech.pojo.PdfPojo;
import com.io.tech.pojo.ProductPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.BranchDetailsRepository;
import com.io.tech.repository.CompanyDetailsRepository;
import com.io.tech.repository.ProductId;
import com.io.tech.repository.ProductRepository;
import com.io.tech.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ProductServices {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyDetailsRepository companyRepository;

    @Autowired
    BranchDetailsRepository branchRepository;


    public StandardMessageResponse generateIntake(String companyId, String userId, ProductPojo pojo) {
        try {
            if (!validateParams(companyId, pojo))
                return StandardResponseUtil.noDataMessage(Constants.REQUIRED_MISSING_PARAMETER);
            if (!Validator.hasData(companyRepository.findByCompanyId(companyId)))
                return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            ProductEntity details = productRepository.findTopByIdCompanyIdAndBranchNameOrderByYearDesc(companyId, branchName);
            String intakeIndex = (Constants.I + CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_SHORT_NAME));
            String year;
            if (Validator.hasData(details)) {
                String yearMonth = details.getYear().substring(0, 4);
                year = (yearMonth.equals(DateFormatter.getCurrentYearMonth())) ? String.valueOf(Integer.parseInt(details.getYear()) + 1) : DateFormatter.getCurrentYearMonth() + "0001";
            } else year = DateFormatter.getCurrentYearMonth() + "0001";
            pojo.setIntake_no(intakeIndex + year);
            pojo.setBranch_name(branchName);
            CacheUtil.addObjectToCache("product_details" + pojo.getIntake_no(), pojo.toString(),Constants.TTL_IN_FIVE_MINUTES);
            PdfPojo pdfPojo = new PdfPojo();
            byte[] pdf = getPdf(companyId, userId, branchName, pojo);
            pdfPojo.setIntake_no(pojo.getIntake_no());
            pdfPojo.setPdf(pdf);
            pojo.setPdf(pdf);
            addStockDetails(companyId, userId, pojo);
            return StandardResponseUtil.successResponse(Collections.singletonList(pdfPojo));
        } catch (Exception e) {
            log.error("Error occurred in generateIntake service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private ProductEntity addStockDetails(String companyId, String userId, ProductPojo pojo) {
        ProductId id = new ProductId();
        id.setIntakeNo(pojo.getIntake_no());
        id.setCompanyId(companyId);
        ProductEntity entity = new ProductEntity();
        entity.setId(id);
        entity.setMobileNumber(pojo.getMobile_number());
        entity.setAlternativeMobileNumber(Validator.hasData(pojo.getAlternative_mobile_number()) ? pojo.getAlternative_mobile_number() : 0);
        entity.setName(pojo.getName());
        entity.setAddress1(pojo.getAddress1());
        entity.setAddress2(pojo.getAddress2());
        entity.setState(pojo.getState());
        entity.setPincode(pojo.getPincode());
        entity.setMailId(pojo.getMail_id());
        entity.setMake(pojo.getMake());
        entity.setModel(pojo.getModel());
        entity.setLaptopSerialNumber(pojo.getLaptop_serial_number());
        entity.setAdapterSerialNumber(pojo.getAdapter_serial_number());
        entity.setBatterySerialNumber(pojo.getBattery_serial_number());
        entity.setProblemReported(pojo.getProblem_reported());
        entity.setDamages(pojo.getDamages());
        entity.setOthers(pojo.getOthers());
        String year = pojo.getIntake_no().substring(4, 12);
        entity.setYear(year);
        entity.setStatus("New");
        entity.setUserId(userId);
        entity.setBranchName(pojo.getBranch_name());
        entity.setPdf(pojo.getPdf());
        productRepository.save(entity);
        return entity;
    }

    public StandardMessageResponse getProductList(String companyId, String userId) {
        try {
            List<ProductEntity> details = productRepository.findByIdCompanyIdAndBranchNameOrderByYearAsc(companyId, CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME));
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(details);
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getProductList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getLatestProductDetails(String companyId, String branchName) {
        try {
            ProductEntity details = productRepository.findTopByIdCompanyIdAndBranchNameOrderByYearDesc(companyId, branchName);
            if (!Validator.hasData(details))
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            return StandardResponseUtil.successResponse(Collections.singletonList(details));
        } catch (Exception e) {
            log.error("Error occurred in getLatestProductDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getProductDetails(String companyId, String userId, String searchNumber) {
        try {
            ProductEntity details;
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            if (searchNumber.length() == 10)
                details = productRepository.findTopByIdCompanyIdAndMobileNumberAndBranchNameOrderByIdIntakeNoDesc(companyId, Long.parseLong(searchNumber), branchName);
            else
                details = productRepository.findByIdCompanyIdAndIdIntakeNoAndBranchName(companyId, searchNumber, branchName);
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(Collections.singletonList(details));
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getProductDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getProductStatus(String companyId, String userId, String status) {
        try {
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            List<ProductEntity> details = productRepository.findByIdCompanyIdAndBranchNameAndStatusOrderByYearAsc(companyId, branchName, status);
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(details);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getProductStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }


    public StandardMessageResponse getProductStatusDetails(String companyId, String userId, String searchNumber, String status) {
        try {
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            List<ProductEntity> details = null;
            if (searchNumber.length() == 10)
                details = productRepository.getProductDetailsByMobileNo(companyId, searchNumber, branchName, status);
            else
                details = productRepository.getProductDetailsByIntakeNo(companyId, searchNumber, branchName, status);
            if (!Validator.hasData(details))
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            return StandardResponseUtil.successResponse(details);
        } catch (Exception e) {
            log.error("Error occurred in getProductStatusDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse updateProductDetails(String companyId, String userId, ProductPojo pojo) {
        try {
            if (!Validator.hasData(companyId) || !Validator.hasData(pojo.getIntake_no()))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            ProductEntity details = productRepository.findByIdCompanyIdAndIdIntakeNoAndBranchName(companyId, pojo.getIntake_no(), CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME));
            if (Validator.hasData(details)) {
                addProductDetailsEntity(pojo, details);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateProductDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();

        }
    }

    public StandardMessageResponse removeProductDetails(String companyId, String userId, String intakeNo) {
        try {
            if (!Validator.hasData(companyId) && !Validator.hasData(companyId) && !Validator.hasData(companyId))
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            productRepository.removeIntake(companyId, intakeNo, branchName);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
        } catch (Exception e) {
            log.error("Error occurred in removeProductDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();

        }
    }

    private void addProductDetailsEntity(ProductPojo pojo, ProductEntity details) {
        ProductEntity entity = new ProductEntity();
        ProductId id = new ProductId();
        id.setCompanyId(details.getId().getCompanyId());
        id.setIntakeNo(pojo.getIntake_no());
        entity.setId(id);
        entity.setYear(details.getYear());
        entity.setBranchName(details.getBranchName());
        entity.setMobileNumber(details.getMobileNumber());
        entity.setAlternativeMobileNumber(details.getAlternativeMobileNumber());
        entity.setName(details.getName());
        entity.setAddress1(details.getAddress1());
        entity.setAddress2(details.getAddress2());
        entity.setPincode(details.getPincode());
        entity.setState(details.getState());
        entity.setMailId(details.getMailId());
        entity.setMake(details.getMake());
        entity.setModel(details.getModel());
        entity.setLaptopSerialNumber(details.getLaptopSerialNumber());
        entity.setBatterySerialNumber(details.getBatterySerialNumber());
        entity.setAdapterSerialNumber(details.getAdapterSerialNumber());
        entity.setProblemReported(details.getProblemReported());
        entity.setDamages(details.getDamages());
        entity.setOthers(details.getOthers());
        entity.setPrice(Validator.hasData(pojo.getPrice()) ? pojo.getPrice() : details.getPrice());
        entity.setProblemIdentified(Validator.hasData(pojo.getProblem_identified()) ? pojo.getProblem_identified() : details.getProblemIdentified());
        entity.setRejectedReason(Validator.hasData(pojo.getRejected_reason()) ? pojo.getRejected_reason() : details.getRejectedReason());
        entity.setStatus(Validator.hasData(pojo.getStatus()) ? pojo.getStatus() : details.getStatus());
        entity.setUserId(details.getUserId());
        productRepository.save(entity);
    }

    private byte[] getPdf(String companyId, String userId, String branchName, ProductPojo pojo) {
        CompanyDetailsEntity companyDetails = companyRepository.findByCompanyId(companyId);
        BranchDetailsEntity branchDetails = branchRepository.findByIdCompanyIdAndIdBranchName(companyId, branchName);
        String userName = CacheUtil.getObjectFromCache(companyId + userId + Constants.USER_NAME);
        return generateIntake(userName, companyDetails, branchDetails, pojo);
    }

    private byte[] generateIntake(String userName, CompanyDetailsEntity companyDetails, BranchDetailsEntity branchDetails, ProductPojo pojo) {
        try {


            String htmlContent = CommonUtil.readHtmlFromFile("pdf-template/IntakePdf.html");
            String branchAddress1 = branchDetails.getAddress1();
            String branchAddress2 = branchDetails.getAddress2();
            String branchPincode = String.valueOf(branchDetails.getPincode());
            String branchMobileNo = String.valueOf(branchDetails.getMobileNumber());
            long telephoneNumber = branchDetails.getTelephoneNumber();

            String populatedHtml = htmlContent.replace("${companyName}", companyDetails.getCompanyName())
                    .replace("${branAddress1}", branchAddress1)
                    .replace("${branAddress2}", branchAddress2)
                    .replace("${branchPincode}", branchPincode)
                    .replace("${branchMobileNo}", branchMobileNo)
                    .replace("${telephoneNo}", telephoneNumber > 0 ? telephoneNumber + "" : "")
                    .replace("${branchPersonSignature}", userName)
                    .replace("${intakeNo}", pojo.getIntake_no())
                    .replace("${intakeDate}", DateFormatter.getCurrentDate())
                    .replace("${customerName}", pojo.getName())
                    .replace("${customerMobileNo}", String.valueOf(pojo.getMobile_number()))
                    .replace("${alternativeMobileNo}", Validator.hasData(pojo.getAlternative_mobile_number()) ? pojo.getAlternative_mobile_number() + "" : "")
                    .replace("${customerAddress}", pojo.getAddress1() + pojo.getAddress2())
                    .replace("${customerState}", pojo.getState())
                    .replace("${customerPincode}", String.valueOf(pojo.getPincode()))
                    .replace("${customerMailId}", pojo.getMail_id() != null ? pojo.getMail_id() : "")
                    .replace("${make}", pojo.getMake() != null ? pojo.getMake() : "")
                    .replace("${model}", pojo.getModel() != null ? pojo.getModel() : "")
                    .replace("${laptopSerialNo}", pojo.getLaptop_serial_number())
                    .replace("${batterySerialNo}", Validator.hasData(pojo.getAdapter_serial_number()) ? pojo.getAdapter_serial_number() : "")
                    .replace("${adaptorSerialNo}", Validator.hasData(pojo.getAdapter_serial_number()) ? pojo.getAdapter_serial_number() : "")
                    .replace("${problemReported}", pojo.getProblem_reported())
                    .replace("${damages}", pojo.getDamages())
                    .replace("${others}", Validator.hasData(pojo.getOthers()) ? pojo.getOthers() : "");
            return CommonUtil.getPdfByte( populatedHtml);
        } catch (Exception e) {
            log.error("Error Occurred in generatePdf : " + e.getMessage());
        }
        return null;
    }

    private boolean validateParams(String companyId, ProductPojo pojo) {
        return Validator.hasData(companyId) && Validator.hasData(pojo.getMobile_number()) && Validator.hasData(pojo.getName()) && Validator.hasData(pojo.getAddress1()) && Validator.hasData(pojo.getAddress2()) && Validator.hasData(pojo.getPincode()) && Validator.hasData(pojo.getState()) && Validator.hasData(pojo.getMail_id()) && Validator.hasData(pojo.getMake()) && Validator.hasData(pojo.getModel()) && Validator.hasData(pojo.getLaptop_serial_number()) && Validator.hasData(pojo.getProblem_reported()) && Validator.hasData(pojo.getDamages());
    }
}
