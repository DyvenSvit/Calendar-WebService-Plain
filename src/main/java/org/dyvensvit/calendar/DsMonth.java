package org.dyvensvit.calendar;

import java.util.List;

public class DsMonth {

    private String month;

    private String year;

    private List<DsDayTiny> days;

    public List<DsDayTiny> getDays() {
        return days;
    }

    public void setDays(List<DsDayTiny> days) {
        this.days = days;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DsMonth dsMonth = (DsMonth) o;

        if (!month.equals(dsMonth.month)) return false;
        return year.equals(dsMonth.year);

    }

    @Override
    public int hashCode() {
        int result = month.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }
}
