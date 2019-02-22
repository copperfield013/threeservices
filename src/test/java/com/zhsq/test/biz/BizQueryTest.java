package com.zhsq.test.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.RelationEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.PanelFactory;
import com.abc.rrc.query.entity.EntitySortedPagedQuery;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.query.queryrecord.criteria.CriteriaFactory;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BizQueryTest {
	private static Logger logger = Logger
			.getLogger(BizQueryTest.class);	
	@Test
	public void select() {
		
		/*List<String> codes=RecordFactory.query(buildCriteria());
		Integration integration=PanelFactory.getIntegration();
		BizFusionContext context = new BizFusionContext();
		context.putBizMap(BaseConstant.TYPE_工作任务, new WorkTaskBNB());
		context.setUserCode(AuthConstant.SUPERCODE);
		context.setSource(FusionContext.SOURCE_COMMON);
		if(codes!=null) {
			logger.debug("总数："+codes.size());
			for(String code :codes) {
				String tempCode=integration.integrate(code, context);
				RecordCompound recordCompound=RecordFactory.get(tempCode);
				logger.debug(recordCompound.getRecord().toJson());
			}
		}*/
	}
	
	public List<Criteria> buildCriteria(String recordCode){

		List<Criteria> criterias = new ArrayList<Criteria>();
		Criteria common;
		common =CriteriaFactory.createLikeCriteria(recordCode,"SW0208","刘志华");//姓名
		criterias.add(common);
		return criterias; 
	} 

	public void select(List<Criteria> criterias, String colName,BizFusionContext context) {
		long startTime = System.currentTimeMillis();
		try {
			
			Discoverer discoverer = PanelFactory.getDiscoverer(context);

			EntitySortedPagedQuery sortedPagedQuery = discoverer.discover(
					criterias, colName);
			sortedPagedQuery.setPageSize(5);

			for (int i = 1; i < 3; i++) {
				logger.debug("第" + i + "页,共" + sortedPagedQuery.getAllCount()
						+ "条数据,每页" + sortedPagedQuery.getPageSize() + "条");
				// abcNode.selectAliasAsTitle();
				for (Object obj : sortedPagedQuery.visit(i)) {
					Entity entity=(Entity)obj;
					// people.addMapping(abcNode);
					for(Object name:entity.getRelationNames()){
						List<RelationEntity> rel=entity.getRelations((String)name);
						for(RelationEntity re:rel){
							logger.debug(re.getFullName()+"-e:"+re.getFullName());
							logger.debug(re.getFullName()+"-e:"+re.getEntity().getFullName());
						}
						
					}
					logger.debug(entity.toJson());
					
				}
			}
			long endTime = System.currentTimeMillis();// 记录结束时间
			logger.debug((float) (endTime - startTime) / 1000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}

