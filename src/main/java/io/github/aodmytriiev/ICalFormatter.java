package io.github.aodmytriiev;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ICalFormatter {

    private ICalFormatter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String formatDateTimeForCalendar(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
    }

    public static String formatDateForCalendar(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String nameToEmailString(String name) {
        return name.toLowerCase().replace(" ", ".").concat("@example.com");
    }
}
