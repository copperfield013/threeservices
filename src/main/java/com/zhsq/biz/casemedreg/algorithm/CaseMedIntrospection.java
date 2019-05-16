package com.zhsq.biz.casemedreg.algorithm;

import java.util.Collection;


import com.abc.complexus.RecordComplexus;
import com.abc.model.enun.ValueType;
import com.abc.rrc.record.Attribute;
import com.abc.rrc.record.LeafRecord;
import com.zhsq.biz.constant.DateUtils;
import com.zhsq.biz.constant.casemedreg.CaseMedRegItem;

public class CaseMedIntrospection {
	
	
	/**
	 * 获取调解记录最新一条的调解结果
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Object getMediateRes(RecordComplexus recordComplexus, String recordCode) {
		
		Long beforeTime = 0L;
		Object result = "";
		Collection<LeafRecord> leafs = recordComplexus.getRootRecord(recordCode).findLeafs(CaseMedRegItem.调解记录);
		for (LeafRecord leafRecord : leafs) {
			 Attribute findAttribute = leafRecord.findAttribute(CaseMedRegItem.调解记录_调解日期);
			 String value =(String) findAttribute.getValue(ValueType.STRING);
			Long longTime = DateUtils.toLongTime(null, value);
			if (longTime > beforeTime) {
				beforeTime = longTime;
				result = leafRecord.findAttribute(CaseMedRegItem.调解记录_本次调解结果).getValue(ValueType.INT);
			}
		}
		
		return result;
	}
	
	/**
	 * 所有的履行记录中， 履行金额的和
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Integer getMoney(RecordComplexus recordComplexus, String recordCode) {
		Integer sum = 0;
		Collection<LeafRecord> leafs = recordComplexus.getRootRecord(recordCode).findLeafs(CaseMedRegItem.履行记录);
		for (LeafRecord leafRecord : leafs) {
			 Object sqlValue  = leafRecord.findAttribute(CaseMedRegItem.履行记录_履行金额).getValue(ValueType.INT);
			 sum += Integer.parseInt(sqlValue.toString());
		}
		return sum;
	}
	
}
