package org.dyvensvit.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DsCalendarApplication {

	@Autowired
	private DsCalendarService dscalendarService;

	public static void main(String[] args) {
		SpringApplication.run(DsCalendarApplication.class, args);
	}

	@RequestMapping(value = "/{year}/{month}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	DsMonth getMonth(@PathVariable final Integer year, @PathVariable final Integer month) {
		return dscalendarService.getMonth(Year.of(year), Month.of(month));
	}
	
	@RequestMapping(value = "/{year}/{month}/{day}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	DsDayFull getDay(@PathVariable final Integer year, @PathVariable final Integer month, @PathVariable final Integer day) {
		return dscalendarService.getDay(Year.of(year), Month.of(month), day);
	}
}
