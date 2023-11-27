package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.service.StockService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.Constant;
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
public class StockController {
    @Autowired
    StockService service;

    private static Logger logger = LoggerFactory.getLogger(StockController.class);

    @RequestMapping(value = "/add/stock/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> addVenStockDetails(@RequestBody StockDetailsPojo stockListPojo) {
        StandardResponseMessage result = service.addStockDetails(stockListPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in addVenStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/get/stock/details", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getStockDetails(HttpServletRequest request, @RequestParam String status) {
        StandardResponseMessage result = service.getStockDetails(request.getHeader(Constant.USER_ID), status);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            logger.error("Error occurred in getStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

//    @RequestMapping(value = "/get/all/customer/stock/details", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public ResponseEntity<StandardResponseMessage> getAllCustomerStockDetails(@RequestBody StockDetailsPojo stockListPojo) {
//        StandardResponseMessage result = service.getAllCustomerStockDetails(stockListPojo);
//        try {
//            return CommonUtil.getReturnResponse(result);
//        } catch (Exception e) {
//            logger.error("Error occurred in addCustomerStockDetails Controller :" + e.getMessage());
//            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }




