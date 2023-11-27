package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.service.CustomerStockDetailsService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class CustomerStockDetailsController {

    @Autowired
    CustomerStockDetailsService service;

    @RequestMapping(value = "/add/customer/stock/details", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> addCustomerStockDetails(@RequestBody StockDetailsPojo stockListPojo) {
        StandardResponseMessage result = service.addCustomerStockDetails(stockListPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in addCustomerStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/get/customer/stock/details", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> getCustomerStockDetails(@RequestBody StockDetailsPojo stockListPojo) {
        StandardResponseMessage result = service.getCustomerStockList(stockListPojo.getIntakeNo());
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in addCustomerStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @RequestMapping(value = "/remove/customer/stock/details", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<StandardResponseMessage> removeCustomerStockDetails(@RequestBody StockDetailsPojo stockListPojo) {
        StandardResponseMessage result = service.removeCustomerStockDetails(stockListPojo);
        try {
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in removeCustomerStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
