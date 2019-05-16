package com.zhsq.biz.constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import com.abc.complexus.RecordComplexus;
import com.abc.model.enun.ValueType;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.abc.relation.RelationQueryPanel;
import com.abc.rrc.record.Attribute;
import com.abc.rrc.record.LeafRecord;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.casemedreg.CaseMedRegItem;
import com.zhsq.biz.constant.family.FamilyItem;
import com.zhsq.biz.constant.people.PeopleItem;

public class CommonAlgorithm {
	
	
	/**
	 * 动态金额计算
	 * @return
	 */
	private static Integer dynamicAmountCalculation(String str) {
		Integer money = 0;
		/*帮扶->救助->最低生活保障家庭（低保证）->支出型贫困
		帮扶->救助->最低生活保障家庭（低保证）->收入型低保
		帮扶->救助->最低生活保障边缘家庭（低边证）->低保边缘户
		帮扶->救助->最低生活保障家庭（低保证）->残疾人单列施保*/
		
		if (str== null) {
			return 0;
		}
		
		switch (str) {
			case "帮扶->救助->最低生活保障家庭（低保证）->支出型贫困":
				money = 955 + 55;
				break;
			case "帮扶->救助->最低生活保障家庭（低保证）->收入型低保":
				money = 55;
				break;
			case "帮扶->救助->最低生活保障边缘家庭（低边证）->低保边缘户":
				money = 74;
				break;
			case "帮扶->救助->最低生活保障家庭（低保证）->残疾人单列施保":
				money = 955 + 55;
				break;
		}
		
		return money;
	}
	
	/**
	 * 获取月金额
	 * @param recordComplexus
	 * @param recordCode
	 * @param leaf  PeopleItem.事项名称    多值属性本身
	 * @param item   PeopleItem.事项名称_事项金额   多值属性里面的属性
	 * @return
	 */
	public static Integer getMonthMoney(RecordComplexus recordComplexus, String recordCode, String leaf, String item) {
		Integer money = 0;
		
		Collection<LeafRecord> findLeafs = CommonAlgorithm.getLeaFecords(recordComplexus, recordCode, leaf);
		if (findLeafs != null) {
			for (LeafRecord leafRecord : findLeafs) {
				String sqlValue = (String) leafRecord.findAttribute(item).getValue(ValueType.INT);
				money += dynamicAmountCalculation(sqlValue);
			}
		}
		
		return money;
	}
	
	/**
	 * 获取年金额
	 * @param recordComplexus
	 * @param recordCode
	 * @param leaf  PeopleItem.事项名称    多值属性本身
	 * @param item   PeopleItem.事项名称_事项金额   多值属性里面的属性
	 * @return
	 */
	public static Integer getYearMoney(RecordComplexus recordComplexus, String recordCode, String leaf, String item) {
		Integer monthMoney = getMonthMoney(recordComplexus, recordCode, leaf, item);
		Integer yearMoney = monthMoney*12;
		return yearMoney;
	}
	
