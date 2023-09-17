package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.OtpPojo;
import com.laptop.code.laptop.pojo.PasswordPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.service.ForgotPasswordService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class ForgotPasswordController {
    @Autowired
    ForgotPasswordService service;
    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> forgotPassword(@RequestBody AddUserPojo addUserPojo) {
        try {
            StandardResponseMessage result = service.getForgetPasswordOtp(addUserPojo.getUserId());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in forgotPassword :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/verify/otp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getVerifyOtp(@RequestBody OtpPojo otpPojo) {
        try {
            StandardResponseMessage result = service.getVerifyOtp(otpPojo.getOtp());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in verifyOtp :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/resend/otp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> resendOtp() {
        try {
            StandardResponseMessage result = service.resendOtp();
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in verifyOtp :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/set/password", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> newPassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
        try {
            StandardResponseMessage result = service.setPassword(request, passwordPojo.getNewPassword(), passwordPojo.getConfirmPassword());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in changePassword :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> setPassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
        try {
            StandardResponseMessage result = service.changePassword(request, passwordPojo);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in changePassword :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
