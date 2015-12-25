package com.mgl.twiitermanager.util;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * Zyos Class ManagerDates.java create by: �scar Garz�n. This Class Manager all
 * methods necessaries by the handle of the dates.
 */


public class ManageDate {
	/**
	 * System date in format: yyyymmdd.
	 */
	public static final String YYYYMMDD = "yyyyMMdd";
	/**
	 * System hour in format: hhmm.
	 */
	public static String HHMM = "HHmm";
	/**
	 * System hour in format: hh:mm:ss.
	 */
	public static String HH_MM_SS = "HH:mm:ss";

	/**
	 * System hour in format: hh:mm
	 */
	public static String HH_MM = "HH:mm";
	/**
	 * System date in format: yyyy/mm/dd.
	 */
	public static String YYYY_MM_DD = "yyyy/MM/dd";
	
	public static String DD_MM_YYYY = "dd/MM/yyyy";
	/**
	 * Format for full system date
	 */
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

	public static String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";

	/**
	 * Default constructor
	 */
	public ManageDate() {
	}

	/**
	 * Get the system current date in the format specified in the parameter.
	 * 
	 * @param format
	 *            Format specified of the date
	 */
	public static String getCurrentDate(String format) {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
		return sdf.format(currentDate);
	}
	
	/**
	 * DHAJ
	 * @param format
	 * @return
	 */
	public static String getCustomDate(String format, int day) {		
		Date currentDate = new Date();
		currentDate.setDate(currentDate.getDate() + day);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
		return sdf.format(currentDate);
	}
	
	/**
	 * DHAJ
	 * @param strDate
	 * @return
	 */
	public static Date stringToDate(String strDate){
		 try {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			  return sdf.parse(strDate);
		 } catch (Exception e) {
		 }
		return null;
	}

	/**
	 * Get the system current hour in the format specified in the parameter.
	 * 
	 * @param format
	 *            format specified of the hour
	 */
	public static String getCurrentHour(String format) {
		Date currentHour = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
		return sdf.format(currentHour);
	}

	/**
	 * Return a Date about the format
	 * 
	 * @param format
	 *            date format
	 * @param format2
	 *            hour format
	 * @return
	 */
	public static Date getCurrentTimestamp(String format, String format2) {
		SimpleDateFormat sdf = new SimpleDateFormat(format + " " + format2);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
		return sdf.getCalendar().getTime();
	}

