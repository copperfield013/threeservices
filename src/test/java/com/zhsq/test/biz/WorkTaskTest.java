package com.zhsq.test.biz;


import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkTaskTest {
	
	private static Logger logger = Logger.getLogger(WorkTaskTest.class);
	protected String mapperName = "工作任务";

   @Before
    public void setUp() throws Exception {
        System.out.println("------------Before------------");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("------------After ------------");
    }
	
    @Test
	public void readData() {

    	long startTime = System.currentTimeMillis();
		BizFusionContext context=new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
//		context.setToEntityRange(BizFusionContext.ENTITY_CONTENT_RANGE_ABCNODE_CONTAIN);
		context.setUserCode("e10adc3949ba59abbe56e057f28888d5");
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		IntegrationMsg imsg=integration.integrate(context,entity);
		String code=imsg.getCode();
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		logger.debug(code + " : "+ result.toJson());
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("唯一编码", "120fc092edbe4f3884caab1c76c20870");
		entity.putValue("任务标题", "第99个任务"); 
		entity.putValue("任务状态", "处理中");
		entity.putValue("任务结束时间", "2019-02-17");
		entity.putValue("任务是否超时", "否");
		entity.putValue("保存派发", "派发");
		entity.putValue("类型", "公安");
		entity.putValue("是否需要领用", "是");
		entity.putValue("是否智能匹配", "否");
		
		
		//entity.removeAllRelationEntity("任务执行人");
		
		/*Entity relationentity = new Entity("任务执行人");
		relationentity.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888d5");
		relationentity.putValue("用户名", "admin");
		entity.putRelationEntity("任务执行人","任务执行人", relationentity);
		*/
		/*Entity relationentity1 = new Entity("用户");
		relationentity1.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888u5");
		relationentity1.putValue("用户名", "admin");
		entity.putRelationEntity("任务创建人","创建人", relationentity1);*/
		return entity;
	}
	
	
	@Test
	public void fun() {
		//new WorkTaskTime().doSomething();
		
		
		//Collection<String> org = TaskIntrospection.getOrg("e10adc3949ba59abbe56e057f28888d5");
	}

}