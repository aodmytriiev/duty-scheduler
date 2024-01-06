package io.github.aodmytriiev;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {
    private static final LocalDate SCHEDULE_START_DATE = LocalDate.now();
    private static final LocalDate SCHEDULE_END_DATE = LocalDate.now().plusDays(7);
    private static final int DUTY_DURATION_DAYS = 2;
    private static final List<String> ON_DUTY = List.of("Andrii", "Sergii");

        public static void main(String[] args) {

            new Scheduler().create();

    }
}
