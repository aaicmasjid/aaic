package com.shafi.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shafi.service.PrayerTimeServiceImpl;
import com.shafi.service.TimeChangeAlert;
import com.shafi.service.JamatCalendar;
import com.shafi.util.AlertResponse;
import com.shafi.util.Response;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class Aaiccontroller {

	private static final org.apache.logging.log4j.Logger Log = LogManager.getLogger();
	@Autowired
	PrayerTimeServiceImpl prayerTimeServiceImpl;
	//	@Autowired
	//	JamatTimingsServiceImpl jamatTimingsImpl;
	@Autowired
	JamatCalendar jamatCalendar;
	@Autowired
	Response response;
	@Autowired
	TimeChangeAlert timeChangeAlert;
	@Autowired
	AlertResponse alertResponse;


	@ApiOperation(value = "Get user name", notes = "takes user name and returns")
	@GetMapping("/prayertimes")
	public Response getDetails() {
		Log.info("Entered prayer time controller v3.");

		return prayerTimeServiceImpl.getPrayerTimes();
	}


	@GetMapping("/jt")
	public Response jamatTimings(@RequestParam int intMonth, @RequestParam int intDate) {
		Log.info("Entered jamatTimings method of Aaiccontroller class");

		jamatCalendar.yearlyFajrJamatTimings(intMonth, intDate);
		jamatCalendar.yearlyDhuhrJamatTimings();
		jamatCalendar.yearlyAsrJamatTimings(intMonth, intDate);
		jamatCalendar.yearlyMaghribJamatTimings(intMonth, intDate);
		jamatCalendar.yearlyIshaJamatTimings(intMonth, intDate);

		Log.info("User input  month: {} and date: {}" , intMonth, intDate);

		return response;
	}

	@GetMapping("/tca")
	public AlertResponse timeChangeAlert() {
		ArrayList<AlertResponse> ar = new ArrayList<>();
		Log.info("Entered timeChangeAlert method of Controller Class ");
		
		timeChangeAlert.fajrTimeChangeAlert();
		timeChangeAlert.asrTimeChangeAlert();
		
		

		return alertResponse;
	}



}
