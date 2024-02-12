package com.io.tech.service;

import com.io.tech.entity.UserDetailsEntity;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.pojo.UserDetailsPojo;
import com.io.tech.pojo.UserKeyPojo;
import com.io.tech.repository.UserDetailsId;
import com.io.tech.repository.UserDetailsRepository;
import com.io.tech.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class AuthnService {


    @Autowired
    UserDetailsRepository userDetailsRepository;


    public String verifyJwt(String companyId, String userId, String jwt) {
        try {
            UserDetailsEntity entity = userDetailsRepository.findByIdCompanyIdAndIdUserIdAndUserKey(companyId, userId, jwt);
            if (Validator.hasData(entity)) return "Verified";
            else return "Not verified";
        } catch (Exception e) {
            log.error("Exception occurred in verifyJwt Controller : " + e.getMessage());
            return null;
        }
    }


    public StandardMessageResponse login(String companyId, String userId, String password) {
        StandardMessageResponse response = null;
        try {
            UserDetailsEntity details = userDetailsRepository.findByIdCompanyIdAndIdUserId(companyId, userId);
            if (!Validator.hasData(details) || !details.getId().getUserId().equals(userId))
                return StandardResponseUtil.noDataMessage("Invalid userId or password");
            if (!details.getPassword().equals(password)) return StandardResponseUtil.noDataMessage("Invalid password");
            return getResponse(companyId, userId, details);
        } catch (Exception e) {
            log.error("Exception occurred in login service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse changePassword(String companyId, String userId, String oldPassword, String newPassword, String confirmPassword) {
        try {
            UserDetailsEntity details = userDetailsRepository.findByIdCompanyIdAndIdUserId(companyId, userId);
            if (Validator.hasData(details) && !oldPassword.equals(details.getPassword()))
                return StandardResponseUtil.noDataMessage(Constants.INVALID_OLD_PASSWORD);
            if (!newPassword.equals(confirmPassword))
                return StandardResponseUtil.noDataMessage(Constants.PASSWORD_NOT_MATCHING);
            if (oldPassword.equals(newPassword))
                return StandardResponseUtil.noDataMessage("Old password must be different from new one");
            details.setPassword(newPassword);
            userDetailsRepository.save(details);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
        } catch (Exception e) {
            log.error("Exception occurred in changePassword service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }


    public StandardMessageResponse logout(String companyId, String userId) {
        StandardMessageResponse response = null;
        try {
            if (!Validator.hasData(companyId) || !Validator.hasData(userId))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            UserDetailsId id = new UserDetailsId();
            userDetailsRepository.removeUserKey(companyId, userId);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
        } catch (Exception e) {
            log.error("Exception occurred in logout service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private void setUserDetails(String companyId, String userId, UserDetailsEntity details) {
        CacheUtil.addObjectToCache(companyId + userId + Constants.USER_NAME, details.getUserName(), Constants.TTL_IN_TWELVE_HOURS);
        CacheUtil.addObjectToCache(companyId + userId + Constants.BRANCH_SHORT_NAME, details.getBranchShortName(), Constants.TTL_IN_TWELVE_HOURS);
        CacheUtil.addObjectToCache(companyId + userId + Constants.BRANCH_NAME, details.getBranchName(), Constants.TTL_IN_TWELVE_HOURS);
    }

    private StandardMessageResponse getResponse(String companyId, String userId, UserDetailsEntity details) {
        UserDetailsPojo pojo = new UserDetailsPojo();
        String key = CommonUtil.getUuid();
        pojo.setUser_key(key);
        pojo.setBranch_name(details.getBranchName());
        setUserDetails(companyId, userId, details);
        details.setUserKey(key);
        userDetailsRepository.save(details);
        UserKeyPojo userKeyPojo = new UserKeyPojo();
        userKeyPojo.setUserKey(key);
        return StandardResponseUtil.successResponse(Collections.singletonList(userKeyPojo));
    }
}
