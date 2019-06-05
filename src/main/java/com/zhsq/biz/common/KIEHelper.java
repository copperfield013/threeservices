package com.zhsq.biz.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kie.api.runtime.KieSession;

import com.abc.complexus.RecordComplexus;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.ImproveResult;
import com.abc.fuse.improve.attribute.FuseAttribute;
import com.abc.fuse.improve.attribute.leaf.FuseLeafAttribute;
import com.abc.fuse.improve.ops.builder.RootRecordBizzOpsBuilder;
import com.abc.fuse.improve.transfer.BizzAttributeTransfer;
import com.abc.ops.builder.RecordRelationOpsBuilder;

import com.abc.ops.complexus.OpsComplexus;
import com.abc.relation.RelationCorrelation;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.record.RootRecord;
import com.abc.rrc.record.Attribute;

public class KIEHelper {
	
	private static Logger logger = Logger.getLogger(KIEHelper.class);
	
	public static List<Criteria> getBizCriteriaListFromKIE(String recordCode, RecordComplexus complexus,
			KieSession kSession) {
		RootRecord record = complexus.getRootRecord(recordCode);
		
		List<FuseAttribute> transfer = BizzAttributeTransfer.transfer(record);
		
		BizzAttributeTransfer.transfer(record).forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		kSession.setGlobal("recordName", record.getName());
		
		BizzCriteriaFactory bizzCriteriaFactory =null;
		try {
			bizzCriteriaFactory = new BizzCriteriaFactory(record.getName());
			kSession.setGlobal("bizzCriteriaFactory", bizzCriteriaFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 触发规则
		logger.debug("开始执行规则===================== ");
		int fireAllRules = kSession.fireAllRules();
		logger.debug("本次触发规则数量 =  " + fireAllRules);
		logger.debug("规则执行完毕===================== ");
		List<Criteria> criteriaList = bizzCriteriaFactory.getCriterias();
		
		/*QueryResults results = kSession.getQueryResults("query criteria");

		for (QueryResultsRow row : results) {
			criteriaList.add((Criteria) row.get("criteria"));
		}*/
		
		kSession.destroy();
		return criteriaList;
	}

	public static ImproveResult getImproveResultFromKIE(FGFusionContext fgFusionContext, String recordCode,
			OpsComplexus opsComplexus, RecordComplexus recordComplexus, KieSession kSession) {
		
		String userCode = fgFusionContext.getUserCode();
		
		RootRecord rootRecord = recordComplexus.getRootRecord(recordCode);
		String recordName = rootRecord.getName();
		String hostCode = recordComplexus.getHostCode();
		String hostType = recordComplexus.getHostType();
		// 定义 全局变量
		
		List<RootRecord> rootRecordList = new ArrayList<RootRecord>();
		List<Integer> addedLabelList = new ArrayList<Integer>();
		List<Integer> removedLabelList = new ArrayList<Integer>();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		List<FuseLeafAttribute> addedLeafAttrList = new ArrayList<FuseLeafAttribute>();
		Map<String, String> removedLeafAttrMap = new HashMap<String, String>();
		
		//存放新建
		List<RecordRelationOpsBuilder> recordRelationOpsBuilderNew = new ArrayList<RecordRelationOpsBuilder>();
		
		RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordName,
				recordCode);

		try {
			kSession.setGlobal("userCode", userCode);
			kSession.setGlobal("recordCode", recordCode);
			kSession.setGlobal("recordName", recordName);
			kSession.setGlobal("recordComplexus", recordComplexus);
			kSession.setGlobal("recordRelationOpsBuilder", recordRelationOpsBuilder);
			
			kSession.setGlobal("recordRelationOpsBuilderNew", recordRelationOpsBuilderNew);
		} catch (Exception e) {
			logger.debug("全局变量未设置： recordRelationOpsBuilderNew");
		}
		try {
			kSession.setGlobal("rootRecordList", rootRecordList);
		} catch (Exception e) {
			logger.debug("全局变量未设置： rootRecordList");
		}
		try {
			kSession.setGlobal("hostCode", hostCode);
		} catch (Exception e) {
			logger.debug("全局变量未设置： hostCode");
		}
		try {
			kSession.setGlobal("hostType", hostType);
		} catch (Exception e) {
			logger.debug("全局变量未设置： hostType");
		}
		try {
			kSession.setGlobal("addedLabelList", addedLabelList);
		} catch (Exception e) {
			logger.debug("全局变量未设置： addedLabelList");
		}
		try {
			kSession.setGlobal("removedLabelList", removedLabelList);
		} catch (Exception e) {
			logger.debug("全局变量未设置： removedLabelList");
		}
		try {
			kSession.setGlobal("attributeList", attributeList);
		} catch (Exception e) {
			logger.debug("全局变量未设置： attributeList");
		}
		try {
			kSession.setGlobal("addedLeafAttrList", addedLeafAttrList);
		} catch (Exception e) {
			logger.debug("全局变量未设置： addedLeafAttrList");
		}
		try {
			kSession.setGlobal("removedLeafAttrMap", removedLeafAttrMap);
		} catch (Exception e) {
			logger.debug("全局变量未设置： removedLeafAttrMap");
		}
		try {
			kSession.setGlobal("rootRecord", rootRecord);
		} catch (Exception e) {
			logger.debug("全局变量未设置： rootRecord");
		}
		try {
			kSession.setGlobal("recordComplexus", recordComplexus);
		} catch (Exception e) {
			logger.debug("全局变量未设置： recordComplexus");
		}
		
		// insert object
		BizzAttributeTransfer.transfer(rootRecord).forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
	
		if (relationCorrelation != null) {
			relationCorrelation.getRecordRelation().forEach(recordRelation -> kSession.insert(recordRelation));
		}
		
		if (opsComplexus != null) {
			if (opsComplexus.getRootRecordOps(recordCode) != null) {
				BizzAttributeTransfer.transfer(opsComplexus.getRootRecordOps(recordCode))
						.forEach(opsAttr -> kSession.insert(opsAttr));
			}

			if (opsComplexus.getRecordRelationOps(recordCode) != null) {
				BizzAttributeTransfer.transfer(opsComplexus.getRecordRelationOps(recordCode))
						.forEach(opsRelation -> kSession.insert(opsRelation));
			}

		}

		// 触发规则
		logger.debug(  "开始执行规则===================== ");
		
		int fireAllRules = kSession.fireAllRules();
		logger.debug("本次触发规则数量 =  " + fireAllRules);
		logger.debug("规则执行完毕===================== ");
		kSession.destroy();

		// 组装结果
		RootRecordBizzOpsBuilder rootRecordOpsBuilder = RootRecordBizzOpsBuilder.getInstance(recordName, recordCode);
		
		rootRecordOpsBuilder.removeLabel(removedLabelList);
		rootRecordOpsBuilder.addLabel(addedLabelList);
		rootRecordOpsBuilder.addAttribute(attributeList);
		
		rootRecordOpsBuilder.addLeafAttribute(addedLeafAttrList);
		// 删除的多值属性
		for (String key : removedLeafAttrMap.keySet()) {
			rootRecordOpsBuilder.removeLeaf(removedLeafAttrMap.get(key), key);
		}
		
		ImproveResult imprveResult = new ImproveResult();
		imprveResult.setRootRecordOps(rootRecordOpsBuilder.getRootRecordOps());
		imprveResult.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());
		imprveResult.setGeneratedRecords(rootRecordList);
		
		for (RecordRelationOpsBuilder builder : recordRelationOpsBuilderNew) {
			imprveResult.putDerivedRecordRelationOps(builder.getRecordRelationOps());
		}
		
		return imprveResult;
	}

	public static ImproveResult getImproveResultFromKIE(FGFusionContext context, String recordCode,
			RecordComplexus recordComplexus, KieSession kSession) {
		return getImproveResultFromKIE(context, recordCode, null, recordComplexus, kSession);
	}

}
