package com.matji.sandwich.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtil {
	final static String timezone = "UTC";	
	static int timeOffset = 0;
	
	public static String getAgoFromSecond(long second) {
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
		
		if ((x = second / 31104000) > 0) { // year
			if (x == 1) {
				timestampText = x + " " + year; // singular
			} else {
				timestampText = x + " " + years;	// plural
			}
		} else {
			if ((x = second / 2592000) > 0) { // month
				if (x == 1) {
					timestampText = x+ " " + month; // singular
				} else {
					timestampText = x+ " " + months; // plural
				}
			} else {
				if ((x = second / 604800) > 0) { // week
					if (x == 1) {
						timestampText = x+ " " + week; // singular
					} else {
						timestampText = x+ " " + weeks; // plural
					}
				} else {
					if ((x = second / 86400) > 0) { // day
						if (x == 1) {
							timestampText = x+ " " + day; // singular
						} else {
							timestampText = x+ " " + days; // plural
						}
					} else {
						if ((x = second / 3600) > 0) { // hour
							if (x == 1) {
								timestampText = x+ " " + hour; // singular
							} else {
								timestampText = x+ " " + hours; // plural
							}
						} else {
							if ((x = second / 60) > 0) { // min
								timestampText = x+ " " + min;
							} else { // sec
								timestampText = second+ " " + sec;
							}
						}
					}
				}
			}
		}
		
		return timestampText;
	}
	
	public static Date getDateInTimeZone(Date currentDate, String timeZoneId)
	{
		TimeZone tz = TimeZone.getTimeZone(timeZoneId);
		Calendar mbCal = new GregorianCalendar(tz);
		mbCal.setTimeInMillis(currentDate.getTime());
	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
	
		return cal.getTime();
	}
}