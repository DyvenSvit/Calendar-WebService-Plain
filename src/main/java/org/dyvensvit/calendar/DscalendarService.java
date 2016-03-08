package org.dyvensvit.calendar;

import java.time.Month;
import java.time.Year;

public interface DscalendarService {
    DsMonth getMonth(Month month);

    DsMonth getMonth(Year year, Month month);
}
