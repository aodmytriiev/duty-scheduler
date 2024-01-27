package io.github.aodmytriiev;

import java.util.Objects;

public class OnDuty {
    private int id;
    private String person;
    private ShiftPeriod shiftPeriod;
    private boolean endOfSprint;

    public OnDuty(int id, String person, ShiftPeriod shiftPeriod, boolean endOfSprint) {
        this.id = id;
        this.person = person;
        this.shiftPeriod = shiftPeriod;
        this.endOfSprint = endOfSprint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public ShiftPeriod getShiftPeriod() {
        return shiftPeriod;
    }

    public void setShiftPeriod(ShiftPeriod shiftPeriod) {
        this.shiftPeriod = shiftPeriod;
    }

    public boolean isEndOfSprint() {
        return endOfSprint;
    }

    public void setEndOfSprint(boolean endOfSprint) {
        this.endOfSprint = endOfSprint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OnDuty onDuty = (OnDuty) o;

        if (id != onDuty.id) {
            return false;
        }
        if (endOfSprint != onDuty.endOfSprint) {
            return false;
        }
        if (!Objects.equals(person, onDuty.person)) {
            return false;
        }
        return Objects.equals(shiftPeriod, onDuty.shiftPeriod);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (shiftPeriod != null ? shiftPeriod.hashCode() : 0);
        result = 31 * result + (endOfSprint ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OnDuty{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", shiftPeriod=" + shiftPeriod +
                ", endOfSprint=" + endOfSprint +
                '}';
    }
}
