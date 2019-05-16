package com.zhsq.biz.minzsalvation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.FunctionalGroup;
import com.abc.fuse.fg.IdentityQuery;
import com.abc.fuse.fg.ImproveResult;
import com.abc.fuse.fg.ThreeRoundImprovement;
import com.abc.hc.HCFusionContext;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;
/**
 * 民政帮扶数据
 * @author so-well
 *
 */
@Repository(value = "XFJDE1691")
public class MinZSalvationBNB implements FunctionalGroup, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {
	
	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-minzsalvation-idt-query"));
	}

	@Override
	public ImproveResult preImprove(FGFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	} 

	@Override
	public boolean afterFusition(String recordCode, HCFusionContext context) {

		return false;
	}

	@Override
	public ImproveResult postImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult secondImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult thirdImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}
}
