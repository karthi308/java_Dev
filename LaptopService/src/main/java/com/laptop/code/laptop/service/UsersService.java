package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    public StandardResponseMessage addUser(HttpServletRequest request, AddUserPojo pojo) {
        System.out.println("ddd --> " + pojo);
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String latestUserId = userDetailsRepository.getMaxUserId();
            String integerPart = latestUserId.replaceAll("\\D", "");
            String userId = String.format("E%03d", (Integer.parseInt(integerPart) + 1));
            UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
            userDetailsEntity.setUserId(userId);
            userDetailsEntity.setUserName(pojo.getUserName().toUpperCase());
            userDetailsEntity.setStatus("Active");
            userDetailsEntity.setPwd(pojo.getBranchName().toUpperCase() + "@123");
            userDetailsEntity.setUserModificationAccess(pojo.isUserModificationAccess());

            userDetailsEntity.setSwitchBranchAccess(false);
            userDetailsEntity.setVendorStockAccess(pojo.isVendorStockAccess());
            userDetailsEntity.setBranchName(pojo.getBranchName().toUpperCase());
            userDetailsEntity.setMailId(pojo.getMailId().toLowerCase());
            userDetailsRepository.save(userDetailsEntity);
            standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully user created"));
        } catch (
                Exception e) {
            log.error("Error Occurred in add User service" + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getAllUsersDetails() {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
//            List<UserDetailsEntity> usersList = userDetailsRepository.findAll();
            List<UserDetailsEntity> usersList = userDetailsRepository.findAll(CommonUtil.getSortedObject("userId"));
            if (Validators.hasData(usersList)) {
                for (UserDetailsEntity UserDetailsList : usersList) {
                    UserDetailsList.setPwd(null);
                }
                standardResponseMessage = StandardResposneUtil.successResponse(usersList);
            } else
                standardResponseMessage = StandardResposneUtil.notFound();
        } catch (Exception e) {
            log.error("Error Occurred in getUsersList Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getUserDetailsByStatus(String status) {
        try {
            if (status.equals("All"))
                return getAllUsersDetails();
            List<UserDetailsEntity> list = userDetailsRepository.getUserDetailsByStatus(status, CommonUtil.getSortedObject(Constant.USER_ID));
            if (Validators.hasData(list))
                return StandardResposneUtil.successResponse(list);
            else
                return StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error Occurred in getUserDetailsByStatus : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    public StandardResponseMessage removeUser(String userId) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            userDetailsRepository.removeUser(userId.toUpperCase(), "InActive");
            standardResponseMessage = StandardResposneUtil.successMessage("Successfully User Removed");
        } catch (Exception e) {
            log.error("Error Occurred in removeUser : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getUserDetails(String userId) {
        try {
            Optional<UserDetailsEntity> userList = userDetailsRepository.findById(userId);
            if (Validators.hasData(userList))
                return StandardResposneUtil.successResponse(Collections.singletonList(userList));
        } catch (Exception e) {
            log.error("Error Occurred in userDetails : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
        return StandardResposneUtil.noDataMessage("no data found");
    }

    public StandardResponseMessage updateUserDetails(HttpServletRequest request, AddUserPojo pojo) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            Optional<UserDetailsEntity> list = userDetailsRepository.findById(pojo.getUserId());
            UserDetailsEntity entity = new UserDetailsEntity();
            entity.setUserId(pojo.getUserId());
            entity.setUserName(Validators.hasData(pojo.getUserName()) ? pojo.getUserName() : list.get().getUserName());
            entity.setBranchName(Validators.hasData(pojo.getBranchName()) ? pojo.getBranchName() : list.get().getBranchName());
            entity.setMailId(Validators.hasData(pojo.getMailId()) ? pojo.getMailId() : list.get().getMailId());
            entity.setPwd(Validators.hasData(pojo.getPwd()) ? pojo.getMailId() : list.get().getPwd());
            entity.setUserKey(Validators.hasData(pojo.getPwd()) ? pojo.getMailId() : list.get().getPwd());
            entity.setStatus(Validators.hasData(pojo.getStatus()) ? pojo.getMailId() : list.get().getStatus());
            entity.setSwitchBranchAccess(Validators.hasData(pojo.isSwitchBranchAccess()) ? pojo.isSwitchBranchAccess() : list.get().isSwitchBranchAccess());
            entity.setUserModificationAccess(Validators.hasData(pojo.isUserModificationAccess()) ? pojo.isUserModificationAccess() : list.get().isUserModificationAccess());
            entity.setVendorStockAccess(Validators.hasData(pojo.isVendorStockAccess()) ? pojo.isVendorStockAccess() : list.get().isVendorStockAccess());
            userDetailsRepository.save(entity);
            standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully Updated User Details"));
        } catch (Exception e) {
            log.error("Error Occurred in update User details service :" + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

}
