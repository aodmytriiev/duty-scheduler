package io.github.aodmytriiev;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        var scheduler = new Scheduler();
        var schedule = scheduler.createSchedule();
        var onDuty = scheduler.mapToOnDuty(schedule);
        var reviewedOnDuty = new ScheduleReviewer(SCANNER).reviewSchedule(onDuty);
        var calendarCreator = new CalendarCreator();
        var iCalContent = calendarCreator.createICalendarContent(reviewedOnDuty);
        calendarCreator.saveScheduleToICalFile(iCalContent);

        SCANNER.close();
    }
}
