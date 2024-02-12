package com.io.tech.controller;

import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.BranchTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BranchTargetController {

    @Autowired
    BranchTargetService service;


    @PutMapping("/branch/target")
    public ResponseEntity<StandardMessageResponse> addBranchTarget(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam("branch_name") String branchName, @RequestParam("target_amount") int targetAmount, @RequestParam String date) {
        StandardMessageResponse response = null;
        try {
            response = service.addBranchTarget(companyId, userId, branchName, targetAmount, date);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addBranchTarget Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
