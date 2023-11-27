package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.service.UsersService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class UsersController {
    @Autowired
    UsersService service;

    @PutMapping("/add/user")
    public ResponseEntity<StandardResponseMessage> addUser(HttpServletRequest request, @RequestBody AddUserPojo createNewUserPojo) {
        StandardResponseMessage result = service.addUser(request, createNewUserPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
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
            log.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/get/user/Details/by/Status", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getUserDetailsByStatus(@RequestBody AddUserPojo userId) {
        StandardResponseMessage result = service.getUserDetailsByStatus(userId.getStatus());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in removeUser Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }



    @PostMapping("/get/user/details")
    public ResponseEntity<StandardResponseMessage> userDetails(HttpServletRequest request, @RequestBody AddUserPojo pojo) {
        StandardResponseMessage result = service.getUserDetails(pojo.getUserId());
        try {
            return CommonUtil.getReturnResponse(service.getUserDetails(pojo.getUserId()));
        } catch (Exception e) {
            log.error("Error occurred in userDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/update/user/details", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateUserDetails(HttpServletRequest request, @RequestBody AddUserPojo addUserPojo) {
        StandardResponseMessage result = service.updateUserDetails(request, addUserPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/remover/user", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> removeUser(@RequestBody AddUserPojo userId) {
        StandardResponseMessage result = service.removeUser(userId.getUserId());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in removeUser Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
