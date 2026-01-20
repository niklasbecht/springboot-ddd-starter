package com.becht.staffMemberService.adapter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceHealthController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String healthCheck() {
        return "Service "+appName+" is up and running";
    }
}