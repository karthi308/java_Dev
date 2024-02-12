package com.io.tech.service;

import com.io.tech.entity.BranchDetailsEntity;
import com.io.tech.pojo.BranchDetailsPojo;
import com.io.tech.pojo.BranchNameList;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.BranchDetailsId;
import com.io.tech.repository.BranchDetailsRepository;
import com.io.tech.util.CacheUtil;
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
public class BranchDetailsService {

    @Autowired
    BranchDetailsRepository repository;

    private static BranchDetailsEntity getBranchDetailsEntity(String companyId, BranchDetailsPojo pojo, BranchDetailsEntity details) {
        BranchDetailsEntity entity = new BranchDetailsEntity();
        BranchDetailsId id = new BranchDetailsId();
        id.setCompanyId(companyId);
        id.setBranchName(Validator.hasData(pojo.getBranch_name()) ? pojo.getBranch_name() : details.getId().getBranchName());
        entity.setId(id);
        entity.setBranchShortName(Validator.hasData(pojo.getBranch_short_name()) ? pojo.getBranch_short_name() : details.getBranchShortName());
        entity.setAddress1(Validator.hasData(pojo.getAddress1()) ? pojo.getAddress1() : details.getAddress1());
        entity.setAddress2(Validator.hasData(pojo.getAddress2()) ? pojo.getAddress2() : details.getAddress2());
        entity.setState(Validator.hasData(pojo.getState()) ? pojo.getState() : details.getState());
        entity.setPincode(Validator.hasData(pojo.getPincode()) ? pojo.getPincode() : details.getPincode());
        entity.setMobileNumber(Validator.hasData(pojo.getMobile_number()) ? pojo.getMobile_number() : details.getMobileNumber());
        entity.setTelephoneNumber(Validator.hasData(pojo.getTelephone_number()) ? pojo.getTelephone_number() : details.getTelephoneNumber());
        entity.setStatus(Validator.hasData(pojo.getStatus()) ? pojo.getStatus() : details.getStatus());
        return entity;
    }

    public StandardMessageResponse addBranchDetails(String companyId, BranchDetailsPojo pojo) {
        try {
            if (!validateParams(companyId, pojo))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            BranchDetailsEntity details = repository.findByIdCompanyIdAndIdBranchName(companyId, pojo.getBranch_name());
            if (!Validator.hasData(details)) {
                BranchDetailsEntity entity = new BranchDetailsEntity();
                BranchDetailsId id = new BranchDetailsId();
                id.setCompanyId(companyId);
                id.setBranchName(pojo.getBranch_name());
                entity.setId(id);
                entity.setBranchShortName(pojo.getBranch_short_name());
                entity.setAddress1(pojo.getAddress1());
                entity.setAddress2(pojo.getAddress2());
                entity.setState(pojo.getState());
                entity.setPincode(pojo.getPincode());
                entity.setMobileNumber(pojo.getMobile_number());
                entity.setTelephoneNumber(pojo.getTelephone_number());
                entity.setStatus(Constants.ACTIVE);
                repository.save(entity);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_ADDED);
            } else
                return StandardResponseUtil.noDataMessage("Branch already exists");
        } catch (Exception e) {
            log.error("Error occurred in addCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchList(String companyId) {
        try {
            List<BranchDetailsEntity> list = repository.findByIdCompanyId(companyId);
            if (Validator.hasData(list))
                return StandardResponseUtil.successResponse(list);
            else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getBranchList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchNameList(String companyId, String userId) {
        try {
            List<String> list = repository.findAllBranchNames(companyId);
            if (Validator.hasData(list)) {
                String currentBranch = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
                BranchNameList nameList = new BranchNameList();
                nameList.setBranchNameList(list);
                nameList.setCurrentBranch(currentBranch);
                return StandardResponseUtil.successResponse(Collections.singletonList(nameList));
            } else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getBranchNameList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchDetails(String companyId, String branchName) {
        try {
            BranchDetailsEntity list = repository.findByIdCompanyIdAndIdBranchName(companyId, branchName);
            if (Validator.hasData(list))
                return StandardResponseUtil.successResponse(Collections.singletonList(list));
            else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getBranchStatus(String companyId, String status) {
        try {
            List<BranchDetailsEntity> list = repository.findByIdCompanyIdAndStatus(companyId, status);
            if (Validator.hasData(list))
                return StandardResponseUtil.successResponse(list);
            else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in findCompanyDetailsByStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse updateBranchDetails(String companyId, BranchDetailsPojo pojo) {
        try {
            BranchDetailsEntity details = repository.findByIdCompanyIdAndIdBranchName(companyId, pojo.getBranch_name());
            if (Validator.hasData(details)) {
                BranchDetailsEntity entity = getBranchDetailsEntity(companyId, pojo, details);
                repository.save(entity);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse removeBranchDetails(String companyId, String branchName) {
        try {
            if (Validator.hasData(companyId)) {
                repository.updateStatusByCompanyId(companyId, branchName, "In Active");
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
            } else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse switchBranchName(String companyId, String userId, String branchName) {
        try {
            if (Validator.hasData(companyId)) {
                CacheUtil.addObjectToCache(companyId + userId + Constants.BRANCH_NAME, branchName, Constants.TTL_IN_TWELVE_HOURS);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_SWITCHED);
            } else
                return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in switchBranchName service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private boolean validateParams(String companyId, BranchDetailsPojo pojo) {
        return Validator.hasData(companyId) && Validator.hasData(pojo.getBranch_name()) && Validator.hasData(pojo.getBranch_short_name()) && Validator.hasData(pojo.getAddress1()) && Validator.hasData(pojo.getAddress2()) && Validator.hasData(pojo.getPincode()) && Validator.hasData(pojo.getMobile_number());
    }
}
