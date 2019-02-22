package com.zhsq.biz.family;

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

@Repository(value = "XFJDE305")
public class FamilyBNB implements BizNoBusy, IdentityQuery, Improvement, IFusitionCallBack {
	
	/**
	 * 只有关系改变时， 
	 * true: 关系改变执行融合
	 * false: 关系改变也不执行融合
	 */
	/*@Override
	public boolean improveOnlyCorrelativeRelation() {
		return Improvement.super.improveOnlyCorrelativeRelation();
	}*/
	
	/**
	 * true: 只要为true， 任何情况都融合
	 * false: 任何情况都不融合
	 */
	/*@Override
	public boolean improveEveryTime() {
		// TODO Auto-generated method stub
		return Improvement.super.improveEveryTime();
	}*/
	
	/**
	 * 需要的时候去融合， 方法内部为判断什么情况为需要
	 */
	/*@Override
	public boolean needImprove(String arg0, OpsComplexus arg1) {
		// TODO Auto-generated method stub
		return Improvement.super.needImprove(arg0, arg1);
	}*/
	
	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-family-idt-query"));
	}

	@Override
	public ImproveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, opsComplexus, recordComplexus,
				SessionFactory.findScannerSession("ks-family-preipm"));
	}

	@Override
	public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-family-ipm"));
	} 

	@Override
	public boolean afterFusition(String recordCode, BizFusionContext context) {

		return false;
	}
	
	//第三步， 可以把检查错误放进这里
	@Override
	public ImproveResult postImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-family-postimp"));
	}

	@Override
	public ImproveResult secondImprove(BizFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
