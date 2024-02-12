package com.io.tech.Jerichoservices.controller;

import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.CustomerStockDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CustomerStockDetailsController {

    @Autowired
    CustomerStockDetailsService service;

    @PutMapping("add/customer/stock")
    public ResponseEntity<StandardMessageResponse> addCustomerStock(@RequestHeader("company-Id") String companyId, @RequestParam("intake_no") String intakeNo, @RequestParam("stock_id") String stock_id, @RequestParam int price) {
        StandardMessageResponse response = null;
        try {
            response = service.addCustomerStock(companyId, intakeNo, stock_id, price);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addCustomerStock Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/customer/stock/list")
    public ResponseEntity<StandardMessageResponse> getCustomerStockList(@RequestHeader("company-Id") String companyId, @RequestParam("intake_no") String intakeNo) {
        StandardMessageResponse response = null;
        try {
            response = service.getCustomerStockList(companyId, intakeNo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getCustomerStockList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/customer/stock/details")
    public ResponseEntity<StandardMessageResponse> getCustomerStockDetails(@RequestHeader("company-Id") String companyId, @RequestParam("stock_id") String stockId) {
        StandardMessageResponse response = null;
        try {
            response = service.getCustomerStockDetails(companyId, stockId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getCustomerStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/customer/stock")
    public ResponseEntity<StandardMessageResponse> updateCustomerStockDetails(@RequestHeader("company-Id") String companyId, @RequestParam("stock_id") String stockId, @RequestParam int price) {
        StandardMessageResponse response = null;
        try {
            response = service.updateCustomerStockDetails(companyId, stockId, price);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateCustomerStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/customer/stock")
    public ResponseEntity<StandardMessageResponse> removeCustomerStock(@RequestHeader("company-Id") String companyId, @RequestParam("stock_id") String stockId) {
        StandardMessageResponse response = null;
        try {
            response = service.removeCustomerStock(companyId, stockId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeCustomerStock Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}

