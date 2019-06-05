package com.zhsq.test.biz;

import org.apache.log4j.Logger;

import com.abc.hc.FusionContext;
import com.abc.hc.HCFusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;

public class CommFusion {
	private static Logger logger = Logger.getLogger(CommFusion.class);
	
	public static CommFusion getInstance() {
		return new CommFusion();
	}

	public Entity fusion(String mapperName, Entity entity) {
		long startTime = System.currentTimeMillis();
		
		HCFusionContext context=new HCFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
		context.setMappingName(mapperName);
		context.setUserCode("e10adc3949ba59abbe56e057f28888d5");
		
		// 获取融合器
		Integration integration=PanelFactory.getIntegration();
		//执行融合
		IntegrationMsg imsg=integration.integrate(context,entity);
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug("执行融合所用时间： " + (float) (endTime - startTime) / 1000 + "秒");
		
		String code=imsg.getCode();
		//获取查询器
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		// 获取执行后的实体
		Entity result=discoverer.discover(code);
		return result;
	}
	
}
