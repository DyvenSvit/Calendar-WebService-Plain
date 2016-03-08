package org.dyvensvit.calendar;

import java.time.Month;
import java.time.Year;

public interface DscalendarDao {

    DsMonth getMonth(Month month);

    DsMonth getMonth(Year year, Month month);
}
