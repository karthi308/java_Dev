package com.io.tech.service;

import com.io.tech.entity.BranchDetailsEntity;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.repository.BranchDetailsRepository;
import com.io.tech.repository.BranchTargetRepository;
import com.io.tech.util.Constants;
import com.io.tech.util.StandardResponseUtil;
import com.io.tech.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BranchTargetService {

    @Autowired
    BranchTargetRepository repository;
    @Autowired
    BranchDetailsRepository branchDetailsRepository;


    public StandardMessageResponse addBranchTarget(String companyId, String userId, String branchName, int targetAmount, String date) {
        try {
            if (validateParams(companyId, userId, branchName, date) || !Validator.hasData(targetAmount))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            BranchDetailsEntity entity = branchDetailsRepository.findByIdCompanyIdAndIdBranchName(companyId, branchName);
//            entity.setId(targetAmount);
//            branchDetailsRepository.save(entity);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            log.error("Error occurred in addBranchTarget service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private boolean validateParams(String companyId, String userId, String branchName, String date) {
        return !Validator.hasData(companyId) || !Validator.hasData(userId) || !Validator.hasData(branchName) || !Validator.hasData(date);
    }
}