	/**
	 * 	获取指定关系的数量   
	 * @param recordComplexus
	 * @param recordName
	 * @param recordCode
	 * @param relationType   指定的关系类型       type==RelationType.RR_人口信息_走访记录_走访记录
	 * @return
	 */
	public static Integer getAppointRecordRelationCount(RecordComplexus recordComplexus,String recordName, String recordCode, String relationType) {
		Integer count = 0;
		RelationCorrelation	relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		if (relationCorrelation != null) {
			Collection<RecordRelation> recordRelation = relationCorrelation.getRecordRelation();
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					if (relationType.equals(recordRelation2.getType())) {
						count++;
					}
				}
			}
		}
		
		return count;
	}
	
	
	/**
	 * 	获取指定关系的  右实体code
	 * @param recordComplexus
	 * @param recordName
	 * @param recordCode
	 * @param relationType   指定的关系类型       type==RelationType.RR_人口信息_走访记录_走访记录
	 * @return
	 */
	public static String getAppointRecordRelation(RecordComplexus recordComplexus,String recordName, String recordCode, String relationType) {
		RelationCorrelation	relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		if (relationCorrelation != null) {
			Collection<RecordRelation> recordRelation = relationCorrelation.getRecordRelation();
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					if (relationType.equals(recordRelation2.getType())) {
						return recordRelation2.getRight();
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 根据 recordCode 获取本实例的所有关系
	 * @param recordComplexus
	 * @param recordName    
	 * @param recordCode
	 * @return
	 */
	public static RelationCorrelation getRelationCorrelation(RecordComplexus recordComplexus,String recordName, String recordCode) {
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		return relationCorrelation;
	}
	
	public static RelationCorrelation getRe(RecordComplexus recordComplexus,String recordName, String recordCode) {
		System.out.println();
		RelationCorrelation relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		RelationCorrelation relationCorrelation2 = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, "5f947aefebfc429e9a6336252f49d27c");
	return relationCorrelation2;
	}
	
	
	/**
	 * 根据  recordCode 获取本实例  指定属性的值
	 * @param recordComplexus
	 * @param recordCode
	 * @param itemValue
	 * @return
	 * 
	 * 
	 */
	public static Object getDataValue(RecordComplexus recordComplexus, String recordCode, String itemValue) {
		
		RootRecord rootRecord = recordComplexus.getRootRecord(recordCode);
		
		Object name = null;
		if (rootRecord != null) {
			Attribute findAttribute = rootRecord.findAttribute(itemValue);
			if (findAttribute != null) {
				name = findAttribute.getValue(ValueType.STRING);
			}
		}
		return name;
	}
	
	/**
	 * 	可以根据这个数量判断多值属性是否有值
	 * 获取多值属性值的数量       
	 * @param recordComplexus
	 * @param recordCode
	 * @param leaf
	 * @return
	 */
	public static Integer getLeafCount(RecordComplexus recordComplexus, String recordCode, String leaf) {
		Collection<LeafRecord> findLeafs = CommonAlgorithm.getLeaFecords(recordComplexus, recordCode, leaf);
		if (findLeafs == null) {
			return 0;
		}
		
		return findLeafs.size();
	}
	
	/**
	 * 获取指定的多值属性
	 * @param recordComplexus
	 * @param recordCode
	 * @param leaf
	 * @return
	 */
	private static Collection<LeafRecord> getLeaFecords(RecordComplexus recordComplexus, String recordCode, String leaf) {
		return recordComplexus.getRootRecord(recordCode).findLeafs(leaf);
	}
	
	
	public static boolean isLawfulPassword(Object password){
		
		if(password ==null || !(password instanceof String)){
			return false;
		}
		
		String passwordStr=(String)password;
		if(passwordStr.length()<6){
			return false;
		}
		return true;
		
	}
	
	public static String encryptMD5(Object obj){
		String temp=obj.toString();
		return md5(temp);
	}
	
	/**
	   * 对字符串进行MD5摘要加密，返回结果与MySQL的MD5函数一致
     * 
     * @param input
     * @return 返回值中的字母为小写
     */
    private static String md5(String input) {
        if (null == input) {
            input = "";
        }
        String result = "";
        try {
            // MessageDigest类用于为应用程序提供信息摘要算法的功能，如MD5或SHA算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 获取输入
            md.update(input.getBytes());
            // 获得产出（有符号的哈希值字节数组，包含16个元素）
            byte output[] = md.digest();

            // 32位的加密字符串
            StringBuilder builder = new StringBuilder(32);
            // 下面进行十六进制的转换
            for (int offset = 0; offset < output.length; offset++) {
                // 转变成对应的ASSIC值
                int value = output[offset];
                // 将负数转为正数（最终返回结果是无符号的）
                if (value < 0) {
                    value += 256;
                }
                // 小于16，转为十六进制后只有一个字节，左边追加0来补足2个字节
                if (value < 16) {
                    builder.append("0");
                }
                // 将16位byte[]转换为32位无符号String
                builder.append(Integer.toHexString(value));
            }
            result = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
