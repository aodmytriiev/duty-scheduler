package io.github.aodmytriiev;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.apache.commons.lang3.tuple.Pair;

public class ScheduleReviewer {

    private static final String IS_SCHEDULE_CORRECT_MESSAGE =
            "Is this schedule correct? (yes/no): ";
    private Scanner scanner;

    public ScheduleReviewer(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<OnDuty> reviewSchedule(List<OnDuty> onDutyList) {
        SchedulePrinter.print(onDutyList);

        var answer = askUserAndReturnResult(IS_SCHEDULE_CORRECT_MESSAGE);
        if (readyToUpdateCalendar(answer)) {
            return onDutyList;

        } else {
            while (!readyToUpdateCalendar(answer)) {
                updateSchedule(onDutyList);
                System.out.println("Updated schedule: ");
                SchedulePrinter.print(onDutyList);
                answer = askUserAndReturnResult(IS_SCHEDULE_CORRECT_MESSAGE);
            }

            return onDutyList;
        }
    }

    private String askUserAndReturnResult(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    private boolean readyToUpdateCalendar(String isScheduleCorrect) {
        return Objects.equals(isScheduleCorrect, "yes");
    }

    private void updateSchedule(List<OnDuty> scheduleData) {
        var changeRequest = askUserAndReturnResult(
                "Choose ids of the schedule from the table that need to be interchanged (ex: schedule with ids 2,3 and 4,6 should be interchanged: 2-3, 4-6): ");
        var idPairsToUpdate =
                Arrays.stream(changeRequest.trim().replace(" ", "").split(","))
                      .map(pair -> pair.split("-"))
                      .map(pair -> Pair.of(Integer.parseInt(pair[0]), Integer.parseInt(pair[1])))
                      .toList();

        for (var idPair : idPairsToUpdate) {
            var firstId = idPair.getLeft();
            var secondId = idPair.getRight();

            var scheduleItem1 = scheduleData.stream()
                                            .filter(scheduleItem -> scheduleItem.getId() == firstId)
                                            .findFirst()
                                            .orElseThrow(NoSuchElementException::new);

            var scheduleItem2 = scheduleData.stream()
                                            .filter(scheduleItem -> scheduleItem.getId()
                                                    == secondId)
                                            .findFirst()
                                            .orElseThrow(NoSuchElementException::new);

            var scheduleItemPerson1 = scheduleItem1.getPerson();
            var scheduleItemPerson2 = scheduleItem2.getPerson();

            for (var scheduleItem : scheduleData) {
                if (scheduleItem.getId() == firstId) {
                    scheduleItem.setPerson(scheduleItemPerson2);
                }
                if (scheduleItem.getId() == secondId) {
                    scheduleItem.setPerson(scheduleItemPerson1);
                }
            }
        }
    }

}
