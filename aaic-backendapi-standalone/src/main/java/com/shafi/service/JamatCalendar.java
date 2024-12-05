package com.shafi.service;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.TreeMap;

import org.shredzone.commons.suncalc.SunTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shafi.util.PrayTime;
import com.shafi.util.Response;

@Component
public class JamatCalendar {


	//	Date date = new Date();
	LocalDateTime now = LocalDateTime.now();

	DayOfWeek dow = now.getDayOfWeek();
	String today = dow.toString();


	@Autowired
	Response response;

	PrayTime prayers = new PrayTime();

	//=================DAYLIGH SAVINGS ==============================
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	int calMonth = localDate.getMonthValue();
	int calDate = localDate.getDayOfMonth();
	DayOfWeek dayOfWeek = localDate.getDayOfWeek();
	int dayName = dayOfWeek.getValue();
	int weekVal = dayOfWeek.getValue();
	String weekName = dayOfWeek.name();

	//============================DAYLIGHT SAVINGS Begin ============================
	int currYear =  localDate.getYear();
	int marchDaylightSavingDay = getMarchDayLightSavings();
	int novDaylightSavingDay = getNovDayLightSavings();
	LocalTime targetTime = LocalTime.of(2, 0);

	//Get Daylight saving for March 
	public int getMarchDayLightSavings() {
		if(calMonth == 3) {
			for(int i=1; i<17; i++) {
				LocalDate marchMonth = LocalDate.of(currYear, 3, i);
				int month = marchMonth.getMonthValue();
				DayOfWeek dayWeek =  marchMonth.getDayOfWeek();
				String dow = dayWeek.name();

				if(dow.equalsIgnoreCase("SUNDAY") && month == 3 && (i >= 7 && i <= 16)) {
					marchDaylightSavingDay = i;
				}
			}
		}
		return marchDaylightSavingDay;
	}

	//	//Get Daylight saving for November 
	public int getNovDayLightSavings() {
		if(calMonth == 11) {
			for(int i=1; i<8; i++) {
				LocalDate novMonth = LocalDate.of(currYear, 11, i);
				int month = novMonth.getMonthValue();
				DayOfWeek dayWeek =  novMonth.getDayOfWeek();
				String novDOW = dayWeek.name();

				if(novDOW.equalsIgnoreCase("SUNDAY") && month == 11 && (i >= 1 && i <= 8)) {
					novDaylightSavingDay = i;
				}
			}
		}
		return novDaylightSavingDay;

	}

	//============================DAYLIGHT SAVINGS ENDS ============================



