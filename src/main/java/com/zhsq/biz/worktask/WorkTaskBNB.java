package com.zhsq.biz.worktask;

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

@Repository(value = "XFJDE379")
public class WorkTaskBNB implements BizNoBusy, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {
	
	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-worktask-idt-query"));
	
	}

	@Override
	public ImproveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, opsComplexus, recordComplexus,
				SessionFactory.findScannerSession("ks-worktask-preipm"));
	}

	@Override
	public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-worktask-ipm"));
	} 

	@Override
	public boolean afterFusition(String recordCode, BizFusionContext context) {

		return false;
	}

	@Override
	public ImproveResult postImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-worktask-postipm"));
	}

	@Override
	public ImproveResult secondImprove(BizFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImproveResult thirdImprove(BizFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
