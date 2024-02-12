package com.io.tech.service;

import com.io.tech.entity.BranchDetailsEntity;
import com.io.tech.entity.UserDetailsEntity;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.pojo.UserDetailsPojo;
import com.io.tech.repository.BranchDetailsRepository;
import com.io.tech.repository.UserDetailsId;
import com.io.tech.repository.UserDetailsRepository;
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
public class UserDetailsServices {

    @Autowired
    UserDetailsRepository repository;

    @Autowired
    BranchDetailsRepository branchDetailsRepository;

    public StandardMessageResponse addUser(String companyId, String userId, UserDetailsPojo pojo) {
        try {
            if (validateParams(companyId, pojo)) {
                String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
                BranchDetailsEntity branchDetails = branchDetailsRepository.findByIdCompanyIdAndIdBranchName(companyId, branchName);
                if (Validator.hasData(branchDetails)) {
                    String id = repository.getMaxIdUserId(companyId);
                    if (Validator.hasData(id)) {
                        String integerPart = id.replaceAll("\\D", "");
                        id = String.format("E%03d", (Integer.parseInt(integerPart) + 1));
                    } else id = "E001";
                    UserDetailsEntity entity = new UserDetailsEntity();
                    UserDetailsId ids = new UserDetailsId();
                    ids.setUserId(id);
                    ids.setCompanyId(companyId);
                    entity.setId(ids);
                    entity.setUserName(pojo.getUser_name());
                    entity.setBranchName(branchName);
                    entity.setBranchShortName(CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_SHORT_NAME));
                    entity.setPassword(branchDetails.getBranchShortName() + "@123");
                    entity.setMailId(pojo.getMail_id());
                    entity.setCompanyDetailsAccess(pojo.isCompany_details_access());
                    entity.setSwitchBranchAccess(pojo.isSwitch_branch_access());
                    entity.setUserDetailsAccess(pojo.isUser_details_access());
                    entity.setVendorStockAccess(pojo.isVendor_stock_access());
                    entity.setStatus(Constants.ACTIVE);
                    repository.save(entity);
                    return StandardResponseUtil.successResponse(Collections.singletonList(entity));
                } else return StandardResponseUtil.badRequestResponse(Constants.SOMETHING_WENT_WRONG);
            } else return StandardResponseUtil.noDataMessage(Constants.REQUIRED_MISSING_PARAMETER);
        } catch (Exception e) {
            log.error("Error occurred in addUser service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getUserList(String companyId) {
        try {
            List<UserDetailsEntity> details = repository.findByIdCompanyIdOrderByIdUserId(companyId);
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(Collections.singletonList(details));
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getUserList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getUserDetails(String companyId, String userId) {
        try {
            UserDetailsEntity details = repository.findByIdCompanyIdAndIdUserId(companyId, userId);
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(Collections.singletonList(details));
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getUserList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getUserStatus(String companyId, String status) {
        try {
            List<UserDetailsEntity> details = null;
            if (status.equals(Constants.ALL))
                details = repository.findByIdCompanyIdOrderByIdUserId(companyId);
            else details = repository.findByIdCompanyIdAndStatusOrderByIdUserId(companyId, status);
            if (Validator.hasData(details))
                return StandardResponseUtil.successResponse(details);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getUserStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }


    public StandardMessageResponse updateUserDetails(String companyId, String userId, UserDetailsPojo pojo) {
        try {
            UserDetailsEntity details = repository.findByIdCompanyIdAndIdUserId(companyId, userId);
            if (Validator.hasData(details)) {
                getCompanyDetailsEntity(pojo, details);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateUserDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();

        }
    }

    public StandardMessageResponse removeUserDetails(String companyId, String userId) {
        try {
            UserDetailsEntity details = repository.findByIdCompanyIdAndIdUserId(companyId, userId);
            repository.updateStatusById(companyId, userId, Constants.IN_ACTIVE);
            if (Validator.hasData(details)) return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
            else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in removeUserDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private void getCompanyDetailsEntity(UserDetailsPojo pojo, UserDetailsEntity details) {
        UserDetailsEntity entity = new UserDetailsEntity();
        UserDetailsId id = new UserDetailsId();
        id.setCompanyId(details.getId().getCompanyId());
        id.setUserId(details.getId().getUserId());
        entity.setId(id);
        entity.setBranchShortName(Validator.hasData(pojo.getBranch_short_name()) ? pojo.getBranch_short_name() : details.getBranchShortName());
        entity.setBranchName(Validator.hasData(pojo.getBranch_name()) ? pojo.getBranch_name() : details.getBranchName());
        entity.setUserName(Validator.hasData(pojo.getUser_name()) ? pojo.getUser_name() : details.getUserName());
        entity.setPassword(details.getPassword());
        entity.setMailId(Validator.hasData(pojo.getMail_id()) ? pojo.getMail_id() : details.getMailId());
        entity.setCompanyDetailsAccess(pojo.isCompany_details_access());
        entity.setUserDetailsAccess(pojo.isUser_details_access());
        entity.setSwitchBranchAccess(pojo.isSwitch_branch_access());
        entity.setVendorStockAccess(pojo.isVendor_stock_access());
        entity.setUserKey(Validator.hasData(pojo.getUser_key()) ? pojo.getUser_key() : details.getUserKey());
        entity.setStatus(Validator.hasData(pojo.getStatus()) ? pojo.getStatus() : details.getStatus());
        entity.setProfileImage(Validator.hasData(pojo.getProfile_image()) ? pojo.getProfile_image() : details.getProfileImage());
        repository.save(entity);
    }

    private boolean validateParams(String companyId, UserDetailsPojo pojo) {
        return Validator.hasData(pojo.getUser_name()) && Validator.hasData(pojo.getMail_id()) && Validator.hasData(companyId);
    }
}
