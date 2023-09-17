package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.AddUserPojo;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    NewUserRepository newUserRepository;
    private static Logger logger = LoggerFactory.getLogger(UsersService.class);

    public StandardResponseMessage addUser(AddUserPojo createNewUserPojo){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try{
            Optional<NewUserEntity> userList=newUserRepository.findById(createNewUserPojo.getUserId().toUpperCase());
            if(Validators.hasData(userList)){
                return standardResponseMessage= StandardResposneUtil.badRequestResponse("User Already Exists");
            }
            else {
                NewUserEntity newUserEntity = new NewUserEntity();
                newUserEntity.setUserId(createNewUserPojo.getUserId().toUpperCase());
                newUserEntity.setUserName(createNewUserPojo.getUserName().toUpperCase());
                newUserEntity.setPwd(createNewUserPojo.getBranch().toUpperCase() + "@123");
                newUserEntity.setAdminAccess(createNewUserPojo.getAdminAccess());
                newUserEntity.setBranch(createNewUserPojo.getBranch().toUpperCase());
                newUserEntity.setMailId(createNewUserPojo.getMailId().toLowerCase());
                newUserEntity.setSwitchBranchName(createNewUserPojo.getBranch().toUpperCase());
                newUserRepository.save(newUserEntity);
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully user Added"));
            }
        }
        catch (Exception e){
            logger.error("Error Occurred in add User service"+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage getAllUsersDetails(){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            List<NewUserEntity> usersList = newUserRepository.findAll();
            if(Validators.hasData(usersList)) {
                for (NewUserEntity UserDetailsList : usersList) {
                    UserDetailsList.setPwd(null);
                }
                standardResponseMessage = StandardResposneUtil.successResponse(usersList);
            }
            else
                standardResponseMessage= StandardResposneUtil.notFound();
        }
        catch (Exception e){
            logger.error("Error Occurred in getUsersList Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage removeUser(String userId){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try{
            newUserRepository.deleteById(userId.toUpperCase());
            standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Successfully User Removed"));
        }
        catch (Exception e){
            logger.error("Error Occurred in removeUser : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage getUserDetails(HttpServletRequest request){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try{
            String userId= CommonFunction.getUserId(request);
            Optional<NewUserEntity> userList=newUserRepository.findById(userId);
            if(Validators.hasData(userList)) {
                standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList(userList));
            }
            else{
                standardResponseMessage= StandardResposneUtil.badRequestResponse();
            }
        }
        catch (Exception e){
            logger.error("Error Occurred in userDetails");
        }
        return standardResponseMessage;
    }
}
