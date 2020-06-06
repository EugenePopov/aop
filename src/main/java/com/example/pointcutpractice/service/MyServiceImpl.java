package com.example.pointcutpractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyServiceImpl implements MyService {

    @Override
    public String serve(String data) {
        log.info("Target Called");
        return "Target called";
    }
}
