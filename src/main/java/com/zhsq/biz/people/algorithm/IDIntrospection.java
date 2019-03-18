package com.zhsq.biz.people.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.SystemOutLogger;

import com.abc.complexus.RecordComplexus;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.abc.relation.RelationQueryPanel;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.CommonAlgorithm;
import com.zhsq.biz.constant.DateUtils;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.constant.RelationType;
import com.zhsq.biz.constant.interview.InterviewItem;
import com.zhsq.biz.constant.people.PeopleItem;

public class IDIntrospection {
	
	
	//获取最新一条关系的指定属性的值， 最新判断-》属性应该为日期型
	/**
	 * 
	 * @param recordComplexus
	 * @param recordName
	 * @param recordCode
	  * @param relationType   指定的关系类型      type==RelationType.RR_人口信息_走访记录_走访记录
	 * @param isItemValue   判断为最新的依据（必须为日期型）  实例中具体的属性字段        InterviewItem.走访时间
	 * @param resultItemValue  返回的结果值        InterviewItem.走访时间  任何值
	 * @return
	 */
	public static String getNewestRelationPro(RecordComplexus recordComplexus,String recordName, String recordCode, String relationType, String isItemValue, String resultItemValue) {
		RecordRelation newestRecordRelation = getNewestRecordRelation(recordComplexus, recordName, recordCode, relationType, isItemValue);
		String birthStr = null;
		if (newestRecordRelation !=null) {
			birthStr = (String)CommonAlgorithm.getDataValue(recordComplexus, newestRecordRelation.getRight(), resultItemValue);
		}
		
		return birthStr;
	}
	
