package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.BranchPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.NewUserRepository;
import com.laptop.code.laptop.util.CommonFunction;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserBranchService {
    @Autowired
    NewUserRepository newUserRepository;
    private static Logger logger = LoggerFactory.getLogger(UserBranchService.class);

    public StandardResponseMessage switchBranch(HttpServletRequest request, String branchName ){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            if(BranchPojo.adminAccess.equals(1) || BranchPojo.adminAccess.equals(2)) {
                String userId = CommonFunction.getUserId(request);
                Optional<NewUserEntity> userList = newUserRepository.findById(userId);
                NewUserEntity newUserEntity = new NewUserEntity();
                newUserEntity.setUserId(userList.get().getUserId());
                newUserEntity.setUserName(userList.get().getUserName());
                newUserEntity.setPwd(userList.get().getPwd());
                newUserEntity.setMailId(userList.get().getMailId());
                newUserEntity.setAdminAccess(userList.get().getAdminAccess());
                newUserEntity.setBranch(userList.get().getBranch());
                newUserEntity.setUserKey(userList.get().getUserKey());
                newUserEntity.setSwitchBranchName(branchName);
                newUserRepository.save(newUserEntity);
                standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Succesfully Switched to " + branchName));
            }
            else{
                standardResponseMessage= StandardResposneUtil.badRequestResponse("Access Denied..");
            }
        }
        catch (Exception e){
            logger.error("Error Occurred in switchBranchName Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage getSwitchedBranchName(HttpServletRequest request){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            String userId = CommonFunction.getUserId(request);
            Optional<NewUserEntity> userList = newUserRepository.findById(userId);
            if (Validators.hasData(userList)) {
                standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList(userList.get().getSwitchBranchName()));
                //redis get switch branch name
            } else {
                standardResponseMessage= StandardResposneUtil.notFound();
            }
        }
        catch (Exception e){
            logger.error("Error Occurred in getSwitchBranchName Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage getAllBranchName(HttpServletRequest request){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            List<NewUserEntity> branchNamelist = newUserRepository.findAll();
            List<String> branchList = new ArrayList<>();
            for (NewUserEntity newUser : branchNamelist) {
                branchList.add(newUser.getBranch());
            }
            if (Validators.hasData(branchList)) {
                standardResponseMessage = StandardResposneUtil.successResponse(branchList);
            } else
                standardResponseMessage = StandardResposneUtil.notFound();
        }
        catch (Exception e){
            logger.error("Error Occurred in getAllBranchName Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
}
