package com.io.tech.service;

import com.io.tech.entity.BranchStockDetailsEntity;
import com.io.tech.pojo.BranchStockDetailsPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.BranchStockDetailsRepository;
import com.io.tech.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BranchStockDetailsService {

    @Autowired
    BranchStockDetailsRepository repository;

    public StandardMessageResponse getBranchStockList(String companyId, String userId) {
        try {
            if (!Validator.hasData(companyId))
                return StandardResponseUtil.noDataMessage(Constants.REQUIRED_MISSING_PARAMETER);
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            List<BranchStockDetailsEntity> list = repository.findByIdCompanyIdAndBranchNameOrderByIdStockIdAsc(companyId, branchName);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(Collections.singletonList(list));
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getBranchStockList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchDetails(String companyId, String stockId) {
        try {
            if (!Validator.hasData(stockId) && !Validator.hasData(companyId))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            BranchStockDetailsEntity list = repository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(Collections.singletonList(list));
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchStockStatus(String companyId, String userId, String status) {
        try {
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
            List<BranchStockDetailsEntity> list = repository.findByIdCompanyIdAndBranchNameAndStatus(companyId, branchName, status);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(list);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in findCompanyDetailsByStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }


    public StandardMessageResponse updateBranchStockDetails(String companyId, String userId, BranchStockDetailsPojo pojo) {
        try {
            if (validateParams(companyId, pojo.getStock_id()) && !Validator.hasData(pojo.getTransfer_to()))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            BranchStockDetailsEntity details = repository.findByIdCompanyIdAndIdStockId(companyId, pojo.getStock_id());
            if (Validator.hasData(details)) {
                details.setBranchName(pojo.getTransfer_to());
                details.setTransferredFrom(CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME));
                repository.save(details);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateBranchStockDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private boolean validateParams(String companyId, String stockId) {
        return !Validator.hasData(companyId) || !Validator.hasData(stockId);
    }
}