	//Fajr jamat
	public String yearlyFajrJamatTimings(int intMonth, int intDate) {


		if((intMonth == 1) && ((intDate >= 1) && (intDate <= 31))) {

			response.setFajrJamat("6:30 AM");
		}
		if((intMonth == 2) && ((intDate >= 1) && (intDate <= 9))) {

			response.setFajrJamat("6:15 AM");
		}
		if((intMonth == 2) && ((intDate >= 10) && (intDate <= 19))) {

			response.setFajrJamat("6:00 AM");
		}
		if((intMonth == 2) && ((intDate >= 20) && ( (intDate <= 28) || (intDate <= 29)) )) {

			response.setFajrJamat("5:45 AM");
		}

		//=============Daylight savings November=================
		if((intMonth == 3) && (intDate < marchDaylightSavingDay) ) {
			response.setFajrJamat("5:30 AM");
		}if((intMonth == 3) && (intDate >= marchDaylightSavingDay) && (intDate <= 31) ) {
			response.setFajrJamat("6:15 AM");
		}

		//==============================================
		if((intMonth == 4) && ((intDate >= 1) && (intDate <= 6))) {

			response.setFajrJamat("6:15 AM");
		}
		if((intMonth == 4) && ((intDate >= 7) && (intDate <= 13))) {

			response.setFajrJamat("5:20 AM");
		}
		if((intMonth == 4) && ((intDate >= 14) && (intDate <= 30))) {

			response.setFajrJamat("5:05 AM");
		}
		if((intMonth == 5) && ((intDate >= 1) && (intDate <= 7))) {

			response.setFajrJamat("5:00 AM");
		}
		if((intMonth == 5) && ((intDate >= 8) && (intDate <= 18))) {

			response.setFajrJamat("4:45 AM");
		}
		if((intMonth == 5) && ((intDate >= 19) && (intDate <= 31))) {

			response.setFajrJamat("4:30 AM");
		}
		if((intMonth == 6) && ((intDate >= 1) && (intDate <= 30))) {

			response.setFajrJamat("4:30 AM");
		}
		if((intMonth == 7) && ((intDate >= 1) && (intDate <= 13))) {

			response.setFajrJamat("4:30 AM");
		}
		if((intMonth == 7) && ((intDate >= 14) && (intDate <= 31))) {

			response.setFajrJamat("4:45 AM");
		}
		if((intMonth == 8) && ((intDate >= 1) && (intDate <= 10))) {

			response.setFajrJamat("5:00 AM");
		}
		if((intMonth == 8) && ((intDate >= 11) && (intDate <= 22))) {

			response.setFajrJamat("5:15 AM");
		}
		if((intMonth == 8) && ((intDate >= 23) && (intDate <= 31))) {

			response.setFajrJamat("5:30 AM");
		}
		if((intMonth == 9) && ((intDate >= 1) && (intDate <= 14))) {

			response.setFajrJamat("5:45 AM");
		}
		if((intMonth == 9) && ((intDate >= 15) && (intDate <= 30))) {

			response.setFajrJamat("6:00 AM");
		}
		if((intMonth == 10) && ((intDate >= 1) && (intDate <= 13))) {

			response.setFajrJamat("6:00 AM");
		}
		if((intMonth == 10) && ((intDate >= 14) && (intDate <= 31))) {

			response.setFajrJamat("6:15 AM");
		}


		//====================NOVEMBER Daylight saving begins =======================================
		if((intMonth == 11) && ((intDate >= 01) && (intDate < novDaylightSavingDay))) {

			response.setFajrJamat("6:15 AM");
		}
		if((intMonth == 11) && ((intDate >= novDaylightSavingDay) && (intDate <= 13))) {

			response.setFajrJamat("5:45 AM");
		}
		//====================NOVEMBER Daylight saving end =======================================
		if((intMonth == 11) && ((intDate >= 14) && (intDate <= 30))) {

			response.setFajrJamat("6:00 AM");
		}
		if((intMonth == 12) && ((intDate >= 1) && (intDate <= 31))) {

			response.setFajrJamat("6:15 AM");
		}

		return response.getFajrJamat();

	}

	//Dhuhr jamat
	public String yearlyDhuhrJamatTimings() {

		if(today.equalsIgnoreCase("SUNDAY")) {

			response.setDhuhrJamat("1:00 PM");
		}
		else {
			response.setDhuhrJamat("1:30 PM");
		}

		return response.getDhuhrJamat();
	}


