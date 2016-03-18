package org.dyvensvit.calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DsCalendarDaoImpl implements DsCalendarDao {
    private static final Logger LOG = Logger.getLogger(DsCalendarDaoImpl.class);

    public static final String DS_CALENDAR_SOURCE_HOME = "DS_CALENDAR_SOURCE_HOME";
    @Value("${dscalendar.alternative.source.home}")
    private String dsCalendarAlternativeSource;

    @Override
    public DsMonth getMonth(Month month) {
        return getMonth(Year.now(), month);
    }

    @Override
    public DsMonth getMonth(Year year, Month month) {
        Path monthDirectoryPath = getDirectoryForMonth(year, month);
        List<String> daysIndexes = getDaysForMonthAndYear(month, year);
        final List<String> infoLines = getLinesFromCfile(monthDirectoryPath);
        List<DsDayTiny> days = daysIndexes.stream().map(dayIndex -> {
            DsDayTiny dsDay = getDay(monthDirectoryPath, dayIndex);
            String infoLine  = infoLines.get(Integer.valueOf(dayIndex)-1);
            dsDay.setInfo(infoLine);
            return dsDay;
        }).collect(Collectors.toList());
        DsMonth dsMonth = new DsMonth();
        dsMonth.setDays(days);
        dsMonth.setYear(String.valueOf(year.getValue()));
        dsMonth.setMonth(String.format("%02d", month.getValue()));
        return dsMonth;
    }
	
	@Override
    public DsDayFull getDay(Year year, Month month, Integer day) {
        Path monthDirectoryPath = getDirectoryForMonth(year, month);
		String dayIndex = String.format("%02d", day);
		DsDayFull dsDay = new DsDayFull();
        dsDay.setYear(String.valueOf(year.getValue()));
        dsDay.setMonth(String.format("%02d", month.getValue()));
		dsDay.setDate(dayIndex);
        final List<String> infoLines = getLinesFromCfile(monthDirectoryPath);
        String infoLine  = infoLines.get(day-1);
        dsDay.setInfo(infoLine);
        dsDay.setLiturgy(getStringContentOfFile(getPathToFileByPrefix("u", monthDirectoryPath, dayIndex, null)));
        dsDay.setMorning(getStringContentOfFile(getPathToFileByPrefix("t", monthDirectoryPath, dayIndex, "u")));
        dsDay.setNight(getStringContentOfFile(getPathToFileByPrefix("t", monthDirectoryPath, dayIndex, "v")));
        dsDay.setHours(getStringContentOfFile(getPathToFileByPrefix("t", monthDirectoryPath, dayIndex, "c")));
        dsDay.setReadings(getStringContentOfFile(getPathToFileByPrefix("b", monthDirectoryPath, dayIndex, null)));
        dsDay.setSaints(getStringContentOfFile(getPathToFileByPrefix("b", monthDirectoryPath, dayIndex, null)));
        dsDay.setQuotes(getStringContentOfFile(getPathToFileByPrefix("p", monthDirectoryPath, dayIndex, null)));
		
        return dsDay;
    }

    private List<String> getLinesFromCfile(final Path monthDirectoryPath) {
        try {
            return Files.lines(monthDirectoryPath.resolve("c.txt")).collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("Cant read c.txt file");
            return Collections.emptyList();
        }
    }

    private DsDayTiny getDay(final Path monthDirectoryPath, final String dayIndex) {
        DsDayTiny dsDay = new DsDayTiny();
        dsDay.setDate(dayIndex);
        dsDay.setInfo("INFO IS EMPTY");
        return dsDay;
    }

    private Path getPathToFileByPrefix(final String prefix, final Path monthDirPath, final String dayIndex, final String sufix) {
        final StringBuilder filename = new StringBuilder(prefix+dayIndex);
        if (sufix != null)
            filename.append(sufix);
        filename.append(".html");
        return monthDirPath.resolve(filename.toString());
    }

    private String getStringContentOfFile(final Path path) {
        try {
            return new String(Files.readAllBytes(path), "UTF-8");
        } catch (IOException e) {
            LOG.warn("No such file: " + path.toString());
            return null;
        }
    }

    final List<String> getDaysForMonthAndYear(final Month month, final Year year) {
        int monthLength = month.length(year.isLeap());
        final List<String> daysInMonth = new ArrayList<>();
        for (int i=1; i <= monthLength; i++) {
            if (i < 10) {
                daysInMonth.add("0"+i);
            } else {
                daysInMonth.add(String.valueOf(i));
            }
        }
        return daysInMonth;
    }

    private Path getDirectoryForMonth(final Year year, final Month month) {
        Path root = getRootDirectoryPath();
        Path yearDirectoryPath = root.resolve(String.valueOf(year.getValue()));
        if (!yearDirectoryPath.toFile().exists()) {
            throw new IllegalArgumentException("No home directory for year: " + year.getValue());
        }
        String monthVal = resolveMonthDirectoryName(month);
        Path monthDirectoryPath = yearDirectoryPath.resolve(monthVal);
        if (!monthDirectoryPath.toFile().exists()) {
            throw new IllegalArgumentException("No home directory for month: " + month.name());
        }
        return yearDirectoryPath.resolve(monthVal);
    }

    private String resolveMonthDirectoryName(final Month month) {
        String monthVal;
        switch (month) {
            case JANUARY:
                monthVal = "01";
                break;
            case FEBRUARY:
                monthVal = "02";
                break;
            case MARCH:
                monthVal = "03";
                break;
            case APRIL:
                monthVal = "04";
                break;
            case MAY:
                monthVal = "05";
                break;
            case JUNE:
                monthVal = "06";
                break;
            case JULY:
                monthVal = "07";
                break;
            case AUGUST:
                monthVal = "08";
                break;
            case SEPTEMBER:
                monthVal = "09";
                break;
            case OCTOBER:
                monthVal = "10";
                break;
            case NOVEMBER:
                monthVal = "11";
                break;
            case DECEMBER:
                monthVal = "12";
                break;
            default:
                throw new IllegalArgumentException("Month is very strange");
        }
        return monthVal;
    }

    private Path getRootDirectoryPath() {
        final String configurationPath = System.getProperty(DS_CALENDAR_SOURCE_HOME, dsCalendarAlternativeSource);
        if (StringUtils.isEmpty(configurationPath)) {
            throw new IllegalStateException("DS_CALENDAR_SOURCE_HOME is not set");
        }

        final Path rootPath = Paths.get(configurationPath);
        LOG.info("Root path: " + rootPath);
        if (!rootPath.toFile().exists()) {
            throw new IllegalArgumentException("No root path exists: " + configurationPath + "probably configuration " +
                    "of DS_CALENDAR_SOURCE_HOME points on directory that doesn't exist");
        }
        return rootPath;
    }
}
