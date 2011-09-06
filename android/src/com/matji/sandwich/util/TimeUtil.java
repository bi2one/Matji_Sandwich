package com.matji.sandwich.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.matji.sandwich.R;

public class TimeUtil {
    final static String timezone = "UTC";	
    static int timeOffset = 0;
    
    public static boolean isToday(String form, String time) {
        Date date = new Date(System.currentTimeMillis());
        return parseString(form, date).equals(time);		
    }

    public static Date getDateFromCreatedAt(String created_at) {
        created_at = created_at.replace('T', ' ');
        created_at = created_at.replaceAll("Z", "");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone zone = TimeZone.getTimeZone(timezone);
        format.setTimeZone(zone);

        Date date = null;  

        try {
            date = getDateInTimeZone((Date)format.parse(created_at), TimeZone.getDefault().getID());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static String getDay(Date date) {
        return String.valueOf(date.getDay());
    }

    public static String getDay(String created_at) {
        return String.valueOf(getDateFromCreatedAt(created_at).getDay());
    }

    public static String getAgoFromSecond(long second) {
        long x = 0;		
        String timestampText;
        // year 랑 year 의 차이는????????
        String year = MatjiConstants.string(R.string.year_ago);
        String years = MatjiConstants.string(R.string.years_ago);
        String month = MatjiConstants.string(R.string.month_ago);
        String months = MatjiConstants.string(R.string.months_ago);
        String week = MatjiConstants.string(R.string.week_ago);
        String weeks = MatjiConstants.string(R.string.weeks_ago);
        String day = MatjiConstants.string(R.string.day_ago);
        String days = MatjiConstants.string(R.string.days_ago);
        String hour = MatjiConstants.string(R.string.hour_ago);
        String hours = MatjiConstants.string(R.string.hours_ago);
        String min = MatjiConstants.string(R.string.min_ago);
        String sec = MatjiConstants.string(R.string.sec_ago);
        String about = MatjiConstants.string(R.string.about);

        if ((x = second / 31104000) > 0) { // year
            if (x == 1) {
                timestampText = x + " " + year; // singular
            } else {
                timestampText = about + " " + x + " " + years;	// plural
            }
        } else {
            if ((x = second / 2592000) > 0) { // month
                if (x == 1) {
                    timestampText = x+ " " + month; // singular
                } else {
                    timestampText = about + " " + x+ " " + months; // plural
                }
            } else {
                if ((x = second / 604800) > 0) { // week
                    if (x == 1) {
                        timestampText = x+ " " + week; // singular
                    } else {
                        timestampText = about + " " + x+ " " + weeks; // plural
                    }
                } else {
                    if ((x = second / 86400) > 0) { // day
                        if (x == 1) {
                            timestampText = x+ " " + day; // singular
                        } else {
                            timestampText = about + " " + x+ " " + days; // plural
                        }
                    } else {
                        if ((x = second / 3600) > 0) { // hour
                            if (x == 1) {
                                timestampText = x+ " " + hour; // singular
                            } else {
                                timestampText = about + " " + x+ " " + hours; // plural
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

    public static Date getDateInTimeZone(Date currentDate, String timeZoneId) {
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
    
    public static String parseString(String form, Date date) {
        return (String) android.text.format.DateFormat.format(form, date);
    }
}