	//Asr jamat
	public String yearlyAsrJamatTimings(int intMonth, int intDate) {


		//====================================	
		if((intMonth == 1) && ((intDate >= 01) && (intDate <= 31))) {

			response.setAsrJamat("3:00 PM");
		}
		if((intMonth == 1) && ((intDate >= 01) && (intDate <= 9))) {

			response.setAsrJamat("3:45 PM");
		}
		if((intMonth == 2) && (intDate >= 10) && ((intDate <= 28) || (intDate <= 29))) {

			response.setAsrJamat("4:00 PM");
		}
		if((intMonth == 3) && ((intDate >= 01) && (intDate <= 31))) {


			if(intDate > marchDaylightSavingDay) {
				response.setAsrJamat("5:30 PM");
			}else {
				response.setAsrJamat("4:15 PM");
			}

		}
		if((intMonth == 4) && (intDate >= 1) && (intDate <= 30)) {

			response.setAsrJamat("5:45 PM");
		}
		if((intMonth == 5) && (intDate >= 1) && (intDate <= 31)) {

			response.setAsrJamat("6:30 PM");
		}
		if((intMonth == 6) && (intDate >= 1) && (intDate <= 1)) {

			response.setAsrJamat("6:30 PM");
		}
		if((intMonth == 6) && (intDate >= 2) && (intDate <= 15)) {

			response.setAsrJamat("5:45 PM");
		}
		if((intMonth == 6) && (intDate >= 16) && (intDate <= 30)) {

			response.setAsrJamat("5:30 PM");
		}
		if((intMonth == 7) && (intDate >= 1) && (intDate <= 31)) {

			response.setAsrJamat("6:30 PM");
		}
		if((intMonth == 8) && (intDate >= 1) && (intDate <= 18)) {

			response.setAsrJamat("6:15 PM");
		}
		if((intMonth == 8) && (intDate >= 1) && (intDate <= 31)) {

			response.setAsrJamat("6:00 PM");
		}
		if((intMonth == 9) && (intDate >= 1) && (intDate <= 14)) {

			response.setAsrJamat("5:30 PM");
		}
		if((intMonth == 9) && (intDate >= 15) && (intDate <= 30)) {

			response.setAsrJamat("5:15 PM");
		}

		if((intMonth == 10) && ((intDate >= 01) && (intDate <= 13))) {

			response.setAsrJamat("5:00 PM");
		}

		if((intMonth == 10) && ((intDate >= 14) && (intDate <= 31))) {

			response.setAsrJamat("4:45 PM");
		}
		if((intMonth == 11) && ((intDate >= 01) && (intDate <= 30))) {


			//=============Daylight savings November begins=================
			if((intMonth == 11) && (intDate < novDaylightSavingDay)) {
				response.setAsrJamat("4:45 PM");
			}if((intMonth == 11) && (intDate >= novDaylightSavingDay) && (intDate <= 14)) {
				response.setAsrJamat("3:30 PM");
			}if((intMonth == 11) && (intDate >= 15) && (intDate <= 30)) {
				response.setAsrJamat("3:00 PM");
			}

			//==================Daylight savings November end=================	
			if(((intMonth >= 12) && ((intDate >= 1) && (intDate <= 29)))) {

				response.setAsrJamat("3:00 PM");
			}if(((intMonth >= 12) && ((intDate >= 30)) || ((intMonth <= 3) && (intDate <= 8)))) {

				response.setAsrJamat("3:15 PM");
			}
		}
		return response.getAsrJamat();

	}

