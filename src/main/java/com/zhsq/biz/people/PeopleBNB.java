package com.zhsq.biz.people;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.check.FuseCheckInfo;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.FunctionalGroup;
import com.abc.fuse.fg.FuseCheck;
import com.abc.fuse.fg.IdentityQuery;
import com.abc.fuse.fg.ImproveResult;
import com.abc.fuse.fg.OneRoundImprovement;
import com.abc.fuse.fg.ThreeRoundImprovement;
import com.abc.hc.HCFusionContext;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

@Repository(value = "XFJDE001")
public class PeopleBNB implements FuseCheck,FunctionalGroup, IdentityQuery,OneRoundImprovement, ThreeRoundImprovement, IFusitionCallBack {

	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		System.out.println("执行了PeopleBNB -- getCriteriaList");
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-people-idt-query"));
	}

	@Override
	public ImproveResult preImprove(FGFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		System.out.println("执行了PeopleBNB -- preImprove");
		return KIEHelper.getImproveResultFromKIE(context, recordCode, opsComplexus, recordComplexus,
				SessionFactory.findScannerSession("ks-people-preipm"));
	}

	@Override
	public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		System.out.println("执行了PeopleBNB -- improve");
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-people-ipm"));
	} 

	@Override
	public boolean afterFusition(String recordCode, HCFusionContext context) {

		return false;
	}

	@Override
	public ImproveResult postImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-people-postimp"));
	}

	@Override
	public ImproveResult secondImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		System.out.println("执行了PeopleBNB -- secondImprove");
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-people-secondipm"));
	}

	@Override
	public ImproveResult thirdImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		System.out.println("执行了PeopleBNB -- thirdImprove");
		return null;
	}

	@Override
	public FuseCheckInfo afterCheck(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		// TODO Auto-generated method stub
		return new FuseCheckInfo(recordCode);
	}

	@Override
	public FuseCheckInfo beforeCheck(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		// TODO Auto-generated method stub
		return new FuseCheckInfo(recordCode);
	}
	
}
