package com.zhsq.biz.minzsalvation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.improve.ImproveResult;
import com.abc.fuse.improve.ThreeRoundImprovement;
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
public class MinZSalvationBNB implements BizNoBusy, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {
	
	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-minzsalvation-idt-query"));
	}

	@Override
	public ImproveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	} 

	@Override
	public boolean afterFusition(String recordCode, BizFusionContext context) {

		return false;
	}

	@Override
	public ImproveResult postImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult secondImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult thirdImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}
}
