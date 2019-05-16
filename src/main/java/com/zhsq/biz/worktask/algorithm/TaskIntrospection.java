package com.zhsq.biz.worktask.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.abc.record.query.RecordQueryPanel;
import com.abc.rrc.query.criteria.BetweenSymbol;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.query.criteria.CommonSymbol;
import com.abc.rrc.query.criteria.IncludeSymbol;
import com.abc.rrc.query.criteria.NullSymbol;
import com.abc.rrc.query.criteria.UnRecursionRelationCriteriaFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.constant.RelationType;
import com.zhsq.biz.constant.user.UserItem;
import com.zhsq.biz.constant.worktask.WorkTaskItem;

public class TaskIntrospection {
	
	/**
	 * 
	 * @param time 任务结束时间和当前时间进行比较
	 * @return  true 已超时  false： 未超时
	 * @throws ParseException 
	 */
	public static boolean inspectTimeOut(String time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = sdf.parse(time);
		Date date = new Date();
		
		if (parse.getTime() <date.getTime()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取所有用户
	 */
	public static Collection<String> getUserCode() {
		Collection<String> codes = new ArrayList<String>();
		
		/*BizzCriteriaFactory criteriaFactory = new BizzCriteriaFactory(BaseConstant.TYPE_用户);
		criteriaFactory.addNullCriteria(arg0, NullSymbol.ISNULL);
		List<Criteria> criterias = criteriaFactory.getCriterias();
		
		for (int i =0; i<criterias.size(); i++) {
			Collection<String>	codeList = RecordQueryPanel.query(criterias.get(i));
			codes.addAll(codeList);
		}*/
		
		return codes;
	}
	
	
	/**
	 * 根据任务问题类型， 自动匹配任务执行人
	 */
	public static Collection<String> taskMatchingUser(Integer pTypeValue) {
		Collection<String> codes = new ArrayList<String>();
		List<Criteria> criterias = getCriterias(pTypeValue);
		
		for (int i =0; i<criterias.size(); i++) {
			Collection<String>	codeList = RecordQueryPanel.query(criterias.get(i));
			codes.addAll(codeList);
		}
		
		return codes;
	}
	
	private static List<Criteria> getCriterias(Integer pTypeValue) {
		Set<Object> set = new HashSet<Object>();
		set.add(pTypeValue);
		BizzCriteriaFactory criteriaFactory = new BizzCriteriaFactory(BaseConstant.TYPE_用户);
		criteriaFactory.addIncludeCriteria(UserItem.任务类型, set, IncludeSymbol.INCLUDES);
		List<Criteria> criterias = criteriaFactory.getCriterias();
		return criterias; 
	}
	
	/**
	 * 根据用户的code， 通过关系获取组织的code
	 * @param userCode
	 * @return
	 */
	public static Collection<String> getOrg(String userCode) {
		BizzCriteriaFactory criteriaFactory = new BizzCriteriaFactory(BaseConstant.TYPE_组织);
		UnRecursionRelationCriteriaFactory unRecursionRelationCriteriaFactory=UnRecursionRelationCriteriaFactory.getInstance(BaseConstant.TYPE_组织);
		unRecursionRelationCriteriaFactory.addIncludeRTypeCode(RelationType.RR_组织_拥有用户_用户);
		unRecursionRelationCriteriaFactory.addRightCode(userCode);
		BizzCriteriaFactory addRelationCriteria = criteriaFactory.addRelationCriteria(unRecursionRelationCriteriaFactory.getRelationCriteria());
		Collection<String>	codeList = RecordQueryPanel.query(addRelationCriteria.getCriterias());
		return codeList;
	}
	
}