	/**
	 * Return a Date about the format
	 * 
	 * @param format
	 *            date YYYY_MM_DD
	 * @param format2
	 *            hour HH_MM_SS
	 * @return
	 */
	public static Date getCurrentTimestamp() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.getCalendar().setTime(new Date());
		return dateFormat.getCalendar().getTime();
	}

	/**
	 * Build the date about your components and let ready by insert to database
	 * in format: AAAAMMDD.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 */
	public String createDate(String day, String month, String year) {
		return year + month + day;
	}

	/**
	 * Build the hour about your components and let ready by insert to database
	 * in format : HHMMSS.
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public String createTime(String hour, String minute, String second) {
		return hour + minute + second;
	}

	/**
	 * Get the year of a date that have in the format YYYY/MM/DD.
	 * 
	 * @param date
	 * @return year in the format YYYY
	 */
	public static String getYear(String date) {
		return date.substring(0, 4);
	}

	/**
	 * Get the month of a date that have in the format YYYY/MM/DD
	 * 
	 * @param date
	 * @return month in the format MM
	 */
	public static String getMonth(String date) {
		return date.substring(5, 7);
	}

	/**
	 * Get the day of a date that have in the format YYYY/MM/DD
	 * 
	 * @param date
	 * @return day in the format DD
	 */
	public static String getDay(String date) {
		return date.substring(8, 10);
	}

	/**
	 * Get the hour of a time that have in the format HH:MM:SS
	 * 
	 * @param time
	 * @return hour in the format HH
	 */
	public static String getHour(String time) {
		return time.substring(0, 2);
	}

	/**
	 * Get the minute of a time that have in the format HH:MM:SS
	 * 
	 * @param time
	 * @return minute in the format MM
	 */
	public static String getMinute(String time) {
		return time.substring(4, 5);
	}

	/**
	 * Get the second of a time that have in the format HH:MM:SS
	 * 
	 * @param time
	 * @return second in the format SS
	 */
	public static String getSecond(String time) {
		return time.substring(7, 8);
	}

	/**
	 * Converter a Date in format java.util.Date to format YYYY/MM/DD.
	 * 
	 * @param date
	 *            in format YYYYMMDD � yyyy/mm/dd � hh:ss � hh:mm:ss
	 * @return date in format about the format
	 */
	public static String formatDate(Date _date, String _format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_format);
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
			String date = sdf.format(_date);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converter a time in format HHMMSS to format HH:MM:SS
	 * 
	 * @param time
	 *            in format HHMMSS
	 * @return time in format HH:MM:SS
	 */
	public static String formatHour(String time) {
		String hourFormato = "";
		hourFormato += time.substring(0, 2);
		hourFormato += ":";
		hourFormato += time.substring(2, 4);
		hourFormato += ":";
		hourFormato += time.substring(2, 6);
		return hourFormato;
	}

	/**
	 * Converter a any date about the format YYYY/MM/DD | HH:MM:SS to format
	 * java.util.Date.
	 * 
	 * @param date
	 *            in format YYYY/MM/DD
	 * @return date in format Date
	 */
	public static Date formatDate(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method that compares two dates in the format YYYY/MM/DD
	 * 
	 * @param _firstDate
	 *            first date in format YYYY/MM/DD
	 * @param _endDate
	 *            end date in format YYYY/MM/DD
	 * @return the value <code>0</code> if the argument Date is equal to this
	 *         Date; a value less than <code>0</code> if this Date is before the
	 *         Date argument; and a value greater than <code>0</code> if this
	 *         Date is after the Date argument.
	 */
	public static int dateComparator(String _firstDate, String _endDate,
			String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
			sdf.setLenient(false);
			Date firstDate = (Date) sdf.parse(_firstDate);
			Date endDate = (Date) sdf.parse(_endDate);
			return firstDate.compareTo(endDate);
		} catch (Exception e) {
			return -100;
		}
	}

	/**
	 * it deduct two dates and return the value in days.
	 * 
	 * @param start
	 *            date start
	 * @param end
	 *            date end
	 * @return difference between end - start
	 */
	public static double deductDates(Date start, Date end) {
		try {
			return (start.getTime() - end.getTime()) / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * @param timeToPlus
	 * @return
	 */
	public static Date plusDateWithFormat(String date, int timeToPlus,
			String format, int calendarVariable) {
		try {
			return plusDate(formatDate(date, format), timeToPlus,
					calendarVariable);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for plus a value to date. Is necessary put the calendar variable
	 * (Calendar.HOUR_OF_DAY,
	 * Calendar.DAY_OF_YEAR,Calendar.HOUR_OF_DAY,Calendar.DAY_OF_MONTH)
	 * 
	 * @param date
	 * @param value
	 * @param calendarVarible
	 * @return
	 */
	public static Date plusDate(Date date, int value, int calendarVarible) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendarVarible, value);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method for plus a value to date. Is necessary put the calendar variable
	 * (Calendar.HOUR_OF_DAY,
	 * Calendar.DAY_OF_YEAR,Calendar.HOUR_OF_DAY,Calendar.DAY_OF_MONTH)
	 * 
	 * @param date
	 * @param value
	 * @param calendarVarible
	 * @return
	 */
	public static Date plusDateWithFixMonth(Date date, int value,
			int calendarVarible) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendarVarible, value);
			calendar.add(Calendar.MONTH, 1);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}
}
