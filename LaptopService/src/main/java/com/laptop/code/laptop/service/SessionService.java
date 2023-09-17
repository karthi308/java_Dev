package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.DoLoginResponsePojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.NewUserRepository;
import com.laptop.code.laptop.util.CommonFunction;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionService {
    @Autowired
    NewUserRepository newUserRepository;
    private static Logger logger = LoggerFactory.getLogger(SessionService.class);

    public StandardResponseMessage doLogin(HttpServletResponse response, String userId, String pwd) {
//        redisTemplate.opsForValue().set("userid ","EK3008");
//        System.out.println(redisTemplate.opsForValue().get("userid"));
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            standardResponseMessage = hasValidUser(response, userId, pwd);
        } catch (Exception e) {
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
            logger.error("Error in Login Service " + e);
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage hasValidUser(HttpServletResponse response, String userId, String pwd) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        Optional<NewUserEntity> userDetails = newUserRepository.findById(userId);
        String dbUserId = "";
        String dbPwd = "";
        String branchName = "";
        List<DoLoginResponsePojo> doLoginResponseList = new ArrayList<>();
        DoLoginResponsePojo doLoginResponsePojo = new DoLoginResponsePojo();
        if (Validators.hasData(userDetails)) {
            branchName = userDetails.get().getBranch();
            dbUserId = userDetails.get().getUserId();
            dbPwd = userDetails.get().getPwd();
            if (userId.equals(dbUserId) && pwd.equals(dbPwd)) {
                doLoginResponsePojo.setUser_key(setUserDetails(response, userDetails, branchName));
                doLoginResponseList.add(doLoginResponsePojo);
                standardResponseMessage = StandardResposneUtil.successResponse(doLoginResponseList);
            } else if (userId.equals(dbUserId) && !pwd.equals(dbPwd)) {
                doLoginResponsePojo.setLogin_status(Constant.INVALID_PASSWORD);
                doLoginResponseList.add(doLoginResponsePojo);
                standardResponseMessage = StandardResposneUtil.unAuthorizedResponse();
                standardResponseMessage.setData(doLoginResponseList);
            }
        } else {
            doLoginResponsePojo.setLogin_status(Constant.INVALID_USER);
            doLoginResponseList.add(doLoginResponsePojo);
            standardResponseMessage = StandardResposneUtil.unAuthorizedResponse();
            standardResponseMessage.setData(doLoginResponseList);
        }
        return standardResponseMessage;
    }

    public String setUserDetails(HttpServletResponse response, Optional<NewUserEntity> userDetails, String branchName) {
        String userKey = UUID.randomUUID().toString();
        userKey = userKey.replace("-", "");
        setCookiesUserIdAndUserKey(response, userDetails.get().getUserId(), userKey, branchName);
        NewUserEntity newUserEntity = new NewUserEntity();
        newUserEntity.setUserId(userDetails.get().getUserId());
        newUserEntity.setUserName(userDetails.get().getUserName());
        newUserEntity.setPwd(userDetails.get().getPwd());
        newUserEntity.setAdminAccess(userDetails.get().getAdminAccess());
        newUserEntity.setMailId(userDetails.get().getMailId());
        newUserEntity.setBranch(userDetails.get().getBranch());
        newUserEntity.setSwitchBranchName(userDetails.get().getSwitchBranchName());
        newUserEntity.setUserKey(userKey);
        newUserRepository.save(newUserEntity);
        return userKey;
    }

    public void setCookiesUserIdAndUserKey(HttpServletResponse response, String userId, String appKey, String branchName) {
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        Cookie setUserIdCookie = new Cookie("userId", userId);
        response.addCookie(setUserIdCookie);
        Cookie setUserTokenCookie = new Cookie("userKey", appKey);
        response.addCookie(setUserTokenCookie);
        Cookie setBranchCookie = new Cookie("narchb", branchName);
        response.addCookie(setBranchCookie);
    }

    ///
    public StandardResponseMessage logout(HttpServletRequest request, HttpServletResponse response) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = CommonFunction.getUserId(request);
            NewUserEntity newUserEntity1 = new NewUserEntity();
            Optional<NewUserEntity> list = newUserRepository.findById(userId);
            if (Validators.hasData(list)) {
                newUserEntity1.setUserId(list.get().getUserId());
                newUserEntity1.setUserName(list.get().getUserName());
                newUserEntity1.setPwd(list.get().getPwd());
                newUserEntity1.setAdminAccess(list.get().getAdminAccess());
                newUserEntity1.setBranch(list.get().getBranch());
                newUserEntity1.setSwitchBranchName(list.get().getSwitchBranchName());
                newUserEntity1.setUserKey(null);
                newUserEntity1.setMailId(list.get().getMailId());
                newUserRepository.save(newUserEntity1);
                Cookie removeCookiesuserId = new Cookie("userId", "");
                Cookie removeCookiesuserKey = new Cookie("userKey", "");
                Cookie removeBranchCookie = new Cookie("narchb", "");
                response.addCookie(removeBranchCookie);
                response.addCookie(removeCookiesuserId);
                response.addCookie(removeCookiesuserKey);
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully Logged Out"));
            } else
                standardResponseMessage = StandardResposneUtil.notFound();
        } catch (Exception e) {
            logger.error("Error Occurred in logout Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
}
