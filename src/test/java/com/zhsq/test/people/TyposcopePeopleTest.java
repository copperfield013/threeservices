package com.zhsq.test.people;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

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


/**
 * 	助视器测试
 * @author so-well
 *
 */
@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TyposcopePeopleTest {
	
	private static Logger logger = Logger.getLogger(TyposcopePeopleTest.class);
	protected String mapperName = "人口信息";
	
	@Test
	public void readData1() {
			//获取初始实体
			Entity entity=initEntity(mapperName);
			//融合后 实体
			Entity result = CommFusion.getInstance().fusion(mapperName, entity);
			
			java.util.List<LeafEntity> multiAttrEntity = result.getMultiAttrEntity("可享受的补贴项目");
			
			assertNotEquals(null, multiAttrEntity);
			
			//验证补贴项目中是否含有助视器
			List<String> leafchilName = new ArrayList<String>();
			for (LeafEntity leafEntity : multiAttrEntity) {
				String stringValue = leafEntity.getStringValue("补贴项目");
				leafchilName.add(stringValue);
			}
			assertTrue(leafchilName.contains("助视器"));
			
			//修改实体
			result.removeAllMultiAttrEntity("残疾信息");
			//融合结果实体
			Entity result2 = CommFusion.getInstance().fusion(mapperName, result);
			java.util.List<LeafEntity> multiAttrEntity2 = result2.getMultiAttrEntity("可享受的补贴项目");
			
			if(multiAttrEntity2!=null) {
				//验证补贴项目中是否含有助视器
				List<String> leafchi = new ArrayList<String>();
				for (LeafEntity leafEntity : multiAttrEntity2) {
					String stringValue = leafEntity.getStringValue("补贴项目");
					leafchi.add(stringValue);
				}
				assertFalse(leafchi.contains("助视器"));
			}
	}
	
	//生成实体
	private Entity initEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("姓名", "TEST人口555" +new Random().nextInt(1000)); 
		entity.putValue("人口类型", "户籍人口");
		entity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		entity.putValue("身份证号码", "1244443" + new Random().nextInt(1000));
		entity.putValue("救助圈等级", EnumKeyValue.ENUM_救助圈等级_1级);
		
		LeafEntity sentity2 = new LeafEntity("残疾信息");
		sentity2.putValue("残疾类别", EnumKeyValue.ENUM_残疾类别_视力);
		sentity2.putValue("残疾等级", EnumKeyValue.ENUM_残疾等级_一级);
		entity.putMultiAttrEntity(sentity2);
		
		logger.debug("初始实体： " + entity.toJson());
		return entity;
	}
	
}
