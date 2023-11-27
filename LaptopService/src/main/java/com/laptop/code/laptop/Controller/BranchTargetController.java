package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.service.BranchTargetService;
import com.laptop.code.laptop.util.CommonUtil;
import com.laptop.code.laptop.util.StandardResposneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BranchTargetController {

    @Autowired
    BranchTargetService service;

//    @RequestMapping(value = "/get/branch/target", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public ResponseEntity<StandardResponseMessage> getBranchTarget(@RequestBody StockDetailsPojo stockListPojo) {
//        STring result = "service.getBranchTarget(stockListPojo)";
//        try {
//            return CommonUtil.getReturnResponse(result);
//        } catch (Exception e) {
//            log.error("Error occurred in getBranchTarget Controller :" + e.getMessage());
//            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
}
