package com.example.pointcutpractice.advice;

import com.example.pointcutpractice.service.MyService;
import com.example.pointcutpractice.service.MyServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static com.example.pointcutpractice.advice.ServiceAdviceTest.Config;

@SpringBootTest(classes = {ServiceAdvice.class, MyServiceImpl.class, Config.class})
class ServiceAdviceTest {

    @Autowired
    private MyService service;

    @Test
    void log() {
        service.serve("Heloooo");
    }

    @TestConfiguration
    @EnableAspectJAutoProxy
    static class Config {

    }
}