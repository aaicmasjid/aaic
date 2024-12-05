package com.shafi.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Component
@Data
@JsonInclude(Include.NON_NULL)
public class Response {
	
	// Fajr - 05:33 am
	//Sunrise - 06:49 am
	//Dhuhr - 12:39 pm
	//Asr - 04:50 pm
	//Sunset - 06:28 pm
	//Maghrib - 06:28 pm//Isha - 07:46 pm
	//Isha - 07:46 pm
	//int id;
	String fajr;
	String sunrise;
	String dhuhr;
	String asr;
	String sunset;
	String maghrib;
	String isha;
	
	//String nextPrayer = "";
	
	String fajrJamat;
	String dhuhrJamat;
	String asrJamat;
	String maghribJamat;
	String ishaJamat;
	
	String nextJamat;
	String jamatYearlyList;
	
	
	String jamatYearlyFajr;
	String jamatYearlyDhuhr;
	String jamatYearlyAsr;
	String jamatYearlyMaghrib;
	String jamatYearlyIsha;
}
