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
        //        TimeZone zone = TimeZone.getTimeZone(timezone);
        TimeZone zone = TimeZone.getDefault();
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

        if ((x = second / 31104000) > 0) { // year
            timestampText = MatjiConstants.plurals(R.plurals.years_ago, (int) x);
        } else if ((x = second / 2592000) > 0) { // month
            timestampText = MatjiConstants.plurals(R.plurals.months_ago, (int) x);
        } else if ((x = second / 604800) > 0) { // week
            timestampText = MatjiConstants.plurals(R.plurals.weeks_ago, (int) x);
        } else if ((x = second / 86400) > 0) { // day
            timestampText = MatjiConstants.plurals(R.plurals.days_ago, (int) x);
        } else if ((x = second / 3600) > 0) { // hour
            timestampText = MatjiConstants.plurals(R.plurals.hours_ago, (int) x);
        } else if ((x = second / 60) > 0) { // min
            timestampText = String.format(MatjiConstants.string(R.string.min_ago), x);
        } else { // sec
            timestampText = String.format(MatjiConstants.string(R.string.sec_ago), x);
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