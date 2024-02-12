package com.io.tech.controller;

import com.io.tech.pojo.ProductPojo;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ProductController {

    @Autowired
    ProductServices service;

    @PutMapping("/generate/intake")
    public ResponseEntity<StandardMessageResponse> generateIntake(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody ProductPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.generateIntake(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in generateIntake Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/product/list")
    public ResponseEntity<StandardMessageResponse> getProductList(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.getProductList(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getProductList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/latest/product")
    public ResponseEntity<StandardMessageResponse> getLatestProduct(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.getLatestProductDetails(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addProduct Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/product/details")
    public ResponseEntity<StandardMessageResponse> getProductDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam String search_number) {
        StandardMessageResponse response = null;
        try {
            response = service.getProductDetails(companyId, userId, search_number);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getProductDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/product/status")
    public ResponseEntity<StandardMessageResponse> getProductStatus(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getProductStatus(companyId, userId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getProductStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/product/status/details")
    public ResponseEntity<StandardMessageResponse> getProductStatusDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam String search_number, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getProductStatusDetails(companyId, userId, search_number, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getProductStatusDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/product/details")
    public ResponseEntity<StandardMessageResponse> updateProductDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody ProductPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateProductDetails(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateProductDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/product/details")
    public ResponseEntity<StandardMessageResponse> removeProductDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestParam("intake_no") String intakeNo) {
        StandardMessageResponse response = null;
        try {
            response = service.removeProductDetails(companyId, userId, intakeNo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeProductDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
