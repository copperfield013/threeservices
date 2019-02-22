package com.zhsq.biz.constant;


import java.util.HashMap;
import java.util.Map;

public class MessageCodeConstant {
	private static Map<String, String> a = new HashMap();
	public static String ERR1001 = "ERR1001";
	public static String ERR1003 = "ERR1003";
	public static String ERR1005 = "ERR1005";
	public static String ERR1007 = "ERR1007";
	public static String ERR1009 = "ERR1009";
	public static String INFO1002 = "INFO1002";
	public static String ERR1010 = "ERR1010";
	public static String ERR_E16_0001 = "ERR_E16_0001";
	
	public static String ERR2001 = "ERR2001";
	public static String ERR2002 = "ERR2002";
	public static String ERR2003 = "ERR2003";
	
	public static String getName(String arg) {
		return (String) a.get(arg);
	}

	static {
		a.put(ERR1001, "身份证号校验错误");
		a.put(ERR1003, "15位身份证号");
		a.put(ERR1005, "身份证号年龄与年龄属性不一致");
		a.put(ERR1007, "身份证号出生日期与出生日期属性不一致");
		a.put(ERR1009, "身份证号性别与性别属性不一致");
		a.put(ERR1010, "姓名缺失或不规范");
		a.put(INFO1002, "身份证号校验无误");
		a.put(ERR_E16_0001, "残困人员缺少残疾证信息");
		
		a.put(ERR2001, "家庭缺少户主");
		a.put(ERR2002, "【和户主关系】应为户主");
		a.put(ERR2003, "户主数量应为一个");
	}
}