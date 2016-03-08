package org.dyvensvit.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class DscalendarApplication {

	@Autowired
	private DscalendarService dscalendarService;

	public static void main(String[] args) {
		SpringApplication.run(DscalendarApplication.class, args);
	}

	@RequestMapping(value = "/{year}/{month}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	DsMonth getMonth(@PathVariable final Integer year, @PathVariable final Integer month) {
		return dscalendarService.getMonth(Year.of(year), Month.of(month));
	}
}
