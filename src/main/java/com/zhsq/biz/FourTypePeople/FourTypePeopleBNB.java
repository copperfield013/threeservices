package com.zhsq.biz.FourTypePeople;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.improve.ImproveResult;
import com.abc.fuse.improve.Improvement;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

@Repository(value = "XFJDE1015")
public class FourTypePeopleBNB implements BizNoBusy, IdentityQuery, Improvement, IFusitionCallBack {

	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return null;
	}

	@Override
	public ImproveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-FourTypePeople-ipm"));
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
		// TODO Auto-generated method stub
		return null;
	}
	

}
