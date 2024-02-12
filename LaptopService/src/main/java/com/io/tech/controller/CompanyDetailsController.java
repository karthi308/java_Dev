package com.io.tech.controller;

import com.io.tech.pojo.CompanyDetailsPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.CompanyDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CompanyDetailsController {
    @Autowired
    CompanyDetailsService service;

    @PutMapping("/add/company")
    public ResponseEntity<StandardMessageResponse> addCompanyDetails(@RequestBody CompanyDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.getAndSetCompanyDetails(pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addCompanyDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/company/list")
    public ResponseEntity<StandardMessageResponse> getCompanyList() {
        StandardMessageResponse response = null;
        try {
            response = service.getCompanyList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getCompanyList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/company/details")
    public ResponseEntity<StandardMessageResponse> getCompanyDetails(@RequestHeader("company-Id") String companyId) {
        StandardMessageResponse response = null;
        try {
            response = service.getCompanyDetails(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getCompanyDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/company/status")
    public ResponseEntity<StandardMessageResponse> getCompanyStatus(String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getCompanyStatus(status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getCompanyStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/company/details")
    public ResponseEntity<StandardMessageResponse> updateCompanyDetails(@RequestHeader("company-Id") String companyId, @RequestBody CompanyDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateCompanyDetails(companyId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateCompanyDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/company/details")
    public ResponseEntity<StandardMessageResponse> removeCompanyDetails(@RequestHeader("company-Id") String companyId) {
        StandardMessageResponse response = null;
        try {
            response = service.removeCompanyDetails(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeCompanyDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
