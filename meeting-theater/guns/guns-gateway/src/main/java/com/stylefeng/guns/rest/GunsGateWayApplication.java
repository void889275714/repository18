package com.stylefeng.guns.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
public class GunsGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsGateWayApplication.class, args);
    }
}
