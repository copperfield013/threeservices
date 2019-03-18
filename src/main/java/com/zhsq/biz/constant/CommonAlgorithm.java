package com.zhsq.biz.constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import com.abc.complexus.RecordComplexus;
import com.abc.relation.RelationCorrelation;
import com.abc.relation.RelationQueryPanel;
import com.abc.rrc.record.Attribute;
import com.abc.rrc.record.LeafRecord;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.casemedreg.CaseMedRegItem;
import com.zhsq.biz.constant.people.PeopleItem;

public class CommonAlgorithm {
	
	/**
	 * 根据 recordCode 获取本实例的所有关系
	 * @param recordComplexus
	 * @param recordName    
	 * @param recordCode
	 * @return
	 */
	public static RelationCorrelation getRelationCorrelation(RecordComplexus recordComplexus,String recordName, String recordCode) {
		return recordComplexus.getRelationCorrelation(recordCode);
	}
	
	/**
	 * 根据 根据 recordCode 获取本实例  指定属性的值
	 * @param recordComplexus
	 * @param recordCode
	 * @param itemValue
	 * @return
	 * 
	 * 
	 */
	public static Object getDataValue(RecordComplexus recordComplexus, String recordCode, String itemValue) {
		RootRecord rootRecord = recordComplexus.getRootRecord(recordCode);
		Object name = "";
		if (rootRecord != null) {
			Attribute findAttribute = rootRecord.findAttribute(itemValue);
			if (findAttribute != null) {
				name = findAttribute.getSqlValue();
			}
		}
		return name;
	}
	
	/**
	 *  判断 指定的多值属性是否有值
	 * @param recordComplexus
	 * @param recordCode
	 * @param leaf     CaseMedRegItem.调解记录
	 * @return   有值: true  无值： false
	 */
	public static boolean isExistLeaf(RecordComplexus recordComplexus, String recordCode, String leaf) {
		Collection<LeafRecord> findLeafs = recordComplexus.getRootRecord(recordCode).findLeafs(leaf);
		
		if (findLeafs == null) {
			return false;
		}
		
		if (findLeafs.size() != 0) {
			return true;
		}
		
		return false;
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
