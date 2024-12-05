package com.shafi.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Component
@Data
@JsonInclude(Include.NON_NULL)
public class MonthlySchedulePojo {

	
	int year;
	int month;
	int date;
	int day;
}
