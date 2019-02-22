package com.zhsq.biz.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static String date = "yyyy-MM-dd";
	private static String dateTime = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = null;
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
