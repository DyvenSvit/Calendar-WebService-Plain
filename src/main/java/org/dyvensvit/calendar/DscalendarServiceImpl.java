package org.dyvensvit.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.Year;

@Component
public class DscalendarServiceImpl implements DscalendarService {

    @Autowired
    private DscalendarDao dscalendarDao;

    @Override
    public DsMonth getMonth(Month month) {
        return dscalendarDao.getMonth(month);
    }

    @Override
    @Cacheable(value = "months")
    public DsMonth getMonth(Year year, Month month) {
        return dscalendarDao.getMonth(year, month);
    }
}
