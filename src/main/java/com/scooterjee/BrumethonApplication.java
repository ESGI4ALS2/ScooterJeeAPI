package com.scooterjee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BrumethonApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrumethonApplication.class, args);
    }
}
