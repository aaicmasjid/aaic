package com.shafi.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Component
@Data
@JsonInclude(Include.NON_NULL)
public class AlertResponse {
	
	String fajrMessage;
	String dhuhrMessage;
	String ishaMessage;
	String asrMessage;
	String maghribMessage;
	String fajrJamat;
	String dhuhrJamat;
	String asrJamat;
	String ishaJamat;


}
