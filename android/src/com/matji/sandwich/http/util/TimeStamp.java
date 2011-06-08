package com.matji.sandwich.http.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	public static String getAgoFromDate(String dateString){
		dateString = dateString.replace('T', ' ');
		dateString = dateString.replaceAll("Z", "");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;		
		try {
			date = (Date)format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return getTimeStamp(date);
	}
	
	private static String getTimeStamp(Date date) {
		long x = 0;		
		String timestampText;
		String year = "year ago";
		String years = "years ago";
		String month = "month ago";
		String months = "months ago";
		String week = "week ago";
		String weeks = "weeks ago";
		String day = "day ago";
		String days = "days ago";
		String hour = "hour ago";
		String hours = "hours ago";
		String min = "min ago";
		String sec = "sec ago";
		Date now = new Date();
		long nowSeconds = now.getTime() /1000;
		long postSeconds = date.getTime() /1000;
		long time = nowSeconds - postSeconds;

		if ((x = time / 31104000) > 0) { // year
			if (x == 1) {
				timestampText = x + " " + year; // singular
			} else {
				timestampText = x + " " + years;	// plural
			}
		} else {
			if ((x = time / 2592000) > 0) { // month
				if (x == 1) {
					timestampText = x+ " " + month; // singular
				} else {
					timestampText = x+ " " + months; // plural
				}
			} else {
				if ((x = time / 604800) > 0) { // week
					if (x == 1) {
						timestampText = x+ " " + week; // singular
					} else {
						timestampText = x+ " " + weeks; // plural
					}
				} else {
					if ((x = time / 86400) > 0) { // day
						if (x == 1) {
							timestampText = x+ " " + day; // singular
						} else {
							timestampText = x+ " " + days; // plural
						}
					} else {
						if ((x = time / 3600) > 0) { // hour
							if (x == 1) {
								timestampText = x+ " " + hour; // singular
							} else {
								timestampText = x+ " " + hours; // plural
							}
						} else {
							if ((x = time / 60) > 0) { // min
								timestampText = x+ " " + min;
							} else { // sec
								timestampText = time+ " " + sec;
							}
						}
					}
				}
			}
		}		

		return timestampText;
	}
}