	//Maghrib jamat
	public String yearlyMaghribJamatTimings(int intMonth, int intDate) {



		//===============================Maghrib calculation begins =====================================================
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone
		int currYear =  localDate.getYear();
		LocalDate setDate = LocalDate.of(currYear, intMonth, intDate);
		double latitude = 41.8781; // Example latitude
		double longitude = -87.6298; // Example longitude
		// Calculate the sunset time using SunCalc
		SunTimes sunTimes = SunTimes.compute()
				.on(setDate)
				.at(latitude, longitude)
				.timezone(zoneId)
				.execute();
		String sunRise = sunTimes.getRise().toString();
		String noon = sunTimes.getNoon().toString();
		String sunSet = sunTimes.getSet().toString();
		String [] splitSunSet = sunSet.split("T");
		String [] splitHyp = splitSunSet[1].split("-");
		String [] splitTime = splitHyp[0].split(":");
		String hour = splitTime[0].toString();
		String min = splitTime[1].toString();
		int  hourToRegular =  Integer.valueOf(hour) - 12;
		Integer minNum = Integer.valueOf(min)+1;
		String maghribTime = hourToRegular+ ":" + minNum +" " + "PM";
		response.setMaghribJamat(maghribTime);
		//===================================old impl begins========================================
		// double latitude = -37.823689;
		//		double latitude = 41.881832;
		//		double longitude = -87.623177;
		//		double timezone = -4.9;
		//		// Test Prayer times here
		//		PrayTime prayers = new PrayTime();
		//		//==========================added===============
		//		//https://www.timeanddate.com/sun/usa/chicago
		//		int[] offsets = {0,-6,-7,0,-7,-23,0}; 
		//		// should be 7 in order
		//		// of Fajr, Sunrise,
		//		// Dhuhr, Asr, Sunset,
		//		// Maghrib, Isha
		//		//============added end==============================
		//		prayers.setTimeFormat(prayers.Time12);
		//		prayers.setCalcMethod(prayers.Jafari);
		//		// prayers.setAsrJuristic(prayers.Shafii);
		//		prayers.setAsrJuristic(prayers.Hanafi);
		//		prayers.setAdjustHighLats(prayers.AngleBased);
		//		//  int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
		//		prayers.tune(offsets);
		//		intMonth = intMonth-1;
		//		Calendar cal = Calendar.getInstance();
		//		int year = Calendar.getInstance().get(Calendar.YEAR);
		//		cal.set(year, intMonth, intDate);
		//		Map<String, String> resultMap = new TreeMap<>();
		//
		//		ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
		//				latitude, longitude, timezone);
		//
		//
		//		response.setMaghribJamat(prayerTimes.get(5));
		//===================================old impl ends========================================	



		return response.getMaghribJamat();
	}
	//===============================Maghrib calculation Ends =====================================================
	//Isha jamat
	public String yearlyIshaJamatTimings(int intMonth, int intDate) {

		if((intMonth == 1) && ((intDate >= 01) && (intDate <= 31))) {

			response.setIshaJamat("7:30 PM");
		}
		if((intMonth == 2) && ((intDate <= 28) || (intDate <= 29)) ) {

			response.setIshaJamat("7:30 PM");
		}
		if((intMonth == 3) && ((intDate >= 01) && (intDate <= 11))) {

			response.setIshaJamat("7:30 PM");
		}
		if((intMonth == 3) && ((intDate >= 12) && (intDate <= 22))) {

			response.setIshaJamat("8:30 PM");
		}
		if((intMonth == 3) && ((intDate >= 23) && (intDate <= 31))) {

			response.setIshaJamat("8:45 PM");
		}
		if((intMonth == 4) && ((intDate >= 1) && (intDate <= 11))) {

			response.setIshaJamat("9:00 PM");
		}
		if((intMonth == 4) && ((intDate >= 12) && (intDate <= 30))) {

			response.setIshaJamat("9:15 PM");
		}
		if((intMonth == 5) && ((intDate >= 1) && (intDate <= 18))) {

			response.setIshaJamat("9:45 PM");
		}
		if((intMonth == 5) && ((intDate >= 19) && (intDate <= 31))) {

			response.setIshaJamat("10:00 PM");
		}
		if((intMonth == 6) && ((intDate >= 1) && (intDate <= 30))) {

			response.setIshaJamat("10:15 PM");
		}
		if((intMonth == 7) && ((intDate >= 1) && (intDate <= 20))) {

			response.setIshaJamat("10:15 PM");
		}
		if((intMonth == 7) && ((intDate >= 21) && (intDate <= 31))) {

			response.setIshaJamat("10:00 PM");
		}
		if((intMonth == 8) && ((intDate >= 1) && (intDate <= 15))) {

			response.setIshaJamat("9:45 PM");
		}
		if((intMonth == 8) && ((intDate >= 16) && (intDate <= 31))) {

			response.setIshaJamat("9:30 PM");
		}
		if((intMonth == 9) && ((intDate >= 1) && (intDate <= 12))) {

			response.setIshaJamat("9:00 PM");
		}
		if((intMonth == 9) && ((intDate >= 13) && (intDate <= 21))) {

			response.setIshaJamat("8:45 PM");
		}
		if((intMonth == 9) && ((intDate >= 22) && (intDate <= 30))) {

			response.setIshaJamat("8:30 PM");
		}
		if((intMonth == 10) && ((intDate >= 1) && (intDate <= 13))) {

			response.setIshaJamat("8:00 PM");
		}
		if((intMonth == 10) && ((intDate >= 14) && (intDate <= 31))) {

			response.setIshaJamat("7:45 PM");
			//			//====================NOVEMBER Daylight saving begins =======================================
			if((intMonth == 11) && ((intDate >= 01) && (intDate < novDaylightSavingDay))) {

				response.setIshaJamat("7:45 PM");
			}
			if((intMonth == 11) && ((intDate >= novDaylightSavingDay) && (intDate <= 13))) {

				response.setIshaJamat("7:30 PM");
			}
			//			//====================NOVEMBER Daylight saving end =======================================
		}
		if((intMonth == 11) && ((intDate >= 1) && (intDate <= 30))) {

			response.setIshaJamat("7:30 PM");
		}
		if((intMonth == 12) && ((intDate >= 1) && (intDate <= 31))) {

			response.setIshaJamat("7:30 PM");
		}



		if( (intMonth >= 11) &&  ((intDate <= 1) && (intDate >=5)) ) {

			response.setIshaJamat("7:30 PM");
		}



		return response.getIshaJamat();

	}


