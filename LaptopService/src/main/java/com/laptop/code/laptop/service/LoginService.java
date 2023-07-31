package com.laptop.code.laptop.service;

import com.laptop.code.laptop.Controller.LTController;
import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.BranchPojo;
import com.laptop.code.laptop.pojo.DoLoginResponsePojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.NewUserRepository;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.DefaultResponseMessage;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class LoginService {

    @Autowired
    NewUserRepository newUserRepository;
    private static Logger logger = LoggerFactory.getLogger(LTController.class);

    public StandardResponseMessage doLogin(HttpServletRequest request, HttpServletResponse response, String userId, String pwd){
//        redisTemplate.opsForValue().set("userid ","EK3008");
//        System.out.println(redisTemplate.opsForValue().get("userid"));
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            standardResponseMessage=hasValidUser(response,userId,pwd);
        }
        catch (Exception e){
            standardResponseMessage=DefaultResponseMessage.badRequestResponse();
            logger.error("Error in Login Service "+e);
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage hasValidUser(HttpServletResponse response, String userId, String pwd){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        Optional<NewUserEntity> userDetails=newUserRepository.findById(userId);
        String dbUserId="";
        String dbPwd="";
        String loginStatus = Constant.INVALID_PASSWORD;;
        List<DoLoginResponsePojo> doLoginResponseList=new ArrayList<>();
        DoLoginResponsePojo doLoginResponsePojo=new DoLoginResponsePojo();
        if (Validators.hasData(userDetails)) {
            BranchPojo.branch = userDetails.get().getBranch();
            dbUserId = userDetails.get().getUserId().toString();
            dbPwd = userDetails.get().getPwd().toString();
            System.out.println(userId + " " + pwd + " db " + dbUserId + dbPwd);
            if (userId.equals(dbUserId) && pwd.equals(dbPwd)) {
                doLoginResponsePojo.setUserKey(setUserDetails(response,userDetails));
                standardResponseMessage=DefaultResponseMessage.successResponse(doLoginResponseList);
            }
            else if(userId.equals(dbUserId) && !pwd.equals(dbPwd)){
                 loginStatus=Constant.INVALID_PASSWORD;
            }
            doLoginResponsePojo.setLoginStatus(loginStatus);
            standardResponseMessage=DefaultResponseMessage.badRequestResponse();
            standardResponseMessage.setData(doLoginResponseList);

        }
        return standardResponseMessage;
    }
    public String setUserDetails(HttpServletResponse response, Optional<NewUserEntity> userDetails){
            String userKey = UUID.randomUUID().toString();
            userKey = userKey.replace("-", "");
            System.out.println(userKey + " key ");
            setCookiesUserIdAndUserKey(response, userDetails.get().getUserId(),userKey);
            NewUserEntity newUserEntity = new NewUserEntity();
            newUserEntity.setUserId(userDetails.get().getUserId());
            newUserEntity.setUserName(userDetails.get().getUserName());
            newUserEntity.setPwd(userDetails.get().getPwd());
            newUserEntity.setAdminAccess(userDetails.get().getAdminAccess());
            newUserEntity.setMailId(userDetails.get().getMailId());
            newUserEntity.setBranch(userDetails.get().getBranch());
            newUserEntity.setSwitchBranchName(userDetails.get().getSwitchBranchName());
            newUserEntity.setCreatedTime(userDetails.get().getCreatedTime());
            newUserEntity.setUserKey(userKey);
            newUserEntity.setUpdateTime(new Date());
            newUserRepository.save(newUserEntity);
            return userKey;
        }
    public void setCookiesUserIdAndUserKey(HttpServletResponse response,String userId,String appKey){
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","GET,PUT,POST,DELETE,PATCH,OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");


        Cookie userIdCookieRemove = new Cookie("userId", userId);
        response.addCookie(userIdCookieRemove);
        Cookie usertokenCookieRemove = new Cookie("appKey", appKey);
        response.addCookie(usertokenCookieRemove);
    }
}
