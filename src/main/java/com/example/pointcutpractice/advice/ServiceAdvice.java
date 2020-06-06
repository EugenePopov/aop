package com.example.pointcutpractice.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAdvice {

    @Before("within(com.example.pointcutpractice.service..*)")
    public void log() {
        log.info("=================Aspect Called======================");
    }
}
