package com.thtf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String string2String(String dateString,String fromPattern,String toPattern){
		DateFormat df = null;
		Date date = null;
		String dateStr = null;
		try {
			df = new SimpleDateFormat(fromPattern);
			date = df.parse(dateString);
			df = new SimpleDateFormat(toPattern);
			dateStr = df.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateStr;
	}
	public static String beforeDate2String(String dateString,int field,int amount,String pattern ) {
		Date date = DateUtil.string2Date(dateString, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		String s =  DateUtil.date2String(cal.getTime(), pattern);
		return s;
	}
	public static Date string2Date(String dateString, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String date2String(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static int maxMonth(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		int max = cal.getMaximum(Calendar.DAY_OF_MONTH);
		return max;
	}

	public static String beforeDateStr(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		cal.add(cal.DATE, -1);// .get(field)
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(cal.getTime());
	}

	public static String beforeMonthStr(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1); // 得到前一个月
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(cal.getTime());
	}

	public static String beforeYearStr(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1); // 得到前一个月
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(cal.getTime());
	}
	public static String YearStr(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		cal.add(Calendar.YEAR, 0); // 得到前一个月
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(cal.getTime());
	}

	public static String strDateStr(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);

		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(cal.getTime());
	}

	public static int days(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;

			}
		} else {

			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;

		}
		return days;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.beforeMonthStr("2010-10-29 14:28:33",
				"yyyy-MM"));
	}
}
