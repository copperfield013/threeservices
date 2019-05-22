package com.zhsq.test.biz;


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
import com.abc.mapping.entity.LeafEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.constant.casemedreg.CaseMedRegItem;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CaseMedRegTest {
	
	private static Logger logger = Logger.getLogger(CaseMedRegTest.class);
	protected String mapperName = "案件调解登记";

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
		entity.putValue("唯一编码", "5ac55ce001b94b6789af496a590804ae");
		entity.putValue("登记日期", "2019-03-08"); 
		
		LeafEntity sentity = new LeafEntity("调解记录");
		sentity.putValue("调解日期", "2019-3-28");
		sentity.putValue("本次调解结果", EnumKeyValue.ENUM_调解结果_达成书面协议);
		entity.putMultiAttrEntity(sentity);
		
		LeafEntity sentity1 = new LeafEntity("调解记录");
		sentity1.putValue("调解日期", "2019-3-19");
		sentity1.putValue("本次调解结果", EnumKeyValue.ENUM_调解结果_达成口头协议);
		entity.putMultiAttrEntity(sentity1);
		
		LeafEntity sentity2 = new LeafEntity("调解记录");
		sentity2.putValue("调解日期", "2019-3-10");
		sentity2.putValue("本次调解结果", EnumKeyValue.ENUM_调解结果_调解不成功);
		entity.putMultiAttrEntity(sentity2);
		
		return entity;
	}
	

}