	//Next prayer time
	//	public String nextPrayer() {
	//
	//		double latitude = 41.881832;
	//		double longitude = -87.623177;
	//		double timezone = -4.9;
	//
	//		int[] offsets = {0,-6,-7,0,-7,-24,0};
	//		prayers.setTimeFormat(prayers.Time12);
	//		prayers.setCalcMethod(prayers.Jafari);
	//		// prayers.setAsrJuristic(prayers.Shafii);
	//		prayers.setAsrJuristic(prayers.Hanafi);
	//		prayers.setAdjustHighLats(prayers.AngleBased);
	//		//  int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
	//		prayers.tune(offsets);
	//
	//		Date now = new Date();
	//		Calendar cal = Calendar.getInstance();
	//		cal.setTime(now);
	//
	//		ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
	//				latitude, longitude, timezone);
	//
	//		//Next Fajr
	//		int intMonth; int intDate;
	//		String fajr =  yearlyFajrJamatTimings(intMonth, intDate); //prayerTimes.get(0); //05:45 am
	//		String [] splitFajr = fajr.toString().split(" ");
	//		String ampmfajr = splitFajr[1].toString();
	//		String [] splitColonFajr = splitFajr[0].toString().split(":");
	//		String splittedHourFajr = splitColonFajr[0].toString();
	//		String splittedMinFajr = splitColonFajr[1].toString();
	//		Integer intFajrHour =  Integer.valueOf(splittedHourFajr);
	//		Integer intFajrMin =  Integer.valueOf(splittedMinFajr);
	//
	//
	//		if(intFajrMin == 0) {
	//			intFajrMin = 60;
	//		}