	/**
	 * 获取指定关系中 最新的一条关系
	 * 指定字段  必须为日期型
	 * @param recordComplexus
	 * @param recordName
	 * @param recordCode
	 * @param relationType   指定的关系类型       type==RelationType.RR_人口信息_走访记录_走访记录
	 * @param isItemValue     实例中具体的属性字段     InterviewItem.走访时间  任何值
	 * @return
	 */
	public static RecordRelation getNewestRecordRelation(RecordComplexus recordComplexus,String recordName, String recordCode, String relationType, String isItemValue) {
		RecordRelation resultRecordRelation = null;
		Long beforeTime = 0l;
		
		RelationCorrelation	relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		if (relationCorrelation != null) {
			Collection<RecordRelation> recordRelation = relationCorrelation.getRecordRelation();
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					//根据关系的属性， 获取最新的
					if (relationType.equals(recordRelation2.getType())) {
						String dataValue = (String)CommonAlgorithm.getDataValue(recordComplexus, recordRelation2.getRight(), isItemValue);
						
						Long longTime = DateUtils.toLongTime(null, dataValue);
						if (longTime != null && longTime > beforeTime) {
							beforeTime = longTime;
							resultRecordRelation = recordRelation2;
						}
						
					}
				}
			}
		}
		
		return resultRecordRelation;
	}
	
	
	/**
	 * 获取子女的数量， 
	 * 下次更新》 获取指定关系的数量
	 * @param recordComplexus
	 * @param recordName
	 * @param recordCode
	 * @return
	 */
	public static Integer getChildrenCount(RecordComplexus recordComplexus,String recordName, String recordCode) {
		Integer count = 0;
		//RootRecord recordCompound=recordComplexus.getHostRootRecord();
		/*RelationQueryPanel.get(recordName, recordCode);// 此方法是从数据库中读取
		RelationCorrelation relationCorrelation = 
		recordComplexus.getRelationCorrelation(recordCode);//此方法是当前页面加载进来的关系
		*/
		RelationCorrelation relationCorrelation = null;
		relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		if (relationCorrelation !=null) {
			Collection<RecordRelation> recordRelation = relationCorrelation.getRecordRelation();
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					if (RelationType.RR_人口信息_子女_人口信息.equals(recordRelation2.getType())) {
						count++;
					}
				}
			}
		}
		
		return count;
	}
	
	/**
	 * 身份证号校验
	 * @param id
	 * @return
	 */
	public static boolean inspectId(String id){
		
		if (id == null || "".equals(id)) {
			return false;
		}
		if (id.length() == 15) {
			return validate15IDCard(id);
		} 
		return validate18Idcard(id);
	}
	
	/**
	 * 身份证号出生日期与出生日期属性是否一致
	 * @param id  身份证号
	 * @param birthday 2018-09-18
	 * @return 一致： true   不一致： false
	 * @throws ParseException 
	 */
	public static boolean inspectBirthday(String id, String birthday) throws ParseException {
		if (birthday == null || "".equals(birthday)) {
			return false;
		}
		
		if (id == null || "".equals(id) || id.length()!=18) {
			return false;
		}
		String extractBirthday = extractBirthday(id);
		
		if (extractBirthday == null || extractBirthday == "") {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdayDate = sdf.parse(birthday);
		Date parse = sdf.parse(extractBirthday);
		
		if (parse.getTime() == birthdayDate.getTime()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 身份证号性别与性别属性是否一致
	 * @param id  身份证号
	 * @param sex 
	 * @return 一致： true   不一致： false
	 */
	public static boolean inspectSex(String id, String sex) {
		if (sex == null || "".equals(sex)) {
			return false;
		}
		
		if (id == null || "".equals(id) || id.length()!=18) {
			return false;
		}
		Integer extractSex = extractSex(id);
		
		if (extractSex == null) {
			return false;
		}
		
		Integer sexInt = Integer.parseInt(sex);
		if (extractSex.equals(sexInt)) {
			return true;
		}
		return false;
	}

	/**
	 * 身份证号15位转18位
	 * @param id
	 * @return
	 */
	public static String convertIdTo18(String idcard){
		if (validate15IDCard(idcard)==false){
			return idcard;
		}
		String birthday = idcard.substring(6, 12);
		birthday = "19" +birthday;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date birthdate = null;
		try {
			birthdate = sdf.parse(birthday);
			String tmpDate = sdf.format(birthdate);
			if (!tmpDate.equals(birthday)) {// 身份证日期错误
				return null;
			}
		} catch (ParseException e1) {
			return null;
		}
		Calendar cday = Calendar.getInstance();
		cday.setTime(birthdate);
		String year = String.valueOf(cday.get(Calendar.YEAR));
		String idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);
		char c[] = idcard17.toCharArray();
		String checkCode = "";
		// 将字符数组转为整型数组
		int bit[] = converCharToInt(c);
		int sum17 = 0;
		sum17 = getPowerSum(bit);
		// 获取和值与11取模得到余数进行校验码
		checkCode = getCheckCodeBySum(sum17);
		// 获取不到校验位
		if (null == checkCode) {
			return null;
		}
		// 将前17位与第18位校验码拼接
		idcard17 += checkCode;
		return idcard17;
	}
	
	/**
	 * 提取生日信息，格式 yyyy-mm-dd
	 * @param id
	 * @return
	 */
	public static String extractBirthday(String id){
		String tmpDate = "";
		 if(inspectId(id)){
				if (id.length() == 15) {
					id=convertIdTo18(id);
				}
				try{
				// 校验出生日期
				String birthday = id.substring(6, 14);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date birthDate = sdf.parse(birthday);
			         tmpDate = sdf2.format(birthDate);
				}catch (ParseException e1) {
					return null;
				}
              return tmpDate;
		 }
		return null;
	}
	
	/**
	 * 提取年龄信息
	 * @param id
	 * @return
	 */
	public static String extractAge(String id){
		Calendar ca =Calendar.getInstance();
		int nowYear= ca.get(Calendar.YEAR);
		int nowMonth= ca.get(Calendar.MONTH)+1;
		int nowDay = ca.get(Calendar.DAY_OF_MONTH);
		 if(inspectId(id)){
			if (id.length() == 15) {
				id=convertIdTo18(id);
			}
		int IDYear=Integer.parseInt(id.substring(6,10));
		int IDMonth=Integer.parseInt(id.substring(10,12)); 	
		int IDDay=Integer.parseInt(id.substring(12,14)); 
		
		if(IDMonth == nowMonth){//月份相同
			if(nowDay >IDDay) {//当前天数大
				return nowYear-IDYear+"";
			} else {
				return nowYear-IDYear-1+"";
			}
		}else {//月份不同
			if((nowMonth-IDMonth)>0){//当前月大
				return nowYear-IDYear+"";
			}else {
				return nowYear-IDYear-1+"";
			}
		}
		
		}
		return null;
	}
	
	/**
	 * 提取性别信息
	 * @param id
	 * @return
	 */
	public static Integer extractSex(String id){
		 if(inspectId(id)){
				if (id.length() == 15) {
					id=convertIdTo18(id);
				}
		
		 String id17 = id.substring(16, 17);  
         if (Integer.parseInt(id17) % 2 != 0) {  
             return EnumKeyValue.ENUM_性别_男;  
         } else {  
             return  EnumKeyValue.ENUM_性别_女;  
         }  
		 }
		return null;
	}
	
	/**
	 * 身份证号校验
     --15位身份证号码：
	 *  第7、8位为出生年份(两位数)，第9、10位为出生月份，
	 *  第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。 
     --18位身份证号码：
	 *   第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，
 	 *   第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。 
     *   最后一位为校验位 
	 * <pre>
	 * 省、直辖市代码表：
	 *     11 : 北京  12 : 天津  13 : 河北       14 : 山西  15 : 内蒙古  
	 *     21 : 辽宁  22 : 吉林  23 : 黑龙江  31 : 上海  32 : 江苏  
	 *     33 : 浙江  34 : 安徽  35 : 福建       36 : 江西  37 : 山东  
	 *     41 : 河南  42 : 湖北  43 : 湖南       44 : 广东  45 : 广西      46 : 海南  
	 *     50 : 重庆  51 : 四川  52 : 贵州       53 : 云南  54 : 西藏  
	 *     61 : 陕西  62 : 甘肃  63 : 青海       64 : 宁夏  65 : 新疆  
	 *     71 : 台湾  
	 *     81 : 香港  82 : 澳门  
	 *     91 : 国外
	 * </pre> 
     *   
     *   
	 * 
	 */
	
	private static String cityCode[] = { "11", "12", "13", "14", "15", "21",
		"22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
		"43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
		"63", "64", "65", "71", "81", "82", "91" };
	
	/**
	 * 每位加权因子
	 */
	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
			8, 4, 2 };
	
	
	/**
	 * <p>
	 * 判断18位身份证的合法性
	 * </p>
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * <p>
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 * </p>
	 * <p>
	 * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
	 * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
	 * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
	 * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
	 * </p>
	 * <p>
	 * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2
	 * </p>
	 * <p>
	 * 2.将这17位数字和系数相乘的结果相加。
	 * </p>
	 * <p>
	 * 3.用加出来和除以11，看余数是多少
	 * </p>
	 * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
	 * 2。
	 * <p>
	 * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 * </p>
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean validate18Idcard(String idcard) {
		if (idcard == null) {
			return false;
		}

		// 非18位为假
		if (idcard.length() != 18) {
			return false;
		}
		// 获取前17位
		String idcard17 = idcard.substring(0, 17);

		// 前17位全部为数字
		if (!isDigital(idcard17)) {
			return false;
		}

		String provinceid = idcard.substring(0, 2);
		// 校验省份
		if (!checkProvinceid(provinceid)) {
			return false;
		}

		// 校验出生日期
		String birthday = idcard.substring(6, 14);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			Date birthDate = sdf.parse(birthday);
			String tmpDate = sdf.format(birthDate);
			if (!tmpDate.equals(birthday)) {// 出生年月日不正确
				return false;
			}

		} catch (ParseException e1) {

			return false;
		}

		// 获取第18位
		String idcard18Code = idcard.substring(17, 18);

		char c[] = idcard17.toCharArray();

		int bit[] = converCharToInt(c);

		int sum17 = 0;

		sum17 = getPowerSum(bit);

		// 将和值与11取模得到余数进行校验码判断
		String checkCode = getCheckCodeBySum(sum17);
		if (null == checkCode) {
			return false;
		}
		// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
		if (!idcard18Code.equalsIgnoreCase(checkCode)) {
			return false;
		}

		return true;
	}

	/**
	 * 校验15位身份证
	 * 
	 * <pre>
	 * 只校验省份和出生年月日
	 * </pre>
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean validate15IDCard(String idcard) {
		if (idcard == null) {
			return false;
		}
		// 非15位为假
		if (idcard.length() != 15) {
			return false;
		}

		// 15全部为数字
		if (!isDigital(idcard)) {
			return false;
		}

		String provinceid = idcard.substring(0, 2);
		// 校验省份
		if (!checkProvinceid(provinceid)) {
			return false;
		}

		String birthday = idcard.substring(6, 12);

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		try {
			Date birthDate = sdf.parse(birthday);
			String tmpDate = sdf.format(birthDate);
			if (!tmpDate.equals(birthday)) {// 身份证日期错误
				return false;
			}

		} catch (ParseException e1) {

			return false;
		}

		return true;
	}

	/**
	 * 校验省份
	 * 
	 * @param provinceid
	 * @return 合法返回TRUE，否则返回FALSE
	 */
	private static boolean checkProvinceid(String provinceid) {
		for (String id : cityCode) {
			if (id.equals(provinceid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 数字验证
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDigital(String str) {
		return str.matches("^[0-9]*$");
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param bit
	 * @return
	 */
	private static int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	/**
	 * 将和值与11取模得到余数进行校验码判断
	 * 
	 * @param checkCode
	 * @param sum17
	 * @return 校验位
	 */
	private static String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	/**
	 * 将字符数组转为整型数组
	 * 
	 * @param c
	 * @return
	 * @throws NumberFormatException
	 */
	private static int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}
	
	//出生日期字符串转化成Date对象 
    public  Date parse(String strDate) throws ParseException {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        return sdf.parse(strDate);  
    }  
    
    
    public static void testIdcard(String id){
    	System.out.println( "合法性："+inspectId(id));
    	if(inspectId(id)){
    		if (id.length() == 15) {
				System.out.println("转换为18位身份证为："+convertIdTo18(id));
			}
    		System.out.println("周岁："+extractAge(id));
    		System.out.println("性别："+extractSex(id));
    		System.out.println("出生年龄："+extractBirthday(id));
    	}
		
    }

	
	public static void main(String[] args) {
		/*String  idcard = "533123196002180054";
		String idcard3 = "210102198617083732";
		String myidcard2 ="533123198606140073";
		testIdcard(idcard);	*/
		
		String idcard1 = "533123196002180054";
		System.out.println("性别："+extractSex(idcard1));
		System.out.println("出生年龄："+extractBirthday(idcard1));
		System.out.println();
	}
}