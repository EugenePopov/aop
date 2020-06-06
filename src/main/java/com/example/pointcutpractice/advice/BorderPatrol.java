package com.example.pointcutpractice.advice;

import com.example.pointcutpractice.model.people.Human;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class BorderPatrol {

    private final TacticalUnit tacticalUnit;

    // TODO: Implement the pointcut so that the Border Patrol will intercept
    //  the potentially infected caucasian male which will leave the country by ship
    public void capture(JoinPoint joinPoint) {
        final Human human = (Human) joinPoint.getArgs()[0];
        log.info("Good job. You prevented the spread of the virus!");
        log.info("The {} was captured.", human.getClass().getSimpleName());
        tacticalUnit.capture();
        throw new VirusDetectedAlert();
    }
}
