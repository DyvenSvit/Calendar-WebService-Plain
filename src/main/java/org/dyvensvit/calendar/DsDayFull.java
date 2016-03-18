package org.dyvensvit.calendar;

public class DsDayFull extends DsDayTiny {
    //TODO: Comments
    private String month;
    private String year;
    private String liturgy;
    private String morning;
    private String night;
    private String hours;
    private String readings;
    private String saints;
    private String quotes;

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

    public String getLiturgy() {
        return liturgy;
    }

    public void setLiturgy(String liturgy) {
        this.liturgy = liturgy;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getReadings() {
        return readings;
    }

    public void setReadings(String readings) {
        this.readings = readings;
    }

    public String getSaints() {
        return saints;
    }

    public void setSaints(String saints) {
        this.saints = saints;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DsDayFull dsDay = (DsDayFull) o;

        if (!year.equals(dsDay.year)) return false;
        if (!month.equals(dsDay.month)) return false;
        return getDate().equals(dsDay.getDate());

    }

    @Override
    public int hashCode() {
        int result = getDate().hashCode();
        result = result + month.hashCode();
        result = result + year.hashCode();
        return result;
    }
}
