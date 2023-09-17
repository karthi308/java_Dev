package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.service.UserBranchService;
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
public class UserBranchController {
    @Autowired
    UserBranchService service;
    private static Logger logger = LoggerFactory.getLogger(UserBranchController.class);


    @RequestMapping(value = "/switch/Branch", method = RequestMethod.POST, produces = {"application/json;"})
    public ResponseEntity<StandardResponseMessage> switchBranch(HttpServletRequest request, @RequestBody StatusPojo branch) {
        StandardResponseMessage result = service.switchBranch(request, branch.getBranch());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "/get/switched/branch/name", method = RequestMethod.GET, produces = {"application/json;"})
    public ResponseEntity<StandardResponseMessage> getSwitchBranchName(HttpServletRequest request) {
        StandardResponseMessage result = service.getSwitchedBranchName(request);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "/get/branch/list", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getBranchName(HttpServletRequest request) {
        StandardResponseMessage result =  service.getAllBranchName(request);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
