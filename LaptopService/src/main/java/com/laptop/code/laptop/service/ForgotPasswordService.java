package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.OtpPojo;
import com.laptop.code.laptop.pojo.PasswordPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.NewUserRepository;
import com.laptop.code.laptop.util.CommonFunction;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Optional;

@Service
public class ForgotPasswordService {
    @Autowired
    NewUserRepository newUserRepository;
    @Autowired
    OtpPojo otpPojo;
    @Autowired
    LTService ltService;

    @Autowired
    private RedisTemplate<String, String> redisCache;
    @Autowired
    SendMail sendMail;
    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);

    public StandardResponseMessage getForgetPasswordOtp(String userId) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
//            redisCache.opsForValue().set("token_key", "karthi", 1, TimeUnit.MINUTES);
//            System.out.println("redis "+redisCache.opsForValue().get("token_key"));
            Optional<NewUserEntity> userDetails = newUserRepository.findById(userId.toUpperCase());
            if (Validators.hasData(userDetails)) {
                String mailId = userDetails.get().getMailId();
                int otp = getRandomOtp();
                new Thread(() -> {
                    ltService.sendMail.mail(mailId, userId, "One Time Password", "Use " + otp + " to reset your password.Don't share this otp to anyone.");
                }).start();
                //set Otp, userId and mailId in redis
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("OTP Sent to Mail Successfully"));
            } else {
                standardResponseMessage = StandardResposneUtil.badRequestResponse("Invalid User Id");
            }
        } catch (Exception e) {
            logger.error("Error Occurred in Forgot Password Service " + e);
            standardResponseMessage = StandardResposneUtil.internalServerErrorResponse();
        }
        return standardResponseMessage;
    }

    public int getRandomOtp() {
        SecureRandom rand = new SecureRandom();
        int min = 1000;
        int max = 9999;
        int otp = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return otp;
    }

    public StandardResponseMessage resendOtp() {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
//          String userId=otpPojo.getUserId();
//          String mailId=otpPojo.getMailId();
//          get userId and mailId in redis
            int otp = getRandomOtp();
            String mailResponse = sendMail.mail("mailId", "userId", "One Time Password", "Use " + otp + " to reset your password.Don't share this otp to anyone.");
            if (Validators.hasData(mailResponse)) {
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList(mailResponse));
            } else {
                standardResponseMessage = StandardResposneUtil.badRequestResponse();
            }
        } catch (Exception e) {
            logger.error("Error Occurred in Resend Otp : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getVerifyOtp(int mailOtp) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            int otp = otpPojo.getOtp();
            if (otp == mailOtp) {
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully Verified"));
            } else
                standardResponseMessage = StandardResposneUtil.badRequestResponse("Invalid Otp");
        } catch (Exception e) {
            logger.error("Error Occurred in Verify Otp : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    //===================
    public StandardResponseMessage setPassword(HttpServletRequest request, String newPassword, String confirmPassword) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = "EK308";
            // get userId in redis
            NewUserEntity newUser = new NewUserEntity();
            if (newPassword.equals(confirmPassword)) {
                Optional<NewUserEntity> list = newUserRepository.findById(userId);
                newUser.setUserId(list.get().getUserId());
                newUser.setUserName(list.get().getUserName());
                newUser.setPwd(confirmPassword);
                newUser.setBranch(list.get().getBranch());
                newUser.setUserKey(list.get().getUserKey());
                newUser.setMailId(list.get().getMailId());
                newUser.setAdminAccess(list.get().getAdminAccess());
                newUser.setSwitchBranchName(list.get().getSwitchBranchName());
                newUserRepository.save(newUser);
                sendMail.mail(newUser.getMailId(), newUser.getUserId(), "Password Changed Successfully", "Your Password Changed Successfully..");
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Password Changed Successfully"));
            } else if (!newPassword.equals(confirmPassword)) {
                standardResponseMessage = StandardResposneUtil.badRequestResponse("Password Not Matching");
            } else {
                standardResponseMessage = StandardResposneUtil.badRequestResponse();
            }

        } catch (Exception e) {
            logger.error("Error Occurred in Set Password : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage changePassword(HttpServletRequest request, PasswordPojo passwordPojo) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = CommonFunction.getUserId(request);
            Optional<NewUserEntity> userdetails = newUserRepository.findById(userId);
            if (Validators.hasData(userdetails)) {
                if (passwordPojo.getNewPassword().equals(passwordPojo.getConfirmPassword())) {
                    NewUserEntity newUserEntity1 = new NewUserEntity();
                    newUserEntity1.setUserId(userdetails.get().getUserId());
                    newUserEntity1.setUserName(userdetails.get().getUserName());
                    newUserEntity1.setPwd(passwordPojo.getConfirmPassword());
                    newUserEntity1.setAdminAccess(userdetails.get().getAdminAccess());
                    newUserEntity1.setUserKey(userdetails.get().getUserKey());
                    newUserEntity1.setBranch(userdetails.get().getBranch());
                    newUserEntity1.setSwitchBranchName(userdetails.get().getSwitchBranchName());
                    newUserEntity1.setMailId(userdetails.get().getMailId());
                    newUserRepository.save(newUserEntity1);
                    standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Password successfully changed"));
                } else
                    standardResponseMessage = StandardResposneUtil.badRequestResponse("New Password and Confirm Password Not Matching..");
            } else
                standardResponseMessage = StandardResposneUtil.badRequestResponse("InCorrect Old Password");
        } catch (Exception e) {
            logger.error("Error Occurred in Change Password : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }

        return standardResponseMessage;
    }
}

