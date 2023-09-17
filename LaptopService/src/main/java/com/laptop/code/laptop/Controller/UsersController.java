package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.service.UsersService;
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
public class UsersController {
    @Autowired
    UsersService service;
    private static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping(value = "/add/user", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> addUser(@RequestBody AddUserPojo createNewUserPojo) {
        StandardResponseMessage result = service.addUser(createNewUserPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "/get/users/list", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getUserList() {
        StandardResponseMessage result = service.getAllUsersDetails();
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "/remover/user", method = RequestMethod.DELETE, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> removeUser(@RequestBody AddUserPojo userId) {
        StandardResponseMessage result = service.removeUser(userId.getUserId());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "get/user/details", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> userDetails(HttpServletRequest request) {
        StandardResponseMessage result = service.getUserDetails(request);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
