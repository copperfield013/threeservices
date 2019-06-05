package com.zhsq.test.people;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.abc.record.query.RecordQueryPanel;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.record.RootRecord;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.timertask.people.PeopleTimeTask;
import com.zhsq.test.biz.CommFusion;

import antlr.collections.List;


/**
 * 	残障补贴测试
 * @author so-well
 *
 */
@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DisabilityPeopleTest {
	
	private static Logger logger = Logger.getLogger(DisabilityPeopleTest.class);
	protected String mapperName = "人口信息";
	
	@Test
	public void readData1() {
			//获取初始实体
			Entity entity=initEntity(mapperName);
			//融合结果实体
			Entity result = CommFusion.getInstance().fusion(mapperName, entity);
			
			java.util.List<LeafEntity> multiAttrEntity = result.getMultiAttrEntity("可享受的补贴项目");
			assertNotEquals(null, multiAttrEntity);
			
			for (LeafEntity leafEntity : multiAttrEntity) {
				String stringValue = leafEntity.getStringValue("补贴项目");
				assertEquals("残障补贴", stringValue);
			}
			
			//修改实体
			result.putValue("是否残疾", EnumKeyValue.ENUM_是否_否);
			//融合结果实体
			Entity result2 = CommFusion.getInstance().fusion(mapperName, result);
			java.util.List<LeafEntity> multiAttrEntity2 = result2.getMultiAttrEntity("可享受的补贴项目");
			assertEquals(null, multiAttrEntity2);
	}
	
	@Test
	public void readData2() {
			//获取初始实体
			Entity entity=initEntity(mapperName);
			//融合结果实体
			Entity result = CommFusion.getInstance().fusion(mapperName, entity);
			
			java.util.List<LeafEntity> multiAttrEntity = result.getMultiAttrEntity("可享受的补贴项目");
			assertNotEquals(null, multiAttrEntity);
			
			for (LeafEntity leafEntity : multiAttrEntity) {
				String stringValue = leafEntity.getStringValue("补贴项目");
				assertEquals("残障补贴", stringValue);
			}
			
			//修改实体
			LeafEntity sentity2 = new LeafEntity("已享受的补贴项目");
			sentity2.putValue("补贴项目", EnumKeyValue.ENUM_补贴项目_残障补贴);
			result.putMultiAttrEntity(sentity2);
			//融合结果 实体
			Entity result2 = CommFusion.getInstance().fusion(mapperName, result);
			java.util.List<LeafEntity> multiAttrEntity2 = result2.getMultiAttrEntity("可享受的补贴项目");
			assertEquals(null, multiAttrEntity2);
	}
	
	//生成实体
	private Entity initEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("姓名", "TEST人口" +new Random().nextInt(1000)); 
		entity.putValue("人口类型", "户籍人口");
		entity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		entity.putValue("身份证号码", "123" + new Random().nextInt(1000));
		entity.putValue("是否残疾", EnumKeyValue.ENUM_是否_是);
		
		logger.debug("初始实体： " + entity.toJson());
		return entity;
	}
	
}
