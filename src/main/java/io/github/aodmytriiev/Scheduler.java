package io.github.aodmytriiev;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {

    private static final AtomicInteger DUTY_ID = new AtomicInteger(1);
    private static final LocalDate SCHEDULE_START_DATE = LocalDate.now();
    private static final LocalDate SCHEDULE_END_DATE = SCHEDULE_START_DATE.plusMonths(1);
    private static final int DUTY_DURATION_DAYS = 2;
    private static final LocalDate SPRINT_START = SCHEDULE_START_DATE.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
    private static final int SPRINT_DURATION_DAYS = 14;
    private static final List<String> PERSONS = List.of("Mark", "Yuki", "Lucy", "Khan", "Bjorn", "Aurora", "Luna", "Jack");

    public void create() {
        var duty = createSchedule();
        var onDutyList = mapToOnDuty(duty);

        System.out.println("Schedule Start: " + SCHEDULE_START_DATE);
        System.out.println("Schedule End: " + SCHEDULE_END_DATE);
        SchedulePrinter.print(onDutyList);
    }

    public Map<ShiftPeriod, String> createSchedule() {
        Map<ShiftPeriod, String> dutySchedule = new HashMap<>();
        var currentDate = SCHEDULE_START_DATE;
        while (!currentDate.isAfter(SCHEDULE_END_DATE)) {
            for (var person : PERSONS) {
                currentDate = getNextWorkingDayIfWeekend(currentDate);
                var dutyEndDate = getEndDate(currentDate);

                if (currentDate.isAfter(SCHEDULE_END_DATE)) {
                    break;
                }

                dutySchedule.put(new ShiftPeriod(currentDate, dutyEndDate), person);
                currentDate = dutyEndDate.plusDays(1);
            }
        }

        return dutySchedule;
    }

    private List<OnDuty> mapToOnDuty(Map<ShiftPeriod, String> duty) {
        return duty.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().shiftStart()))
                   .map(entry ->
                                new OnDuty(
                                        DUTY_ID.getAndIncrement(),
                                        entry.getValue(),
                                        entry.getKey(),
                                        isEndOfSprint(entry.getKey())))
                   .toList();
    }

    private boolean isEndOfSprint(ShiftPeriod shiftPeriod) {
        var sprintStartDates = getSprintStartDatesDuringSchedule();
        return sprintStartDates.contains(shiftPeriod.shiftStart()) || sprintStartDates.contains(shiftPeriod.shiftEnd());
    }

    private List<LocalDate> getSprintStartDatesDuringSchedule() {
        List<LocalDate> sprintStartDates = new ArrayList<>();

        var sprintStart = SPRINT_START;
        sprintStartDates.add(sprintStart);

        while (!sprintStart.isAfter(SCHEDULE_END_DATE)) {
            sprintStart = sprintStart.plusDays(SPRINT_DURATION_DAYS);
            sprintStartDates.add(sprintStart);
        }

        return sprintStartDates;
    }

    private LocalDate getEndDate(LocalDate startDate) {
        var endDate = startDate.plusDays(DUTY_DURATION_DAYS).minusDays(1);

        return getNextWorkingDayIfWeekend(endDate);
    }

    private LocalDate getNextWorkingDayIfWeekend(LocalDate date) {
        if (isWeekend(date)) {
            return date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return date;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
