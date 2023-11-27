package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.MessageResponsePojo;
import com.laptop.code.laptop.pojo.OtpPojo;
import com.laptop.code.laptop.pojo.PasswordPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.util.Constant;
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
import java.util.concurrent.TimeUnit;

@Service
public class ForgotPasswordService {
    @Autowired
    UserDetailsRepository newUserRepository;
    @Autowired
    OtpPojo otpPojo;
    @Autowired
    LTService ltService;

    @Autowired
    SendMail sendMail;
    @Autowired
    private RedisTemplate<String, String> redisCache;
    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);

    public StandardResponseMessage getForgetPasswordOtp(String userId) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            Optional<UserDetailsEntity> userDetails = newUserRepository.findById(userId.toUpperCase());
            if (Validators.hasData(userDetails)) {
                String mailId = userDetails.get().getMailId();
                int otp = getRandomOtp();
                new Thread(() -> {
                    ltService.sendMail.mail(mailId, userId, "One Time Password", "Use " + otp + " to reset your password.Don't share this otp to anyone.");
                }).start();
                redisCache.opsForValue().set(userId + "OTP", otp + "", 2, TimeUnit.MINUTES);
                MessageResponsePojo messageResponsePojo = new MessageResponsePojo();
                messageResponsePojo.setMessage("OTP Sent Successfully to " + mailId.replaceAll("(?<=.{3}).(?=.*@)", "*"));
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList(messageResponsePojo));
            } else {
                standardResponseMessage = StandardResposneUtil.noDataMessage("Invalid User Id");
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

    public StandardResponseMessage resendOtp(String userId) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            Optional<UserDetailsEntity> userDetails = newUserRepository.findById(userId.toUpperCase());
            if (Validators.hasData(userDetails)) {
                String mailId = userDetails.get().getMailId();
                int otp = getRandomOtp();
                new Thread(() -> {
                    ltService.sendMail.mail(mailId, userId, "One Time Password", "Use " + otp + " to reset your password.Don't share this otp to anyone.");
                }).start();
                redisCache.opsForValue().set(userId + "OTP", String.valueOf(otp), 3, TimeUnit.MINUTES);
                MessageResponsePojo messageResponsePojo = new MessageResponsePojo();
                messageResponsePojo.setMessage("OTP Sent Successfully ");
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList(messageResponsePojo));
            } else {
                standardResponseMessage = StandardResposneUtil.badRequestResponse();
            }
        } catch (Exception e) {
            logger.error("Error Occurred in Resend Otp : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage verifyOtp(String userId, int mailOtp) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String otp = redisCache.opsForValue().get(userId + "OTP");
            System.out.println("rdisss " + otp);
            if (otp.equals(String.valueOf(mailOtp))) {
                standardResponseMessage = StandardResposneUtil.successResponse(Collections.singletonList("Successfully Verified"));
            } else
                standardResponseMessage = StandardResposneUtil.noDataMessage("Invalid Otp");
        } catch (Exception e) {
            logger.error("Error Occurred in Verify Otp : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.internalServerErrorResponse();
        }
        return standardResponseMessage;
    }

    //===================
    public StandardResponseMessage setPassword(HttpServletRequest request, String newPassword, String confirmPassword) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String userId = "E001";
            // get userId in redis
            UserDetailsEntity newUser = new UserDetailsEntity();
            if (newPassword.equals(confirmPassword)) {
                Optional<UserDetailsEntity> list = newUserRepository.findById(userId);
                newUser.setUserId(list.get().getUserId());
                newUser.setUserName(list.get().getUserName());
                newUser.setPwd(confirmPassword);
                newUser.setBranchName(list.get().getBranchName());
                newUser.setUserKey(list.get().getUserKey());
                newUser.setMailId(list.get().getMailId());
                newUser.setUserModificationAccess(list.get().isUserModificationAccess());
                newUser.setSwitchBranchAccess(list.get().isSwitchBranchAccess());
                newUser.setVendorStockAccess(list.get().isVendorStockAccess());
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
        String message = "";
        try {
            String userId = request.getHeader(Constant.USER_ID);
            Optional<UserDetailsEntity> userDetails = newUserRepository.findById(userId);
            String oldPassword = passwordPojo.getOldPassword();
            String newPassword = passwordPojo.getNewPassword();
            String confirmPassword = passwordPojo.getConfirmPassword();
            String dbPassword = userDetails.get().getPwd();
            if (Validators.hasData(userDetails) && oldPassword.equals(dbPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    if (!dbPassword.equals(newPassword)) {
                        UserDetailsEntity newUserEntity1 = new UserDetailsEntity();
                        newUserEntity1.setUserId(userDetails.get().getUserId());
                        newUserEntity1.setUserName(userDetails.get().getUserName());
                        newUserEntity1.setPwd(confirmPassword);
                        newUserEntity1.setUserModificationAccess(userDetails.get().isUserModificationAccess());
                        newUserEntity1.setSwitchBranchAccess(userDetails.get().isSwitchBranchAccess());
                        newUserEntity1.setVendorStockAccess(userDetails.get().isVendorStockAccess());
                        newUserEntity1.setUserKey(userDetails.get().getUserKey());
                        newUserEntity1.setBranchName(userDetails.get().getBranchName());
                        newUserEntity1.setMailId(userDetails.get().getMailId());
                        newUserRepository.save(newUserEntity1);
                        return StandardResposneUtil.successMessage("Password successfully changed");
                    } else
                        message = "Old Password and New Password must not be Same";
                } else
                    message = "New Password and Confirm Password Not Matching";
            } else
                message = "InCorrect Old Password";
        } catch (Exception e) {
            logger.error("Error Occurred in Change Password : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }

        return StandardResposneUtil.noDataMessage(message);

    }
}

