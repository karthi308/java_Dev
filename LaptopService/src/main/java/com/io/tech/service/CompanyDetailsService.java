package com.io.tech.service;

import com.io.tech.entity.BranchDetailsEntity;
import com.io.tech.entity.CompanyDetailsEntity;
import com.io.tech.entity.UserDetailsEntity;
import com.io.tech.pojo.CompanyDetailsPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.*;
import com.io.tech.util.CommonUtil;
import com.io.tech.util.Constants;
import com.io.tech.util.StandardResponseUtil;
import com.io.tech.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyDetailsService {

    @Autowired
    CompanyDetailsRepository companyRepository;

    @Autowired
    BranchDetailsRepository branchRepository;

    @Autowired
    UserDetailsRepository userRepository;

    private static CompanyDetailsEntity getAndSetCompanyDetails(CompanyDetailsPojo pojo, CompanyDetailsEntity details) {
        CompanyDetailsEntity entity = new CompanyDetailsEntity();
        entity.setCompanyId(details.getCompanyId());
        entity.setCompanyName(Validator.hasData(pojo.getCompany_name()) ? pojo.getCompany_name() : details.getCompanyName());
        entity.setCompanyShortName(Validator.hasData(pojo.getBranch_short_name()) ? pojo.getBranch_short_name() : details.getCompanyShortName());
        entity.setAddress1(Validator.hasData(pojo.getAddress1()) ? pojo.getAddress1() : details.getAddress1());
        entity.setAddress2(Validator.hasData(pojo.getAddress2()) ? pojo.getAddress2() : details.getAddress2());
        entity.setState(Validator.hasData(pojo.getState()) ? pojo.getState() : details.getState());
        entity.setPincode(Validator.hasData(pojo.getPincode()) ? pojo.getPincode() : details.getPincode());
        entity.setCompanyLogo(Validator.hasData(pojo.getCompany_logo()) ? pojo.getCompany_logo() : details.getCompanyLogo());
        entity.setMobileNumber(Validator.hasData(pojo.getMobile_number()) ? pojo.getMobile_number() : details.getMobileNumber());
        entity.setTelephoneNumber(Validator.hasData(pojo.getTelephone_number()) ? pojo.getTelephone_number() : details.getTelephoneNumber());
        entity.setStatus(Validator.hasData(pojo.getStatus()) ? pojo.getStatus() : details.getStatus());
        return entity;
    }

    private void addCompanyDetails(CompanyDetailsPojo pojo) {
        CompanyDetailsEntity entity = new CompanyDetailsEntity();
        entity.setCompanyId(pojo.getCompanyId());
        entity.setCompanyName(pojo.getCompany_name());
        entity.setAddress1(pojo.getAddress1());
        entity.setAddress2(pojo.getAddress2());
        entity.setState(pojo.getState());
        entity.setPincode(pojo.getPincode());
        entity.setCompanyLogo(pojo.getCompany_logo());
        entity.setMobileNumber(pojo.getMobile_number());
        entity.setTelephoneNumber(pojo.getTelephone_number());
        entity.setStatus(Constants.ACTIVE);
        companyRepository.save(entity);
    }

    private void addBranchDetails(CompanyDetailsPojo pojo) {
        BranchDetailsEntity entity = new BranchDetailsEntity();
        BranchDetailsId id = new BranchDetailsId();
        id.setCompanyId(pojo.getCompanyId());
        id.setBranchName(pojo.getBranch_name());
        entity.setId(id);
        entity.setBranchShortName(pojo.getBranch_short_name());
        entity.setMobileNumber(pojo.getMobile_number());
        entity.setTelephoneNumber(pojo.getTelephone_number());
        entity.setAddress1(pojo.getAddress1());
        entity.setAddress2(pojo.getAddress2());
        entity.setState(pojo.getState());
        entity.setPincode(pojo.getPincode());
        entity.setStatus(Constants.ACTIVE);
        branchRepository.save(entity);
    }

    private void addUserDetails(CompanyDetailsPojo pojo) {
        UserDetailsEntity entity = new UserDetailsEntity();
        UserDetailsId id = new UserDetailsId();
        id.setCompanyId(pojo.getCompanyId());
        id.setUserId("E001");
        entity.setId(id);
        entity.setUserName(pojo.getUser_name());
        entity.setBranchName(pojo.getBranch_name());
        entity.setBranchShortName(pojo.getBranch_short_name());
        entity.setPassword(pojo.getBranch_short_name() + "@123");
        entity.setMailId(pojo.getMail_id());
        entity.setCompanyDetailsAccess(false);
        entity.setSwitchBranchAccess(true);
        entity.setUserDetailsAccess(true);
        entity.setVendorStockAccess(true);
        entity.setStatus(Constants.ACTIVE);
        userRepository.save(entity);
    }

    public StandardMessageResponse getAndSetCompanyDetails(CompanyDetailsPojo pojo) {
        try {
            if (validateParams(pojo)) {
                pojo.setCompanyId(CommonUtil.getUuid());
                addCompanyDetails(pojo);
                addBranchDetails(pojo);
                addUserDetails(pojo);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_ADDED);
            } else return StandardResponseUtil.badRequestResponse("Required missing fields");
        } catch (Exception e) {
            log.error("Error occurred in addCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getCompanyList() {
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, Constants.COMPANY_NAME);
            List<CompanyDetailsEntity> list = companyRepository.findAll(sort);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(list);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCompanyList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getCompanyDetails(String companyId) {
        try {
            CompanyDetailsEntity list = companyRepository.findByCompanyId(companyId);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(Collections.singletonList(list));
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getCompanyStatus(String status) {
        try {
            List<CompanyDetailsEntity> list = companyRepository.findByStatusOrderByCompanyNameAsc(status);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(list);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in findCompanyDetailsByStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse updateCompanyDetails(String companyId, CompanyDetailsPojo pojo) {
        try {
            Optional<CompanyDetailsEntity> details = companyRepository.findById(companyId);
            if (Validator.hasData(details)) {
                CompanyDetailsEntity entity = getAndSetCompanyDetails(pojo, details.get());
                companyRepository.save(entity);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse removeCompanyDetails(String companyId) {
        try {
            if (Validator.hasData(companyId)) {
                companyRepository.updateStatusById(companyId, "In Active");
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private boolean validateParams(CompanyDetailsPojo pojo) {
        return Validator.hasData(pojo.getUser_name()) && Validator.hasData(pojo.getBranch_name()) && Validator.hasData(pojo.getBranch_short_name()) && Validator.hasData(pojo.getMail_id()) && Validator.hasData(pojo.getCompany_name()) && Validator.hasData(pojo.getAddress1()) && Validator.hasData(pojo.getAddress2()) && Validator.hasData(pojo.getPincode()) && Validator.hasData(pojo.getMobile_number());
    }
}
