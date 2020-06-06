package com.example.pointcutpractice.service;

import com.example.pointcutpractice.model.people.Human;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlaneTravelService implements TravelService {

    @Override
    public void travel(Human human) {
        log.info("{} has left the country by plane", human.getClass().getSimpleName());
    }
}
