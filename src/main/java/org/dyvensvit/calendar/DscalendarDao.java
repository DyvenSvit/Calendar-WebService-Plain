package org.dyvensvit.calendar;

import java.time.Month;
import java.time.Year;

public interface DsCalendarDao {

    DsMonth getMonth(Month month);

    DsMonth getMonth(Year year, Month month);
	
	DsDayFull getDay(Year year, Month month, Integer day);
}
