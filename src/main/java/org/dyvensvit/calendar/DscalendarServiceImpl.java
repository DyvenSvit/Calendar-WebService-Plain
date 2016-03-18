package org.dyvensvit.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.Year;

@Component
public class DsCalendarServiceImpl implements DsCalendarService {

    @Autowired
    private DsCalendarDao dscalendarDao;

    @Override
    public DsMonth getMonth(Month month) {
        return dscalendarDao.getMonth(month);
    }

    @Override
    @Cacheable(value = "months")
    public DsMonth getMonth(Year year, Month month) {
        return dscalendarDao.getMonth(year, month);
    }
	
	@Override
    @Cacheable(value = "days")
    public DsDayFull getDay(Year year, Month month, Integer day) {
        return dscalendarDao.getDay(year, month, day);
    }
}
