package com.zhsq.biz.user.algorithm;

import java.util.Collection;

import com.abc.complexus.RecordComplexus;
import com.abc.model.enun.AttributeValueType;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.abc.rrc.record.Attribute;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.CommonAlgorithm;
import com.zhsq.biz.constant.RelationType;
import com.zhsq.biz.constant.org.OrgItem;
import com.zhsq.biz.people.algorithm.IDIntrospection;

public class UserIntrospection {
	
	//获取用户和组织的指定关系， 把组织的名称拼接字符串返回
	public static String getOrgName(RecordComplexus recordComplexus,String recordName, String recordCode) {
		
		StringBuffer sb = new StringBuffer(30);
		
		RelationCorrelation relationCorrelation = null;
		
		relationCorrelation = CommonAlgorithm.getRelationCorrelation(recordComplexus, recordName, recordCode);
		
		if (relationCorrelation !=null) {
			Collection<RecordRelation> recordRelation = relationCorrelation.getRecordRelation();
			if (!recordRelation.isEmpty()) {
				for (RecordRelation recordRelation2 : recordRelation) {
					if (RelationType.RR_用户_属于组织_组织.equals(recordRelation2.getType())) {
						RootRecord rootRecord = recordComplexus.getRootRecord(recordRelation2.getRight());
						if (rootRecord != null) {
							Attribute findAttribute = rootRecord.findAttribute(OrgItem.名称);
							if (findAttribute != null) {
								sb.append(findAttribute.getValue(AttributeValueType.STRING) + " ");
							}
						}
					}
				}
			}
		}
		
		return sb.toString();
	}
}

