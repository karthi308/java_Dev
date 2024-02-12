package com.io.tech.controller;

import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.AuthnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/authn")
@RestController
public class AuthnController {

    @Autowired
    AuthnService service;

    @GetMapping("/login")
    public ResponseEntity<StandardMessageResponse> login(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String user_id, @RequestParam String password) {
        StandardMessageResponse response = null;
        try {
            response = service.login(companyId, user_id, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in login Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/change/password")
    public ResponseEntity<StandardMessageResponse> changePassword(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String user_id, @RequestParam("old_password") String oldPassword, @RequestParam("new_password") String newPassword, @RequestParam("confirm_password") String confirmPassword) {
        StandardMessageResponse response = null;
        try {
            response = service.changePassword(companyId, user_id, oldPassword, newPassword, confirmPassword);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in changePassword Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<StandardMessageResponse> login(@RequestHeader("company-Id") String companyId, @RequestHeader("user-Id") String user_id) {
        StandardMessageResponse response = null;
        try {
            response = service.logout(companyId, user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred in logout Controller : " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
