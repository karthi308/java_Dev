package com.laptop.code.laptop.Controller;


import com.laptop.code.laptop.pojo.CustomerDetailsPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.service.UpdateStatusService;
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
public class UpdateStatusController {
    @Autowired
    UpdateStatusService service;
    private static Logger logger = LoggerFactory.getLogger(UpdateStatusController.class);

    @RequestMapping(value = "/problem/identified", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> problemIdentified(@RequestBody CustomerDetailsPojo ltPojo) {
        StandardResponseMessage result = service.problemIdentified(ltPojo.getIntakeNo(), ltPojo.getProblemIdentified(), ltPojo.getPrice());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateRejectedStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @RequestMapping(value = "/update/product/status", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.updateStatus(statusPojo.getIntakeNo(), statusPojo.getStatus());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/update/approved/status", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateApprovedStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.updateApprovedStatus(statusPojo.getIntakeNo(), statusPojo.getPrice());
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

    @RequestMapping(value = "/hand/out/ready", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> updateHandOutReadyStatus(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.updateHandOutReadyStatus(statusPojo.getIntakeNo());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in updateHandOutReadyStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


//    @RequestMapping(value="/fetchUser/details",method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject fetchUserDetails(@RequestBody StatusPojo statusPojo){
//        return ltService.fetchUserDetails(statusPojo.getSearchNo());
//    }
}
