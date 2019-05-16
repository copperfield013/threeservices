package com.zhsq.biz.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static String date = "yyyy-MM-dd";
	private static String dateTime = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = null;
	
	/**
	 *	 当前时间与给定时间差几天
	 * @return 返回时间差
	 * @param time  格式必须为YYYY-MM-dd
	 * @return
	 * @throws ParseException 
	 */
	public static Integer getShortDaytoCurr(String birthStr) {
		 LocalDate today = LocalDate.now();
		try {
			
			if (birthStr == null) {
				return null;
			}
			sdf = new SimpleDateFormat(dateTime);
			LocalDate birthDate =getLocalDate(sdf.parse(birthStr));
		  long between = ChronoUnit.DAYS.between(birthDate, today);
		  return  (int) Math.abs(between);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 指定日期字符串转换为LocalDate对象  
	 * 目前支持yyyy-MM-dd
	 * @param dateStr
	 * @return
	 */
	public static LocalDate getLocalDate(Date date) {
		 try {
			 
			 if (date == null) {
					return null;
				}
			 
			Calendar ca =Calendar.getInstance();
			ca.setTime(date);
			int IDYear=ca.get(Calendar.YEAR);
			int IDMonth=ca.get(Calendar.MONTH)+1;
			int IDDay=ca.get(Calendar.DAY_OF_MONTH);
			return LocalDate.of(IDYear, IDMonth, IDDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据年龄获取出生日期
	 * @return
	 */
	public static LocalDate getBirthByAge(Integer age) {
		 LocalDate today = LocalDate.now();
		return today.plusYears(age);
	}
	
	/**
	 * 返回日期的毫秒值
	 * @param time
	 * @return
	 */
	public static Long toLongTime(String dateFormat, String time) {
		if (time == null || "".equals(time)) {
			return null;
		}
		
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = date;
		} 
		
		sdf = new SimpleDateFormat(dateFormat);
		try {
			
			Date date = sdf.parse(time);
			long time2 = date.getTime();
			return time2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		return null;
	}
}
