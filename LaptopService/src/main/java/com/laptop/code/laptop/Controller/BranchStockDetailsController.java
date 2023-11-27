package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.BranchPojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.service.BranchStockDetailsService;
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
public class BranchStockDetailsController {
    @Autowired
    BranchStockDetailsService service;

    @GetMapping("/get/branch/all/stock/details")
    public ResponseEntity<StandardResponseMessage> getAllStockDetails(HttpServletRequest request) {
        try {
            StandardResponseMessage result = service.getAllStockDetails(request);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in getAllStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/get/branch/stock/details/by/status")
    public ResponseEntity<StandardResponseMessage> getStockDetailsByStatus(HttpServletRequest request, @RequestParam String status) {
        try {
            StandardResponseMessage result = service.getStockDetailsByStatus(request, status);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in getAllStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/get/branch/stock/details")
    public ResponseEntity<StandardResponseMessage> getStockDetails(@RequestBody StockDetailsPojo stockDetailsPojo) {
        try {
            StandardResponseMessage result = service.getStockDetails(stockDetailsPojo.getStockId());
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in getStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/transfer/stock")
    public ResponseEntity<StandardResponseMessage> transferStock(HttpServletRequest request, @RequestBody StockDetailsPojo stockDetailsPojo) {
        try {
            StandardResponseMessage result = service.transferStock(request, stockDetailsPojo);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in transferStock Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
