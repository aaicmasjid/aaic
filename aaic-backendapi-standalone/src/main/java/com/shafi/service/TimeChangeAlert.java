package com.shafi.service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shafi.util.AlertResponse;
import com.shafi.util.Constants;
import com.shafi.util.PrayTime;
import com.shafi.util.Response;

@Component
public class TimeChangeAlert {

	@Autowired
	Response Response;
	@Autowired
	AlertResponse alertResponse;

	PrayTime prayers = new PrayTime();
	ArrayList <String> alertTimeChange = new ArrayList();

	Date date = new Date();


	String currDate = formatDateToString(date, "dd", "CST");
	String currMonth = formatDateToString(date, "MM", "CST");
	int intDate  = Integer.parseInt(currDate);
	int intMonth = Integer.parseInt(currMonth);


	//CREDIT to https://www.digitalocean.com/community/tutorials/how-to-convert-java-date-into-specific-timezone-format
	public static String formatDateToString(Date date, String format,
			String timeZone) {
		// null check
		if (date == null) return null;
		// create SimpleDateFormat object with input format
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		// return Date in required format with timezone as String
		return sdf.format(date);
	}



	//=================DAYLIGH SAVINGS ==============================

	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	int calMonth = localDate.getMonthValue();
	//	int calDate = localDate.getDayOfMonth();
	//	DayOfWeek dayOfWeek = localDate.getDayOfWeek();
	//	int dayName = dayOfWeek.getValue();
	//	int weekVal = dayOfWeek.getValue();
	//	String weekName = dayOfWeek.name();

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

	//yearly prospective jamat time change alert

