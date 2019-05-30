package com.zhsq.biz.worktask;

import com.abc.complexus.RecordComplexus;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.ImproveResult;
import com.abc.ops.complexus.OpsComplexus;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

public class WorkTaskFGTimer extends WorkTaskFG {

	@Override
	public ImproveResult preImprove(FGFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-worktask-ipm-ipmTimer"));
	} 

	@Override
	public ImproveResult postImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}
}
