package com.zhsq.biz.FourTypePeople;

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

@Repository(value = "XFJDE1015")
public class FourTypePeopleFG implements FunctionalGroup, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {

	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return null;
	}

	@Override
	public ImproveResult preImprove(FGFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-FourTypePeople-ipm"));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImproveResult thirdImprove(FGFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
