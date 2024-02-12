package com.io.tech.service;

import com.io.tech.entity.BranchStockDetailsEntity;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.BranchStockDetailsRepository;
import com.io.tech.util.Constants;
import com.io.tech.util.StandardResponseUtil;
import com.io.tech.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CustomerStockDetailsService {

    @Autowired
    BranchStockDetailsRepository branchStockRepository;


    public StandardMessageResponse addCustomerStock(String companyId, String intakeNo, String stockId, int price) {
        try {
            if (!Validator.hasData(companyId) && !Validator.hasData(intakeNo) && !Validator.hasData(stockId) && !Validator.hasData(price))
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            BranchStockDetailsEntity details = branchStockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            details.setIntakeNo(intakeNo);
            details.setPrice(price);
            details.setStatus("Waiting");
            branchStockRepository.save(details);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            log.error("Error occurred in addCustomerStock service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }

    }

    public StandardMessageResponse getCustomerStockList(String companyId, String intakeNo) {
        try {
            if (!Validator.hasData(companyId) && !Validator.hasData(intakeNo))
                return StandardResponseUtil.noDataMessage(Constants.REQUIRED_MISSING_PARAMETER);
            List<BranchStockDetailsEntity> list = branchStockRepository.findByIdCompanyIdAndIntakeNoOrderByIdStockIdAsc(companyId, intakeNo);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(list);
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCustomerStockList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getCustomerStockDetails(String companyId, String stockId) {
        try {
            BranchStockDetailsEntity details = branchStockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (!Validator.hasData(details)) return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            return StandardResponseUtil.successResponse(Collections.singletonList(details));
        } catch (Exception e) {
            log.error("Exception occurred in getCustomerStockDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse updateCustomerStockDetails(String companyId, String stockId, int price) {
        try {
            BranchStockDetailsEntity details = branchStockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (!Validator.hasData(details)) return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            details.setPrice(price);
            branchStockRepository.save(details);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
        } catch (Exception e) {
            log.error("Exception occurred in updateCustomerStockDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse removeCustomerStock(String companyId, String stockId) {
        try {
            BranchStockDetailsEntity details = branchStockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (!Validator.hasData(details))
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            details.setStatus("In Stock");
            branchStockRepository.save(details);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
        } catch (Exception e) {
            log.error("Exception occurred in removeCustomerStock service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }
}
