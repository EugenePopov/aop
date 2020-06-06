package com.example.pointcutpractice;

import com.example.pointcutpractice.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class PointcutPracticeApplication implements CommandLineRunner {

    @Autowired
    private MyService service;

    public static void main(String[] args) {
        new SpringApplicationBuilder(PointcutPracticeApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) {
        log.info("Hello");
        service.serve("");
    }
}
