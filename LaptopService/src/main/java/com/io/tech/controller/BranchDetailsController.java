package com.io.tech.controller;

import com.io.tech.pojo.BranchDetailsPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.BranchDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BranchDetailsController {

    @Autowired
    BranchDetailsService service;

    @PutMapping("/add/branch")
    public ResponseEntity<StandardMessageResponse> addBranchDetails(@RequestHeader("company-Id") String companyId, @RequestBody BranchDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.addBranchDetails(companyId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addBranchDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/list")
    public ResponseEntity<StandardMessageResponse> getBranchList(@RequestHeader("company-Id") String companyId) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchList(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/name/list")
    public ResponseEntity<StandardMessageResponse> getBranchNameList(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchNameList(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchNameList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/details")
    public ResponseEntity<StandardMessageResponse> getBranchDetails(@RequestHeader("company-Id") String companyId, @RequestParam String branch_name) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchDetails(companyId, branch_name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/branch/status")
    public ResponseEntity<StandardMessageResponse> getBranchStatus(@RequestHeader("company-Id") String companyId, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getBranchStatus(companyId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getBranchStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/update/branch/details")
    public ResponseEntity<StandardMessageResponse> updateBranchDetails(@RequestHeader("company-Id") String companyId, @RequestBody BranchDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateBranchDetails(companyId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateBranchDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/branch/details")
    public ResponseEntity<StandardMessageResponse> removeBranchDetails(@RequestHeader("company-Id") String companyId, @RequestParam String branch_name) {
        StandardMessageResponse response = null;
        try {
            response = service.removeBranchDetails(companyId, branch_name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeBranchDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("switch/branch")
    public ResponseEntity<StandardMessageResponse> switchBranch(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam("switch_branch_name") String switchBranchName) {
        StandardMessageResponse response = null;
        try {
            response = service.switchBranchName(companyId, userId, switchBranchName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeBranchDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
