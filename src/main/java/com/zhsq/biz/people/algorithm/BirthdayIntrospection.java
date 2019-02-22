package com.zhsq.biz.people.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zhsq.biz.constant.EnumKeyValue;

public class BirthdayIntrospection {
	
	private static String date = "yyyy-MM-dd";
	private static String dateTime = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(date);
	
	/**
	 * 根据老人出生日期获取老人标签
	 * 
	 * @return  60-69岁老人  70-79岁老人
	 */
	@SuppressWarnings("deprecation")
	public static Integer getOldLaber(String birthday) {
		if (birthday == null || "".equals(birthday)) {
			return null;
		}
		try {
			Integer age = extractAge(birthday);
			
			if (age>=90) {
				return EnumKeyValue.ENUM_人口标签_90以上岁老人;
			} else if (age>=80) {
				return EnumKeyValue.ENUM_人口标签_80_89岁老人;
			} else if (age>=70) {
				return EnumKeyValue.ENUM_人口标签_70_79老人;
			} else if (age>=60) {
				return EnumKeyValue.ENUM_人口标签_60_69老人;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 提取年龄信息
	 * @param id
	 * @return
	 */
	public static Integer extractAge(String birthday){
		
		if (birthday == null || "".equals(birthday)) {
			return 0;
		}
		try {
			Date birthdayDate = sdf.parse(birthday);
			
			//获取当前年， 月
			Calendar ca =Calendar.getInstance();
			int nowYear= ca.get(Calendar.YEAR);
			int nowMonth= ca.get(Calendar.MONTH)+1;
			int nowDay = ca.get(Calendar.DAY_OF_MONTH);
			//获取生日年月
			ca.setTime(birthdayDate);
			int IDYear=ca.get(Calendar.YEAR);
			int IDMonth=ca.get(Calendar.MONTH)+1;
			int IDDay=ca.get(Calendar.DAY_OF_MONTH);
			
			
			//2019 1 24 
			// 2017 1 24
			
			if(IDMonth == nowMonth){//月份相同
				if(nowDay >=IDDay) {//当前天数大
					return nowYear-IDYear;
				} else {
					return nowYear-IDYear-1;
				}
			}else {//月份不同
				if((nowMonth-IDMonth)>0){//当前月大
					return nowYear-IDYear;
				}else {
					return nowYear-IDYear-1;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
}
