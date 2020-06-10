package com.example.pointcutpractice.advice;

import com.example.pointcutpractice.model.people.*;
import com.example.pointcutpractice.service.PlaneTravelService;
import com.example.pointcutpractice.service.ShipTravelService;
import com.example.pointcutpractice.service.TrainTravelService;
import com.example.pointcutpractice.service.TravelService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.stream.Stream;

import static com.example.pointcutpractice.advice.BorderPatrolTest.Config;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {BorderPatrol.class, PlaneTravelService.class, ShipTravelService.class,
        TrainTravelService.class, Config.class})
class BorderPatrolTest {

    private static final American AMERICAN = new American();
    private static final Caucasian CAUCASIAN = new Caucasian();
    private static final Ethiopian ETHIOPIAN = new Ethiopian();
    private static final Mongolian MONGOLIAN = new Mongolian();

    @MockBean
    private TacticalUnit tacticalUnit;

    @Autowired
    private ApplicationContext applicationContext;

    private static final ExceptionVerifier<TravelService, Human> EXCEPTION_THROWN =
            (travelService, human) -> assertThatThrownBy(() -> travelService.travel(human))
                    .isExactlyInstanceOf(VirusDetectedAlert.class);

    private static final ExceptionVerifier<TravelService, Human> VERIFY_NO_EXCEPTION =
            (travelService, human) -> assertThatCode(() -> travelService.travel(human)).doesNotThrowAnyException();

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    void testVirusHunter(final Human human,
                         final int expectedTimes,
                         final ExceptionVerifier<TravelService, Human> exceptionVerifier,
                         final Class<? extends TravelService> travelServiceType) {

        final TravelService travelService = applicationContext.getBean(travelServiceType);

        exceptionVerifier.verify(travelService, human);

        verify(tacticalUnit, times(expectedTimes)).capture();
    }

    private static Stream<Arguments> argumentsProvider() {
        return Stream.of(
                Arguments.of(CAUCASIAN, 1, EXCEPTION_THROWN, ShipTravelService.class),
                Arguments.of(CAUCASIAN, 0, VERIFY_NO_EXCEPTION, PlaneTravelService.class),
                Arguments.of(CAUCASIAN, 0, VERIFY_NO_EXCEPTION, TrainTravelService.class),
                Arguments.of(AMERICAN, 0, VERIFY_NO_EXCEPTION, ShipTravelService.class),
                Arguments.of(AMERICAN, 0, VERIFY_NO_EXCEPTION, PlaneTravelService.class),
                Arguments.of(AMERICAN, 0, VERIFY_NO_EXCEPTION, TrainTravelService.class),
                Arguments.of(ETHIOPIAN, 0, VERIFY_NO_EXCEPTION, ShipTravelService.class),
                Arguments.of(ETHIOPIAN, 0, VERIFY_NO_EXCEPTION, PlaneTravelService.class),
                Arguments.of(ETHIOPIAN, 0, VERIFY_NO_EXCEPTION, TrainTravelService.class),
                Arguments.of(MONGOLIAN, 0, VERIFY_NO_EXCEPTION, ShipTravelService.class),
                Arguments.of(MONGOLIAN, 0, VERIFY_NO_EXCEPTION, PlaneTravelService.class),
                Arguments.of(MONGOLIAN, 0, VERIFY_NO_EXCEPTION, TrainTravelService.class)
        );
    }

    @TestConfiguration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    static class Config {

    }

    @FunctionalInterface
    interface ExceptionVerifier<T, U> {
        void verify(T t, U u);
    }
}