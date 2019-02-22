package com.zhsq.biz.family.algorithm;

import java.util.Collection;

import com.abc.complexus.RecordComplexus;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.RelationType;
import com.zhsq.biz.people.algorithm.IDIntrospection;

public class DataIntrospection {
	
	
	/**
	 * 获取家庭中:家庭人口的数量
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Integer getFamilyPeoCount(RecordComplexus recordComplexus, String recordName, String recordCode) {
			Integer count = 0;
			Collection<RecordRelation> recordRelation = getRecordRelation(recordComplexus,recordName, recordCode);
			count = recordRelation.size();
		return count;
	}
	
	/**
	 * 获取家庭中户主的数量
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Integer getHzCount(RecordComplexus recordComplexus, String recordName, String recordCode) {
			Integer count = 0;
			Collection<RecordRelation> recordRelation = getRecordRelation(recordComplexus, recordName, recordCode);
			
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					if (RelationType.RR_家庭信息_户主_人口信息.equals(recordRelation2.getType())) {
						count++;
					}
				}
			}
		return count;
	}
	
	private static Collection<RecordRelation> getRecordRelation(RecordComplexus recordComplexus, String recordName, String recordCode) {
		RelationCorrelation relationCorrelation = IDIntrospection.getRelationCorrelation(recordComplexus, recordName, recordCode);
		if (relationCorrelation != null) {
			return relationCorrelation.getRecordRelation();
		}
		
		return null;
	}
	
}
