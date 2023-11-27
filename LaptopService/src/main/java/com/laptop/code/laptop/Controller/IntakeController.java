package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.CustomerDetailsPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.service.IntakeService;
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
public class IntakeController {
    @Autowired
    IntakeService service;
    private static Logger logger = LoggerFactory.getLogger(IntakeController.class);

    @RequestMapping(value = "/add/intake", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getIntake(HttpServletRequest request, @RequestBody CustomerDetailsPojo customerDetailsPojo) {
        StandardResponseMessage result = service.addIntake(request, customerDetailsPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getIntake Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "get/all/customer/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getAllClientDetails(HttpServletRequest request, @RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.getAllCustomerDetails(request, statusPojo.getSearchNo());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getAllClientDetails  Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @RequestMapping(value = "get/customer/details/by/mobileNo", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getCustomerDetailsByMobileNo(@RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.getCustomerDetailsByMobileNo(statusPojo.getMobileNo());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getCustomerDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "get/customer/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getCustomerDetails(HttpServletRequest request, @RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.getCustomerDetails(request, statusPojo.getStatus());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getCustomerDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @RequestMapping(value = "search/customer/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> searchCustomerDetails(HttpServletRequest request, @RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.getCustomerDetails(request, statusPojo.getStatus());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getCustomerDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

}
