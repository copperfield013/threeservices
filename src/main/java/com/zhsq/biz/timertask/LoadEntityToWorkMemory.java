package com.zhsq.biz.timertask;

import java.util.Collection;

import com.abc.auth.constant.AuthConstant;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.FunctionalGroup;
import com.abc.hc.FusionContext;
import com.abc.hc.HCFusionContext;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;

public class LoadEntityToWorkMemory {
	public static void loadEntity(String entityType, Collection<String>  codes, FunctionalGroup functionalGroup) {
		Integration integration=PanelFactory.getIntegration();
		HCFusionContext context = new HCFusionContext();
		context.putFunctionalGroup(entityType, functionalGroup);
		context.setUserCode(AuthConstant.SUPERCODE);
		context.setSource(FusionContext.SOURCE_COMMON);
		if(codes!=null) {
			for(String code :codes) {
				integration.integrate(context,code);
			}
		}
	}
	
}
