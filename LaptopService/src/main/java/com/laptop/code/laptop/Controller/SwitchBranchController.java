package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.AddUserPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.service.SwitchBranchService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SwitchBranchController {

    @Autowired
    SwitchBranchService service;

    @PutMapping(value = "/switch/branch")
    public ResponseEntity<StandardResponseMessage> switchBranch(HttpServletRequest request, @RequestBody AddUserPojo addUserPojo) {
        try {
            StandardResponseMessage result = service.switchBranch(request, addUserPojo);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in switchBranch Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/get/branch/list")
    public ResponseEntity<StandardResponseMessage> getSwitchBranchList(HttpServletRequest request) {
        try {
            StandardResponseMessage result = service.getSwitchBranchList(request);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in getSwitchBranchList Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