	//		//Next Dhuhr jamat
	//		//int militaryHour = intHour+12;
	//		if(today.equals("SUNDAY")) {
	//
	//			if( (intHour > intFajrHour) && (intHour <= 12) && (intMinutes <= 59)) {
	//
	//				response.setNextJamat(response.getDhuhrJamat());
	//
	//			}
	//		}
	//		if(!(today.equals("SUNDAY"))) {
	//			if( (intHour >= intFajrHour) && (intHour <= 12) && (intMinutes <= 59)) {
	//
	//				response.setNextJamat(yearlyDhuhrJamatTimings());
	//
	//			}
	//			return response.getNextJamat();
	//		}
	//
	//		String asr = yearlyAsrJamatTimings();		//prayerTimes.get(0); //05:45 am
	//		String [] splitAsr = asr.toString().split(" ");
	//		String [] splitColonAsr = splitAsr[0].toString().split(":");
	//		String splittedHourAsr = splitColonAsr[0];
	//		String splittedMinAsr = splitColonAsr[1].toString();
	//		Integer intAsrHour =  Integer.valueOf(splittedHourAsr);
	//		Integer intAsrrMin =  Integer.valueOf(splittedMinAsr);
	//		Integer militaryAsr = Integer.valueOf(splittedHourAsr);
	//		int convertedMilitaryAsr = militaryAsr + 12;
	//		//Integer militaryAsrMins = Integer.valueOf(splittedMinAsr);
	//
	//		if(intAsrrMin == 0) {
	//			intAsrrMin = 60;
	//		}
	//
	//		//Next ASR jamat
	//		if(today.equals("SUNDAY")) {
	//			if( (intHour >= 13) && (intHour <= convertedMilitaryAsr)) {
	//
	//				response.setNextJamat(yearlyAsrJamatTimings());
	//
	//			}
	//		}
	//		if(!(today.equals("SUNDAY"))) {
	//			if( (intHour > 13) && (intHour <= convertedMilitaryAsr) ) {
	//
	//				response.setNextJamat(response.getAsrJamat());
	//				return response.getNextJamat();
	//
	//			}
	//
	//		}
	//
	//		//Next Maghrib
	//		String maghrib = prayerTimes.get(5); //05:45 am
	//		String [] splitMaghrib = maghrib.toString().split(" ");
	//		String [] splitColonMaghrib = splitMaghrib[0].toString().split(":");
	//		String splittedHourMaghrib = splitColonMaghrib[0].toString();
	//		String splittedMinMaghrib = splitColonMaghrib[1].toString();
	//
	//		Integer convertedMilitaryMaghrib =  Integer.valueOf(splittedHourMaghrib);
	//		Integer intMaghribMin =  Integer.valueOf(splittedMinMaghrib);
	//		int militaryMaghrib = convertedMilitaryMaghrib+12;
	//
	//		if( (intHour > convertedMilitaryAsr ) && (intHour <= militaryMaghrib)) {
	//
	//			response.setNextJamat(yearlyMaghribJamatTimings());
	//			return response.getNextJamat();
	//		}
	//
	//
	//		//Next Isha
	//		String isha = yearlyIshaJamatTimings();
	//		String [] splitIsha = isha.toString().split(" ");
	//		String [] splitColonIsha = splitIsha[0].toString().split(":");
	//		String splittedHourIsha = splitColonIsha[0].toString();
	//		String splittedMinIsha = splitColonIsha[1].toString();
	//
	//		Integer convertedMilitaryIsha =  Integer.valueOf(splittedHourIsha);
	//		Integer intIshaMin =  Integer.valueOf(splittedMinIsha);
	//		int militaryIsha = convertedMilitaryIsha+12;
	//
	//		if( (intHour >= militaryMaghrib )  && (intHour <= militaryIsha)) {
	//
	//			response.setNextJamat(yearlyIshaJamatTimings());
	//			return response.getNextJamat();
	//		}
	//
	//		//Next Fajr
	//		if( ((intHour <= 23)&&(intHour > militaryIsha ))
	//				|| ((intHour <= intFajrHour)) ) {
	//
	//			response.setNextJamat(yearlyFajrJamatTimings());
	//
	//		}
	//
	//
	//		return response.getNextJamat();
	//
	//
	//	}
	//
	//	//===============Yearly Jamat timing Begins ==========================================================
	//	public String yearlyPrayerTimings() {
	//		if(intMonth == 10 && (intDate >= 1 && intDate < 14) ) {
	//
	//			response.setJamatYearlyList(intMonth+"/"+intDate+ " "+"Fajr 6:15 AM" + "|" + "Asr 4:45 PM" + "|" + "Isha 7:45 PM");
	//
	//		}
	//		if( (intMonth == 10 && intDate >= 14) || (intMonth == 11 && intDate <= 1) ) {
	//
	//			response.setJamatYearlyList(intMonth+1+"/"+1+ " "+"Fajr 6:30 AM - Isha 7:30 PM");
	//
	//		}
	//		//above is done
	//		if(intMonth == 11 && (intDate >= 1 && intDate <= 11) ) {
	//
	//			response.setJamatYearlyList(intMonth+"/"+12+ " "+"Fajr 5:45 AM");
	//
	//		}
	//		if(intMonth == 11 && (intDate >=12 && intDate <= 23) ) {
	//
	//			response.setJamatYearlyList(intMonth+"/"+24+ " "+"Fajr 6:00 AM");
	//
	//		}
	//		return response.getJamatYearlyList();
	//
	//	}
	//===============Yearly Jamat timing Ends ==========================================================

	//}
}
