package com.zhsq.biz.constant;

import java.util.HashMap;
import java.util.Map;

public class BaseConstant {

	public static final Integer LABEL_AUTH_工作任务管理类=30153;//权限标签之工作任务管理类
	
	
	public static final String TYPE_工作任务="XFJDE379";//工作任务类型
	public static final String TYPE_人口信息="XFJDE001";//人口信息类型
	public static final String TYPE_家庭信息="XFJDE305";//人口信息类型
	public static final String TYPE_四类人员="XFJDE1015";//人员分类统计
	public static final String TYPE_用户="ABCBE002";//用户
	public static final String TYPE_组织="XFJDE1334";//组织
	
	public static final String Column_ABP0001 = "ABP0001";
	
	/**
	 * 根据枚举和户主关系获取人与人之间的家庭成员的关系
	 */
	private static Map map = new HashMap<>();
	static {
		map.put(EnumKeyValue.ENUM_和户主关系_母亲, RelationType.RR_人口信息_子女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_父亲, RelationType.RR_人口信息_子女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_配偶, RelationType.RR_人口信息_夫妻_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_女, RelationType.RR_人口信息_父母_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_子, RelationType.RR_人口信息_父母_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_孙女, RelationType.RR_人口信息_爷爷奶奶_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_孙子, RelationType.RR_人口信息_爷爷奶奶_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_奶奶, RelationType.RR_人口信息_孙子孙女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_爷爷, RelationType.RR_人口信息_孙子孙女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_外公, RelationType.RR_人口信息_外孙子和外孙女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_外婆, RelationType.RR_人口信息_外孙子和外孙女_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_外孙女, RelationType.RR_人口信息_外公和外婆_人口信息);
		map.put(EnumKeyValue.ENUM_和户主关系_外孙子, RelationType.RR_人口信息_外公和外婆_人口信息);
		/*map.put(EnumKeyValue.ENUM_和户主关系_户主, RelationType.RR_人口);
		 * map.put(EnumKeyValue.ENUM_和户主关系_孙女, RelationType.RR_人口信息_);
		map.put(EnumKeyValue.ENUM_和户主关系_孙子, value);
		map.put(EnumKeyValue.ENUM_和户主关系_外孙女, value);
		map.put(EnumKeyValue.ENUM_和户主关系_外孙子, value);
		
		map.put(EnumKeyValue.ENUM_和户主关系_曾外孙女, value);
		map.put(EnumKeyValue.ENUM_和户主关系_曾外孙子, value);*/
	}
	
	public static String getRelationName(String enumRelationName) {
		if (enumRelationName== null || "".equals(enumRelationName)) {
			return null;
		}
		
		int parseInt;
		try {
			parseInt = Integer.parseInt(enumRelationName);
		} catch (NumberFormatException e) {
			return null;
		}
		
		String relationName = (String)map.get(parseInt);
		return relationName;
	} 
}
