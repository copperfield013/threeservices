package com.zhsq.biz.timertask.people;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abc.record.query.RecordQueryPanel;
import com.abc.rrc.query.criteria.BetweenSymbol;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.constant.people.PeopleItem;
import com.zhsq.biz.people.PeopleFGTimer;
import com.zhsq.biz.timertask.LoadEntityToWorkMemory;

@Component
@Lazy(value=false)
public class PeopleTimeTask {
	/*	
	private String entityType = BaseConstant.TYPE_工作任务;*/
	//@Scheduled(cron = "0 0/3 * * * ?")//每3分钟执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void doSomething() {
		Collection<String> codes = getCodes();
		LoadEntityToWorkMemory.loadEntity(BaseConstant.TYPE_人口信息, codes, new PeopleFGTimer());
	}
	
	private Collection<String> getCodes() {
		Collection<String> codes = new ArrayList<String>();
		 List<List<Criteria>> criterias = getCriterias();
		for (int i =0; i<criterias.size(); i++) {
			Collection<String>	codeList = RecordQueryPanel.query(criterias.get(i));
			codes.addAll(codeList);
		}
		
		return codes;
	}
	 
	private List<List<Criteria>> getCriterias() {
		List<List<Criteria>> list = new ArrayList<List<Criteria>>();
		LocalDate localDate = LocalDate.now();
		LocalDate date16 = localDate.plusYears(-16);//今天刚满16岁的 添加育龄妇女标签
		LocalDate date51 = localDate.plusYears(-51);//今天刚满51岁的删除育龄妇女标签
		LocalDate date59_ = localDate.plusYears(-60).plusMonths(3);//还差三个月才满60岁的
		LocalDate date60 = localDate.plusYears(-60);//今天刚满60岁的
		LocalDate date70 = localDate.plusYears(-70);//今天刚满70岁的
		LocalDate date80 = localDate.plusYears(-80);//今天刚满80岁的
		LocalDate date90 = localDate.plusYears(-90);//今天刚满90岁的
		
		list.add(buildCriteria(localDate));
		list.add(buildCriteria(date16));
		list.add(buildCriteria(date51));
		list.add(buildCriteria(date60));
		list.add(buildCriteria(date70));
		list.add(buildCriteria(date80));
		list.add(buildCriteria(date90));
		return list;
	}
	
	private List<Criteria> buildCriteria(LocalDate localDate){
		BizzCriteriaFactory criteriaFactory = new BizzCriteriaFactory(BaseConstant.TYPE_人口信息);
		criteriaFactory.addBetweenCriteria(PeopleItem.出生日期, localDate.toString(), localDate.toString(), BetweenSymbol.BETWEEN);
		List<Criteria> criterias = criteriaFactory.getCriterias();//今天刚出生的
		return criterias; 
}
	

}
