package com.io.tech.controller;

import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.pojo.StockDetailsPojo;
import com.io.tech.service.StockDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class StockDetailsController {

    @Autowired
    StockDetailsService service;

    @PutMapping("add/stock")
    public ResponseEntity<StandardMessageResponse> addStock(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody StockDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.addStock(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addStock Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/stock/list")
    public ResponseEntity<StandardMessageResponse> getStockList(@RequestHeader("company-Id") String companyId) {
        StandardMessageResponse response = null;
        try {
            response = service.getStockList(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getStockList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/stock/details")
    public ResponseEntity<StandardMessageResponse> getStockDetails(@RequestHeader("company-Id") String companyId, @RequestParam String stock_id) {
        StandardMessageResponse response = null;
        try {
            response = service.getStockDetails(companyId, stock_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/stock/status")
    public ResponseEntity<StandardMessageResponse> getStockStockStatus(@RequestHeader("company-Id") String companyId, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getStockStatus(companyId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getStockStockStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/stock/details")
    public ResponseEntity<StandardMessageResponse> updateBranchStockDetails(@RequestHeader("company-Id") String companyId, @RequestBody StockDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateBranchDetails(companyId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateBranchStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/stock/details")
    public ResponseEntity<StandardMessageResponse> removeStockDetails(@RequestHeader("company-Id") String companyId, @RequestParam String stock_id) {
        StandardMessageResponse response = null;
        try {
            response = service.removeStockDetails(companyId, stock_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
