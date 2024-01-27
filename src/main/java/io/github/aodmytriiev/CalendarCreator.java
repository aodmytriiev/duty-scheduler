package io.github.aodmytriiev;

import static io.github.aodmytriiev.ICalFormatter.formatDateForCalendar;
import static io.github.aodmytriiev.ICalFormatter.formatDateTimeForCalendar;
import static io.github.aodmytriiev.ICalFormatter.nameToEmailString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class CalendarCreator {

    public String createICalendarContent(List<OnDuty> schedules) {
        var calendarContentItem = "";
        for (OnDuty schedule : schedules) {
            var calendarContentForOnePerson = gedICalItem(schedule);
            calendarContentItem = calendarContentItem.concat(calendarContentForOnePerson);
        }

        return getIcalendarContent(calendarContentItem);
    }

    public void saveScheduleToICalFile(String iCalContent) throws IOException {
        var filePath = Path.of("build", "duty_schedule.ics");
        Files.write(filePath, iCalContent.getBytes());

        System.out.println("Duty Schedule saved to file: " + filePath);
    }

    private String gedICalItem(OnDuty onDuty) {
        var scheduleContent = """
                BEGIN:VEVENT
                DTSTAMP:%1$s
                DTSTART;TZID=Europe/Helsinki:%2$sT080000
                DTEND;TZID=Europe/Helsinki:%3$sT200000
                SUMMARY:%4$s - AQA Flagman
                CATEGORIES:other
                EVENT-ALLDAY:false
                ORGANIZER;CN=%4$s;CUTYPE=INDIVIDUAL:mailto:%5$s
                ATTENDEE;CN=%4$s;CUTYPE=INDIVIDUAL:mailto:%5$s
                END:VEVENT
                """;

        var timestamp = formatDateTimeForCalendar(LocalDateTime.now());
        var dateStart = formatDateForCalendar(onDuty.getShiftPeriod().shiftStart());
        var dateFinish = formatDateForCalendar(onDuty.getShiftPeriod().shiftEnd());
        var person = onDuty.getPerson();
        var email = nameToEmailString(person);

        return scheduleContent.formatted(timestamp, dateStart, dateFinish, person, email);
    }

    private String getIcalendarContent(String scheduleContent) {
        var calendar = """
                BEGIN:VCALENDAR
                PRODID:-//P&A AQA Flagman//Calendar util//EN
                VERSION:2.0
                X-WR-CALNAME:AQA Flagman
                CALSCALE:GREGORIAN
                BEGIN:VTIMEZONE
                TZID:Europe/Helsinki
                BEGIN:STANDARD
                DTSTART:19961027T040000
                RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU
                TZNAME:EET
                TZOFFSETFROM:+0300
                TZOFFSETTO:+0200
                END:STANDARD
                BEGIN:DAYLIGHT
                DTSTART:19970330T030000
                RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU
                TZNAME:EEST
                TZOFFSETFROM:+0200
                TZOFFSETTO:+0300
                END:DAYLIGHT
                END:VTIMEZONE
                %s
                END:VCALENDAR
                """;
        return calendar.formatted(scheduleContent);
    }
}
