package com.zhsq.biz.people.algorithm;

import com.zhsq.biz.constant.EnumKeyValue;

public class CommIntrospection {
	
	/**
	 * 根据就业形式返回四类人员
	 * @return
	 * 	A类=1单位招用人数							
		B类=2自谋职业+4公益性岗位人数							
		C类=3灵活就业+5其他形式就业人数							
		D类=6实际失业人数
	 */
	public static Integer get4TypePeople(Integer jobType) {

		if(jobType == null) {
			return null;
		}
		
		switch (jobType) {
			case 30352:
				return EnumKeyValue.ENUM_四类人员_A类;
			case 30353:
			case 30354:
				return EnumKeyValue.ENUM_四类人员_B类;
			case 30355:
			case 30356:
				return EnumKeyValue.ENUM_四类人员_C类;
			case 30357:
				return EnumKeyValue.ENUM_四类人员_D类;
		}
		return EnumKeyValue.ENUM_四类人员_正常;
	}
	
	public static String get4TypePeopleStr(Integer fouTypePeople) {
	
		switch (fouTypePeople) {
		case 30370:
			return "A";
		case 30371:
			return "B";
		case 30372:
			return "C";
		case 30373:
			return "D";
	}
		return "正常";
	}
}