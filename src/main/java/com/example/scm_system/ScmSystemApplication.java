package com.example.scm_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScmSystemApplication.class, args);
    }

}