	//Fajr jamat alert
	public String fajrTimeChangeAlert() {


		if(intMonth == 1) {

			//alertResponse.setFajrJamat("6:30 AM");
			alertResponse.setFajrJamat("FEB 1 @ 6:15 AM");
		}
		if((intMonth == 2) && ((intDate >= 1) && (intDate <= 9))) {

			alertResponse.setFajrJamat("FEB 10 @ 6:00 AM");
		}
		if((intMonth == 2) && ((intDate >= 10) && (intDate <= 19))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "FEB 20 @ ");
			alertResponse.setFajrJamat("FEB 20 @ 5:45 AM");

		}
		if((intMonth == 2) && (intDate >= 20)) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "MARCH 1 @ ");
			alertResponse.setFajrJamat("MARCH 1 @ 5:30 AM");
		}

		//=============Daylight savings November=================
		if((intMonth == 3) && (intDate < marchDaylightSavingDay) ) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + marchDaylightSavingDay + " @ ");
			alertResponse.setFajrJamat(marchDaylightSavingDay + " 6:15 AM");
		}if((intMonth == 3) && (intDate >= marchDaylightSavingDay) ) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + " APR 1 @ ");
			alertResponse.setFajrJamat("APR 1 @ 5:45 AM");
		}

		//==============================================
		if((intMonth == 4) && ((intDate >= 1) && (intDate <=8))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "APR 9 @ ");
			alertResponse.setFajrJamat("APR 9 @ 5:20 AM");
		}
		if((intMonth == 4) && ((intDate >= 9) && (intDate <= 22))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "APR 23 @ ");
			alertResponse.setFajrJamat("APR 23 @ 5:05 AM");
		}
		if((intMonth == 4) && ((intDate >= 23) && (intDate <= 30))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "MAY 1 @ ");
			alertResponse.setFajrJamat("MAY 1 @ 5:00 AM");
		}
		if((intMonth == 5) && ((intDate >= 1) && (intDate <= 7))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "MAY 8 @ ");
			alertResponse.setFajrJamat("MAY 8 @ 4:45 AM");
		}
		if((intMonth == 5) && ((intDate >= 8) && (intDate <= 18))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "MAY 19 @ ");
			alertResponse.setFajrJamat("MAY 19 @ 4:30 AM");
		}
		if(( (intMonth == 5) && (intDate >= 19)) || (intMonth == 6) || ( (intMonth == 7) && (intDate <= 13))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "JULY 14 @ ");
			alertResponse.setFajrJamat("JULY 14 @ 4:45 AM");
		}

		if((intMonth == 7) && ((intDate >= 14) && (intDate <= 31))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "AUG 1 @ ");
			alertResponse.setFajrJamat("AUG 1 @ 5:00 AM");
		}
		if((intMonth == 8) && ((intDate >= 1) && (intDate <= 10))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "AUG 11 @ ");
			alertResponse.setFajrJamat("AUG 11 @ 5:15 AM");
		}
		if((intMonth == 8) && ((intDate >= 11) && (intDate <= 22))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "AUG 23 @ ");
			alertResponse.setFajrJamat("AUG 23 @ 5:30 AM");
		}
		if((intMonth == 8) && ((intDate >= 23) && (intDate <= 31))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "SEP 1 @ ");
			alertResponse.setFajrJamat("SEP 1 @ 5:45 AM");
		}
		if((intMonth == 9) && ((intDate >= 1) && (intDate <= 11))) {

			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "OCT 1 @ ");
			alertResponse.setFajrJamat("OCT 1 @ 6:00 AM");
		}
		if((intMonth == 10) && (intDate >= 1) && (intDate == 11) ) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "OCT 11 @ ");
			alertResponse.setFajrJamat("OCT 11 @ 6:15 AM");
		}
		if( (intMonth == 10) && (intDate >= 11) ) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "NOV 1 @ ");
			alertResponse.setFajrJamat("NOV 1 @ 6:30 AM");
		}


		//====================NOVEMBER Daylight saving begins =======================================
		if((intMonth == 11) && ((intDate >= 01) && (intDate < novDaylightSavingDay))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + novDaylightSavingDay + " @ ");
			alertResponse.setFajrJamat(novDaylightSavingDay + "5:45 AM");
		}
		if((intMonth == 11) && ((intDate >= novDaylightSavingDay) && (intDate <= 17))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "NOV 18 @ ");
			alertResponse.setFajrJamat("NOV 18 @ 6:00 AM");
		}
		//====================NOVEMBER Daylight saving end =======================================
		if((intMonth == 11) && ((intDate >= 18) && (intDate <= 30))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "DEC 1 @  ");
			alertResponse.setFajrJamat("DEC 1 @ 6:15 AM");
		}
		if((intMonth == 12) && ((intDate >= 1) && (intDate <= 31))) {
			alertResponse.setFajrMessage(Constants.FAJR_ALERT_MSG + " " + "JAN 1 @ ");
			alertResponse.setFajrJamat("JAN 1 @ 6:30 AM");
		}

		return alertResponse.getFajrMessage() + alertResponse.getFajrJamat();

	}


	//Asr jamat time change
	public String asrTimeChangeAlert() {


		if((intMonth == 1) && ((intDate >= 1) && (intDate <= 31))) {

			//alertResponse.setFajrJamat("6:30 AM");
			alertResponse.setFajrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @ ");
			alertResponse.setFajrJamat("FEB 1 @ 6:15 AM");
		}
		if((intMonth == 2) && ((intDate >= 1) && (intDate <= 9))) {

			alertResponse.setAsrMessage(Constants.FAJR_ALERT_MSG + " " + "FEB 10 @ ");
			alertResponse.setAsrJamat("FEB 10 @ 6:00 AM");
		}
		if((intMonth == 2) && ((intDate >= 10) && (intDate <= 19))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 20 @ ");
			alertResponse.setAsrJamat("FEB 20 @ 5:45 AM");

		}
		if((intMonth == 2) && (intDate >= 20)) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "MARCH 1 @ ");
			alertResponse.setAsrJamat("MARCH 1 @ 5:30 AM");
		}

		//=============Daylight savings November=================
		if((intMonth == 3) && (intDate < marchDaylightSavingDay) ) {
			alertResponse.setAsrMessage(Constants.FAJR_ALERT_MSG + " " + marchDaylightSavingDay + " @ ");
			alertResponse.setAsrJamat(marchDaylightSavingDay + "6:15 AM");
		}if((intMonth == 3) && (intDate >= marchDaylightSavingDay) ) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + " APR 1 @ ");
			alertResponse.setAsrJamat("APR 1 @ 5:45 AM");
		}

		//==============================================
		if((intMonth == 4) && ((intDate >= 1) && (intDate <=8))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "APR 9 @ ");
			alertResponse.setAsrJamat("APR 9 @ 5:20 AM");
		}
		if((intMonth == 4) && ((intDate >= 9) && (intDate <= 22))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "APR 23 @ ");
			alertResponse.setFajrJamat("APR 23 @ 5:05 AM");
		}
		if((intMonth == 4) && ((intDate >= 23) && (intDate <= 30))) {

			alertResponse.setAsrMessage(Constants.FAJR_ALERT_MSG + " " + "MAY 1 @ ");
			alertResponse.setAsrJamat("MAY 1 @ 5:00 AM");
		}
		if((intMonth == 5) && ((intDate >= 1) && (intDate <= 7))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "MAY 8 @ ");
			alertResponse.setAsrJamat("MAY 8 @ 4:45 AM");
		}
		if((intMonth == 5) && ((intDate >= 8) && (intDate <= 18))) {
			alertResponse.setFajrMessage(Constants.ASR_ALERT_MSG + " " + "MAY 19 @ ");
			alertResponse.setAsrJamat("MAY 19 @ 4:30 AM");
		}
		if(( (intMonth == 5) && (intDate >= 19)) || (intMonth == 6) || ( (intMonth == 7) && (intDate <= 13))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "JULY 14 @ ");
			alertResponse.setAsrJamat("JULY 14 @ 4:45 AM");
		}

		if((intMonth == 7) && ((intDate >= 14) && (intDate <= 31))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 1 @ ");
			alertResponse.setAsrJamat("AUG 1 @ 5:00 AM");
		}
		if((intMonth == 8) && ((intDate >= 1) && (intDate <= 10))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 11 @ ");
			alertResponse.setAsrJamat("AUG 11 @ 5:15 AM");
		}
		if((intMonth == 8) && ((intDate >= 11) && (intDate <= 22))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 23 @ ");
			alertResponse.setAsrJamat("AUG 23 @ 5:30 AM");
		}
		if((intMonth == 8) && ((intDate >= 23) && (intDate <= 31))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "SEP 1 @ ");
			alertResponse.setAsrJamat("SEP 1 @ 5:45 AM");
		}
		if((intMonth == 9) && ((intDate >= 1) && (intDate <= 11))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "OCT 1 @ ");
			alertResponse.setAsrJamat("OCT 1 @ 6:00 AM");
		}
		if((intMonth == 10) && (intDate >= 1) && (intDate == 11) ) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "OCT 11 @ ");
			alertResponse.setAsrJamat("OCT 11 @ 6:15 AM");
		}
		if( (intMonth == 10) && (intDate >= 11) ) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "NOV 1 @ ");
			alertResponse.setAsrJamat("NOV 1 @ 6:30 AM");
		}


		//====================NOVEMBER Daylight saving begins =======================================
		if((intMonth == 11) && ((intDate >= 01) && (intDate < novDaylightSavingDay))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + novDaylightSavingDay + " @ ");
			alertResponse.setAsrJamat(novDaylightSavingDay + " @ 3:30 PM");
		}
		if((intMonth == 11) && ((intDate >= novDaylightSavingDay) && (intDate <= 14))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "NOV 15 @ ");
			alertResponse.setAsrJamat("NOV 15 @ 3:00 PM");
		}
		//====================NOVEMBER Daylight saving end =======================================
		if((intMonth == 11) && (intDate >= 15)) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @ ");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 PM");
		}
		if((intMonth == 12)) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @  ");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 PM");
		}
		if(intMonth == 1) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 AM");
		}

		return alertResponse.getAsrMessage() + alertResponse.getAsrJamat();

	}


	//Isha jamat time change
	public String ishaTimeChangeAlert() {


		if( intMonth == 1) {

			//alertResponse.setFajrJamat("6:30 AM");
			alertResponse.setIshaMessage(Constants.ISHA_ALERT_MSG + " " + marchDaylightSavingDay + " @");
			alertResponse.setIshaJamat(marchDaylightSavingDay + " @ 8:45 PM");
		}
		if(intMonth == 2) {
			alertResponse.setIshaMessage(Constants.ISHA_ALERT_MSG + " " + marchDaylightSavingDay + "MARCH 1 @");
			alertResponse.setIshaJamat(marchDaylightSavingDay + " @ 8:45 PM");
		}

		if((intMonth == 3) && (intDate >=1) && (intDate <= marchDaylightSavingDay)) {
			alertResponse.setIshaMessage(Constants.ISHA_ALERT_MSG + " " + marchDaylightSavingDay + " @");
			alertResponse.setIshaJamat(marchDaylightSavingDay + " @ 8:45 PM");
		}
		//=============Daylight savings November=================
		if((intMonth == 3) && (intDate >= marchDaylightSavingDay) && (intDate <= 31)) {
			alertResponse.setIshaMessage(Constants.ISHA_ALERT_MSG + " " + " APRIL 1 @ ");
			alertResponse.setIshaJamat("APRIL 1 @ 9:15 PM");
		}

		//==============================================
		if((intMonth == 4) && (intDate >= 1) && (intDate <= 13)) {
			alertResponse.setIshaMessage(Constants.ISHA_ALERT_MSG + " " + " APRIL 14 @ ");
			alertResponse.setIshaJamat("APRIL 14 @ 9:30 PM");
		}
		if((intMonth == 4) && ((intDate >= 1) && (intDate <=8))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "APR 9 @ ");
			alertResponse.setAsrJamat("APR 9 @ 5:20 AM");
		}
		if((intMonth == 4) && ((intDate >= 9) && (intDate <= 22))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "APR 23 @ ");
			alertResponse.setFajrJamat("APR 23 @ 5:05 AM");
		}
		if((intMonth == 4) && ((intDate >= 23) && (intDate <= 30))) {

			alertResponse.setAsrMessage(Constants.ISHA_ALERT_MSG + " " + "MAY 1 @ ");
			alertResponse.setAsrJamat("MAY 1 @ 5:00 AM");
		}
		if((intMonth == 5) && ((intDate >= 1) && (intDate <= 7))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "MAY 8 @ ");
			alertResponse.setAsrJamat("MAY 8 @ 4:45 AM");
		}
		if((intMonth == 5) && ((intDate >= 8) && (intDate <= 18))) {
			alertResponse.setFajrMessage(Constants.ASR_ALERT_MSG + " " + "MAY 19 @ ");
			alertResponse.setAsrJamat("MAY 19 @ 4:30 AM");
		}
		if(( (intMonth == 5) && (intDate >= 19)) || (intMonth == 6) || ( (intMonth == 7) && (intDate <= 13))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "JULY 14 @ ");
			alertResponse.setAsrJamat("JULY 14 @ 4:45 AM");
		}

		if((intMonth == 7) && ((intDate >= 14) && (intDate <= 31))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 1 @ ");
			alertResponse.setAsrJamat("AUG 1 @ 5:00 AM");
		}
		if((intMonth == 8) && ((intDate >= 1) && (intDate <= 10))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 11 @ ");
			alertResponse.setAsrJamat("AUG 11 @ 5:15 AM");
		}
		if((intMonth == 8) && ((intDate >= 11) && (intDate <= 22))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "AUG 23 @ ");
			alertResponse.setAsrJamat("AUG 23 @ 5:30 AM");
		}
		if((intMonth == 8) && ((intDate >= 23) && (intDate <= 31))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "SEP 1 @ ");
			alertResponse.setAsrJamat("SEP 1 @ 5:45 AM");
		}
		if((intMonth == 9) && ((intDate >= 1) && (intDate <= 11))) {

			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "OCT 1 @ ");
			alertResponse.setAsrJamat("OCT 1 @ 6:00 AM");
		}
		if((intMonth == 10) && (intDate >= 1) && (intDate == 11) ) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "OCT 11 @ ");
			alertResponse.setAsrJamat("OCT 11 @ 6:15 AM");
		}
		if( (intMonth == 10) && (intDate >= 11) ) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "NOV 1 @ ");
			alertResponse.setAsrJamat("NOV 1 @ 6:30 AM");
		}


		//====================NOVEMBER Daylight saving begins =======================================
		if((intMonth == 11) && ((intDate >= 01) && (intDate < novDaylightSavingDay))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + novDaylightSavingDay + " @ ");
			alertResponse.setAsrJamat(novDaylightSavingDay + " @ 3:30 PM");
		}
		if((intMonth == 11) && ((intDate >= novDaylightSavingDay) && (intDate <= 14))) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "NOV 15 @ ");
			alertResponse.setAsrJamat("NOV 15 @ 3:00 PM");
		}
		//====================NOVEMBER Daylight saving end =======================================
		if((intMonth == 11) && (intDate >= 15)) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @ ");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 PM");
		}
		if((intMonth == 12)) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @  ");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 PM");
		}
		if(intMonth == 1) {
			alertResponse.setAsrMessage(Constants.ASR_ALERT_MSG + " " + "FEB 1 @");
			alertResponse.setAsrJamat("FEB 1 @ 3:45 AM");
		}

		return alertResponse.getAsrMessage() + alertResponse.getAsrJamat();

	}

}
