package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.service.LTService;
import com.laptop.code.laptop.service.SessionService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/laptop/service")
@CrossOrigin(origins = "http://localhost:3008")
public class SessionController {

    @Autowired
    SessionService service;

    private static Logger logger = LoggerFactory.getLogger(SessionController.class);


    @Autowired
    LTService ltService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<StandardResponseMessage> login(HttpServletResponse response, @RequestBody AddUserPojo loginDetails) {
        try {
            StandardResponseMessage result = service.doLogin(response, loginDetails.getUserId().toUpperCase(), loginDetails.getPwd());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Login Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<StandardResponseMessage> logout(HttpServletRequest request) {
        try {
            StandardResponseMessage result = service.logout(request);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Login Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
