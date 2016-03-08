package org.dyvensvit.calendar;

import java.util.List;

public class DsMonth {

    private Integer month;

    private Integer year;

    private List<DsDay> days;

    public List<DsDay> getDays() {
        return days;
    }

    public void setDays(List<DsDay> days) {
        this.days = days;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
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
