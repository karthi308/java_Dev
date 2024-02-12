package com.io.tech.controller;

import com.io.tech.pojo.BranchStockDetailsPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.BranchStockDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BranchStockDetailsController {

    @Autowired
    BranchStockDetailsService service;


    @GetMapping("/branch/stock/list")
    public ResponseEntity<StandardMessageResponse> getBranchStockList(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchStockList(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchStockList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/stock/details")
    public ResponseEntity<StandardMessageResponse> getBranchStockDetails(@RequestHeader("company-Id") String companyId, @RequestParam String stock_id) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchDetails(companyId, stock_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/stock/status")
    public ResponseEntity<StandardMessageResponse> getBranchStockStatus(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchStockStatus(companyId, userId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchStockStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/branch/stock")
    public ResponseEntity<StandardMessageResponse> updateBranchStockDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody BranchStockDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateBranchStockDetails(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateBranchStockDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
