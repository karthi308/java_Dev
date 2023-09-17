package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.service.IntakePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntakePdfController {
    @Autowired
    IntakePdfService service;
    @RequestMapping("/hii")
    public String get(){
        return service.generatePdf();
    }
}
