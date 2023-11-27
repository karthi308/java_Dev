package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.DoLoginResponsePojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.util.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {
    @Autowired
    UserDetailsRepository newUserRepository;

    @Autowired
    CacheUtil cache;
    private static Logger logger = LoggerFactory.getLogger(SessionService.class);

    public StandardResponseMessage doLogin(HttpServletResponse response, String userId, String pwd) {
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
        Optional<UserDetailsEntity> userDetails = newUserRepository.findById(userId);
        String dbUserId = "";
        String dbPwd = "";
        List<DoLoginResponsePojo> doLoginResponseList = new ArrayList<>();
        DoLoginResponsePojo doLoginResponsePojo = new DoLoginResponsePojo();
        if (Validators.hasData(userDetails)) {
            cache.setValue(userDetails.get().getUserId() + "branch", userDetails.get().getBranchName(), 12);
            cache.setValue(userDetails.get().getUserId() + "details", CommonUtil.getASJsonString(userDetails.get()), 12);
            dbUserId = userDetails.get().getUserId();
            dbPwd = userDetails.get().getPwd();
            if (userId.equals(dbUserId) && pwd.equals(dbPwd)) {
                doLoginResponsePojo.setUser_key(setUserDetails(response, userDetails));
                doLoginResponseList.add(doLoginResponsePojo);
                standardResponseMessage = StandardResposneUtil.successResponse(doLoginResponseList);
            } else if (userId.equals(dbUserId) && !pwd.equals(dbPwd))
                standardResponseMessage = StandardResposneUtil.noDataMessage(Constant.INVALID_PASSWORD);
        } else
            standardResponseMessage = StandardResposneUtil.noDataMessage(Constant.INVALID_USER);

        return standardResponseMessage;
    }

    public String setUserDetails(HttpServletResponse response, Optional<UserDetailsEntity> userDetails) {
        String userKey = UUID.randomUUID().toString();
        userKey = userKey.replace("-", "");
        setCookiesUserIdAndUserKey(response, userDetails.get().getUserId(), userKey);
        UserDetailsEntity newUserEntity = new UserDetailsEntity();
        newUserEntity.setUserId(userDetails.get().getUserId());
        newUserEntity.setUserName(userDetails.get().getUserName());
        newUserEntity.setPwd(userDetails.get().getPwd());
        newUserEntity.setUserModificationAccess(userDetails.get().isUserModificationAccess());
        newUserEntity.setSwitchBranchAccess(userDetails.get().isSwitchBranchAccess());
        newUserEntity.setVendorStockAccess(userDetails.get().isVendorStockAccess());
        newUserEntity.setMailId(userDetails.get().getMailId());
        newUserEntity.setBranchName(userDetails.get().getBranchName());
        newUserEntity.setUserKey(userKey);
//        newUserEntity.setUserKey("ca748072ff794576bf5c4515e7bff6c3");
        newUserRepository.save(newUserEntity);
        return userKey;
    }

    public void setCookiesUserIdAndUserKey(HttpServletResponse response, String userId, String appKey) {
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        Cookie setUserIdCookie = new Cookie("userId", userId);
        response.addCookie(setUserIdCookie);
        Cookie setUserTokenCookie = new Cookie("userKey", appKey);
        response.addCookie(setUserTokenCookie);
    }

    public StandardResponseMessage logout(HttpServletRequest request) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = request.getHeader(Constant.USER_ID);
            UserDetailsEntity newUserEntity1 = new UserDetailsEntity();
            Optional<UserDetailsEntity> list = newUserRepository.findById(userId);
            if (Validators.hasData(list)) {
                newUserEntity1.setUserId(list.get().getUserId());
                newUserEntity1.setUserName(list.get().getUserName());
                newUserEntity1.setPwd(list.get().getPwd());
                newUserEntity1.setUserModificationAccess(list.get().isUserModificationAccess());
                newUserEntity1.setSwitchBranchAccess(list.get().isSwitchBranchAccess());
                newUserEntity1.setVendorStockAccess(list.get().isVendorStockAccess());
                newUserEntity1.setBranchName(list.get().getBranchName());
                newUserEntity1.setUserKey(null);
                newUserEntity1.setMailId(list.get().getMailId());
                newUserRepository.save(newUserEntity1);
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
