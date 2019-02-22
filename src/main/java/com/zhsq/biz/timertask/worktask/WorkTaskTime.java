package com.zhsq.biz.timertask.worktask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abc.rrc.query.RecordQueryPanel;
import com.abc.rrc.query.criteria.BetweenSymbol;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.constant.worktask.WorkTaskItem;
import com.zhsq.biz.timertask.LoadEntityToWorkMemory;
import com.zhsq.biz.worktask.WorkTaskBNBTimer;

@Component
@Lazy(value=false)
public class WorkTaskTime {
/*	
	private String entityType = BaseConstant.TYPE_工作任务;*/
	 //@Scheduled(cron = "0 0/5 * * * ?")//每5分钟执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void doSomething() {
		 
		 Collection<String> codes = getCodes();
		 LoadEntityToWorkMemory.loadEntity(BaseConstant.TYPE_工作任务, codes, new WorkTaskBNBTimer());
	}
	
	private Collection<String> getCodes() {
		Collection<String> codes = new ArrayList<String>();
		List<Criteria> criterias = getCriterias();
		for (int i =0; i<criterias.size(); i++) {
			Collection<String>	codeList = RecordQueryPanel.query(criterias.get(i));
			codes.addAll(codeList);
		}
		
		return codes;
	}
	
	private List<Criteria> getCriterias() {
		BizzCriteriaFactory criteriaFactory = new BizzCriteriaFactory(BaseConstant.TYPE_工作任务);
		criteriaFactory.addBetweenCriteria(WorkTaskItem.任务结束时间, null, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), BetweenSymbol.BETWEEN);
		List<Criteria> criterias = criteriaFactory.getCriterias();
		return criterias; 
	}

}
