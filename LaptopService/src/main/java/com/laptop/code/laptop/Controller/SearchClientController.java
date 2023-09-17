package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StatusPojo;
import com.laptop.code.laptop.service.SearchClientService;
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
public class SearchClientController {

    @Autowired
    SearchClientService service;
    private static Logger logger = LoggerFactory.getLogger(SearchClientController.class);

    @RequestMapping(value = "/fetch/all/client/details", method = RequestMethod.POST)
    public ResponseEntity<StandardResponseMessage> fetchAllClientDetails(@RequestBody StatusPojo statusPojo) {
        StandardResponseMessage result = service.fetchAllClientDetails(statusPojo.getSearchNo());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Get Client DetailsByStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/fetch/handIn/client/details", method = RequestMethod.POST)
    public ResponseEntity<StandardResponseMessage> fetchHandInClientDetails(@RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.fetchHandInClientDetails(statusPojo.getSearchNo());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Get Client DetailsByStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/get/customer/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getCustomerDetails(HttpServletRequest request, @RequestBody StatusPojo statusPojo) {
        try {
            StandardResponseMessage result = service.getCustomerDetails(statusPojo.getMobileNo());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in Get Client DetailsByStatus Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
