package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.service.BranchStockDetailsService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
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

    @GetMapping("/get/branch/stock/details")
    public ResponseEntity<StandardResponseMessage> getBranchStockDetails() {
        try {
            StandardResponseMessage result = service.getBranchStockDetails();
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in getBranchStockDetails Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/transfer/stock")
    public ResponseEntity<StandardResponseMessage> transferStock(@RequestBody StockDetailsPojo stockDetailsPojo) {
        try {
            StandardResponseMessage result = service.transferStock(stockDetailsPojo);
            return CommonUtil.getReturnResponse(result);
        } catch (Exception e) {
            log.error("Error occurred in transferStock Controller :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
