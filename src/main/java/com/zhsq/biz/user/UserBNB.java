package  com.zhsq.biz.user;

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



@Repository(value = "ABCBE002")
public class UserBNB implements FunctionalGroup, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {

	@Override
	public boolean afterFusition(String code, HCFusionContext context) {

		// 给没有基本权限的用户增加基本权限
		// 1.查询是否以及有基本权限了

		// RelationCriteriaFactory.getInstance().addLeftCode(code).addRightCode(AuthConstant.CODE_AUTH_BASIC).addRTypeCode(arg0);

		// 2.若没有，增加基本权限

		return true;
	}

	@Override
	public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-user-ipm"));
	}
	
	@Override
	public ImproveResult secondImprove(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findScannerSession("ks-user-twoipm"));
	}

	@Override
	public ImproveResult postImprove(FGFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImproveResult preImprove(FGFusionContext arg0, String arg1, OpsComplexus arg2, RecordComplexus arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {

		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findScannerSession("ks-user-idt-query"));

	}

	@Override
	public ImproveResult thirdImprove(FGFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
