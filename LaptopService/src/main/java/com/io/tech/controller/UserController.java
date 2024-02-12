package com.io.tech.controller;

import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.pojo.UserDetailsPojo;
import com.io.tech.service.UserDetailsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserDetailsServices service;

    @PutMapping("/add/user")
    public ResponseEntity<StandardMessageResponse> addUser(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody UserDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.addUser(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in addUser Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/user/list")
    public ResponseEntity<StandardMessageResponse> getUserList(@RequestHeader("company-Id") String companyId) {
        StandardMessageResponse response = null;
        try {
            response = service.getUserList(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getUserList Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/user/details")
    public ResponseEntity<StandardMessageResponse> getUserDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.getUserDetails(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getUserDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/user/status")
    public ResponseEntity<StandardMessageResponse> getUserStatus(@RequestHeader("company-Id") String companyId, @RequestParam String status) {
        StandardMessageResponse response = null;
        try {
            response = service.getUserStatus(companyId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in getUserStatus Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("update/user/details")
    public ResponseEntity<StandardMessageResponse> updateUserDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId, @RequestBody UserDetailsPojo pojo) {
        StandardMessageResponse response = null;
        try {
            response = service.updateUserDetails(companyId, userId, pojo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in updateUserDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("remove/user/details")
    public ResponseEntity<StandardMessageResponse> removeUserDetails(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String userId) {
        StandardMessageResponse response = null;
        try {
            response = service.removeUserDetails(companyId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in removeUserDetails Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
