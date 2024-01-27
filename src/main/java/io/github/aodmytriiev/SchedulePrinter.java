package io.github.aodmytriiev;

import java.util.List;

public class SchedulePrinter {
    private SchedulePrinter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void print(List<OnDuty> onDutyList) {
        System.out.println(
                "+------+------------------------+------------------------+------------------------+-------------------------+");
        System.out.println(
                "| ID   | Duty Start             | Duty Finish            | Person                 | End of Sprint           |");
        System.out.println(
                "+------+------------------------+------------------------+------------------------+-------------------------+");

            for (var onDuty : onDutyList) {
                var endOfSprint = onDuty.isEndOfSprint() ? "YES" : "";
                System.out.printf(
                        "| %-4s | %-22s | %-22s | %-22s | %-22s  |%n",
                        onDuty.getId(),
                        onDuty.getShiftPeriod().shiftStart(),
                        onDuty.getShiftPeriod().shiftEnd(),
                        onDuty.getPerson(),
                        endOfSprint
                );
            }

        System.out.println(
                "+------+------------------------+------------------------+------------------------+-------------------------+");
        }
}
