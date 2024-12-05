package com.shafi.service;



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

//--------------------- Copyright Block ----------------------
/* 

PrayTime.java: Prayer Times Calculator (ver 1.0)
Copyright (C) 2007-2010 PrayTimes.org

Java Code By: Hussain Ali Khan
Original JS Code By: Hamid Zarrabi-Zadeh

License: GNU LGPL v3.0

TERMS OF USE:
	Permission is granted to use this code, with or 
	without modification, in any website or application 
	provided that credit is given to the original work 
	with a link back to PrayTimes.org.

This program is distributed in the hope that it will 
be useful, but WITHOUT ANY WARRANTY. 

PLEASE DO NOT REMOVE THIS COPYRIGHT BLOCK.

 */


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.shredzone.commons.suncalc.SunTimes;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import com.shafi.util.PrayTime;
import com.shafi.util.Response;

@Service
@Component
public class PrayerTimeServiceImpl extends PrayerTimeService {

	@Autowired
	Response res;

	@Autowired
	JamatTimingsServiceImpl jtsImpl;




	//		@Autowired
	//		JamatTimingsServiceImpl jtsi;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PrayerTimeServiceImpl.class);

	//==========================Shafi daylight calc begin ==================================
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int currYear =  localDate.getYear();
	int marchDaylightSavingDay = getMarchDayLightSavings();
	int novDaylightSavingDay = getNovDayLightSavings();

	//Get Daylight saving for March 
	public int getMarchDayLightSavings() {
		for(int i=1; i<17; i++) {
			LocalDate marchMonth = LocalDate.of(currYear, 3, i);
			int month = marchMonth.getMonthValue();
			DayOfWeek dayWeek =  marchMonth.getDayOfWeek();
			String dow = dayWeek.name();

			if(dow.equalsIgnoreCase("SUNDAY") && month == 3 && (i >= 7 && i <= 16)) {
				marchDaylightSavingDay = i;
			}

		}
		return marchDaylightSavingDay;
	}

	//Get Daylight saving for November 
	public int getNovDayLightSavings() {
		for(int i=1; i<8; i++) {
			LocalDate novMonth = LocalDate.of(currYear, 11, i);
			int month = novMonth.getMonthValue();
			DayOfWeek dayWeek =  novMonth.getDayOfWeek();
			String novDOW = dayWeek.name();

			if(novDOW.equalsIgnoreCase("SUNDAY") && month == 11 && (i >= 1 && i <= 8)) {
				novDaylightSavingDay = i;
			}

		}
		return novDaylightSavingDay;

	}
	//==========================Shafi daylight cal end ==============================================

	//  public static void main(String[] args) {
	public Response getPrayerTimes(){  
		Map <String, String> prayerMap = new HashMap<>();
		// double latitude = -37.823689;
		double latitude = 41.881832;
		double longitude = -87.623177;
		double timezone = -4.9;
		// Test Prayer times here
		PrayTime prayers = new PrayTime();
		//==========================added===============
		//==========shafi added code for ASR begin=========
		LocalDate date1 = LocalDate.now();
		int currMonth = date1.getMonthValue();
		int currDate = date1.getDayOfMonth();

		int num = 0;
		if( ( (currMonth >= 11) && (currDate >= novDaylightSavingDay) )  ||  ((currMonth <= 3) && (currDate <= marchDaylightSavingDay)) ) {
			num = -65;
		}
		else {
			num = -5;
		}

		//===========shafi added code for ASR end ============
		//https://www.timeanddate.com/sun/usa/chicago
		int[] offsets = {0,-6,-7,num,-7,-23,0}; 
		// should be 7 in order
		// of Fajr, Sunrise,
		// Dhuhr, Asr, Sunset,
		// Maghrib, Isha
		//============added end==============================
		prayers.setTimeFormat(prayers.Time12);
		prayers.setCalcMethod(prayers.Jafari);
		// prayers.setAsrJuristic(prayers.Shafii);
		prayers.setAsrJuristic(prayers.Hanafi);
		prayers.setAdjustHighLats(prayers.AngleBased);
		//  int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
		prayers.tune(offsets);

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		Map<String, String> resultMap = new TreeMap<>();

		ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
				latitude, longitude, timezone);
		ArrayList<String> prayerNames = prayers.getTimeNames();
		List<Response> resList = new ArrayList<>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		List<String> prayerList = new ArrayList<>();
		List<String> prayerTimeList = new ArrayList<>();
		Response response = new Response();
		Date date = new Date();
		//		String cstCurrentDateTime = jtsi.formatDateToString(date, "dd MM yyyy hh:mm:ss a", "CST");
		//		
		//		String currHourMins = jtsi.formatDateToString(date, "hh:mm", "CST");


