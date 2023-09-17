package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.CustomerDetailsPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.service.IntakeService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class IntakeController {
    @Autowired
    IntakeService service;
    private static Logger logger = LoggerFactory.getLogger(IntakeController.class);

    @RequestMapping(value = "/add/intake", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getIntake(@RequestBody CustomerDetailsPojo customerDetailsPojo) {
        StandardResponseMessage result =  service.addIntake(customerDetailsPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getIntake Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "get/customer/details/by/Status", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getClientDetailsByStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.getByStatus(statusPojo.getStatus());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Get Client DetailsByStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }
    @RequestMapping(value = "/update/client/details", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result =service.updateStatus(statusPojo.getIntakeNo(), statusPojo.getStatus());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @RequestMapping(value = "/update/Rejected/Status", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateRejectedStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.updateRejectedStatus(statusPojo.getIntakeNo(), statusPojo.getRejectedReason());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
