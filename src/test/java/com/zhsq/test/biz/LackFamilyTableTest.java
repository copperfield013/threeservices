package com.zhsq.test.biz;


import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.drools.core.metadata.With;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.hc.FusionContext;
import com.abc.hc.HCFusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.zhsq.biz.people.algorithm.BirthdayIntrospection;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class LackFamilyTableTest {
	
	private static Logger logger = Logger.getLogger(LackFamilyTableTest.class);
	protected String mapperName = "低边家庭各类补贴发放表";

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
    	HCFusionContext context=new HCFusionContext();
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
		entity.putValue("唯一编码", "e54e44fd0c58472387dbeba8dc8fedb1");
		entity.putValue("事项名称", "->->最低生活保障边缘家庭（低边证）->低保边缘户"); 
		entity.putValue("发放年月", "2019-3-24"); 
		entity.putValue("银行打卡金额", "78"); 
		
		Entity relationentity = new Entity("申请人");
		//relationentity.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888d5");
		relationentity.putValue("姓名", "eeee");
		relationentity.putValue("身份证号码", "1234567");
		entity.putRelationEntity("申请人","申请人", relationentity);
		
		
		
		
		/*Entity relationentity2 = new Entity("属于组织");
		//relationentity.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888d5");
		relationentity2.putValue("名称", "4444");
		entity.putRelationEntity("属于组织","属于组织", relationentity2);*/
		
		/*Entity relationentity1 = new Entity("用户");
		relationentity1.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888u5");
		relationentity1.putValue("用户名", "admin");
		entity.putRelationEntity("任务创建人","创建人", relationentity1);*/
		return entity;
	}
}