		for (int i = 0; i < prayerTimes.size(); i++) {

			//response.setId(i+1);
			//			response.setFajr(prayerTimes.get(0));
			response.setFajr(fajrTime());
			//response.setSunrise(prayerTimes.get(1));
			response.setSunrise(sunRise());
			//response.setDhuhr(prayerTimes.get(2) );
			response.setDhuhr(dhuhr());
			response.setAsr(prayerTimes.get(3));
			//response.setSunset(" " + prayerTimes.get(4));
			response.setSunset(sunSet());
			response.setMaghrib(sunSet());
			//response.setIsha(prayerTimes.get(6));
			response.setIsha(isha());
			response.setFajrJamat(jtsImpl.yearlyFajrJamatTimings());
			response.setDhuhrJamat(jtsImpl.yearlyDhuhrJamatTimings());
			response.setAsrJamat(jtsImpl.yearlyAsrJamatTimings());
			response.setMaghribJamat(jtsImpl.yearlyMaghribJamatTimings());
			response.setIshaJamat(jtsImpl.yearlyIshaJamatTimings());
			//response.setNextJamat(jtsImpl.nextPrayer());
			//response.setJamatYearlyList(jtsImpl.yearlyPrayerTimings());
			resList.add(response);

			//log.info((prayerNames.get(i) + " - " + resList.get(i)));
		}
		log.info("Daily salah start time: " + resList.toString());
		log.info("Entered prayer time PrayerTimeServiceImpl class v3.");
		return response;


	}

	public String fajrTime() {

		//Response res;
		double latitude = 41.8781; 
		double longitude = -87.6298; 
		Location location = new Location(latitude, longitude);
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/Chicago");

		Calendar date = Calendar.getInstance(); // Current date

		Calendar astronomicalDawn = calculator.getAstronomicalSunriseCalendarForDate(date);

		String astroDawn = astronomicalDawn.getTime().toString();
		String [] splitAstroDawn = astroDawn.split(" ");

		String astroTime = splitAstroDawn[3].toString();
		String[] parts = astroTime.split(":(?!.*:)"); 

		String fajrTime = parts[0].toString() + " "  + "AM";
		return fajrTime;
	}

	public String sunRise() {

		double latitude = 41.8781; 
		double longitude = -87.6298;
		ZoneOffset offset = ZoneOffset.ofHours(-6); // Example: GMT-6
		ZoneId zoneId = ZoneId.ofOffset("GMT", offset);

		LocalDate date = LocalDate.now();
		SunTimes sunTimes = SunTimes.compute()
				.on(date)
				.at(latitude, longitude)
				.timezone(zoneId)
				.execute();
		String sunRise = sunTimes.getRise().toString();
		String [] splitSunRise = sunRise.toString().split("T");
		String sunRiseTime = splitSunRise[1].toString(); //06:31:09-06:00[America/Chicago]
		String[] splitSunRiseTime = sunRiseTime.toString().split("-");
		String[] parts = splitSunRiseTime[0].split(":(?!.*:)"); 
		String sunRisePart = parts[0].toString() + " "  + "AM";


		return sunRisePart;
	}

	public String sunSet() {

		double latitude = 41.8781; 
		double longitude = -87.6298;
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate date = LocalDate.now();

		SunTimes sunTimes = SunTimes.compute()
				.on(date)
				.at(latitude, longitude)
				.timezone(zoneId)
				.execute();

		String sunSet = sunTimes.getSet().toString();
		String [] splitSunSet = sunSet.toString().split("T");
		String sunSetTime = splitSunSet[1].toString();
		String[] splitSunSetTime = sunSetTime.toString().split("-");
		String []parts = splitSunSetTime[0].split(":"); 
		String getHours = parts[0].toString();
		String getMin = parts[1];
		int intHour = Integer.parseInt(getHours); //converting string to integer
		int formatTime = (intHour - 12);
		String setTime =  String.valueOf(formatTime); //converting int to string
		String sunSetTimeFormatted = setTime + ":" + getMin + " " + "PM";



		return sunSetTimeFormatted;
	}

	public String dhuhr() {

		double latitude = 41.8781; 
		double longitude = -87.6298;
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate date = LocalDate.now();
		String amPm = "";

		SunTimes sunTimes = SunTimes.compute()
				.on(date)
				.at(latitude, longitude)
				.timezone(zoneId)
				.execute();

		String noon = sunTimes.getNoon().toString();
		String [] splitNoon = noon.toString().split("T");
		String noonTime = splitNoon[1].toString();
		String[] splitNoonTime = noonTime.toString().split("-");
		String[] parts = splitNoonTime[0].split(":(?!.*:)"); 

		String [] hour = parts[0].split(":");

		if(hour[0].equals("11")) {
			amPm = "AM";
		}else {
			amPm = "PM";
		}
		String noonPart = parts[0].toString() + " "  + amPm;


		return noonPart;
	}

	public String isha() {


		// Set your desired location and date
		double latitude = 41.8781; // New York City
		double longitude = -87.6298;
		Calendar calendar = Calendar.getInstance();
		int isha = 0;
		// calendar.set(2024, Calendar.NOVEMBER, 11);

		// Create a Location object
		Location location = new Location(latitude, longitude);

		// Create a SunriseSunsetCalculator object
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, TimeZone.getDefault());

		// Calculate nautical twilight times

		Calendar nauticalDusk = calculator.getNauticalSunsetCalendarForDate(calendar);
		String nDusk = nauticalDusk.getTime().toString();
		String [] duskSplit = nDusk.toString().split(":");
		String duskHour = duskSplit[0];
		String [] splitduskHour = duskHour.split(" ");
		String duskHourSplitted = splitduskHour[3];
		String duskMin = duskSplit[1];
		int ishaHour = Integer.parseInt(duskHourSplitted);
		int ishaHourFormatTime = ishaHour-12;
		int ishaMin = Integer.parseInt(duskMin);

		int updateIshaMin = ishaMin+18;

		if(updateIshaMin > 60) {
			ishaHourFormatTime++;

			updateIshaMin = 59;
		}

		String ishaPrayer = ishaHourFormatTime + ":" +  updateIshaMin + "PM";


		return ishaPrayer ;
	}

}
