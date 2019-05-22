package com.zhsq.biz.common;

import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class SessionFactory {
	private static Logger logger = Logger.getLogger(SessionFactory.class);
	
	//findKeepSession
	public static KieSession findScannerSession(String sessionName) {
		//KieSession kSession = KieSessionFactory.findKeepSession(sessionName);
		
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kSession = kieContainer.newKieSession(sessionName);
		
		kSession.addEventListener(new DebugAgendaEventListener() {
            public void matchCreated(MatchCreatedEvent event) {
            	
            	logger.debug("The rule 【"
                        + event.getMatch().getRule().getName()
                        + "】 匹配成功！");
            }
            public void matchCancelled(MatchCancelledEvent event) {
            	logger.debug("The rule 【"
                        + event.getMatch().getRule().getName()
                        + "】 取消匹配");
            }
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
/*            	logger.debug("The rule "
                        + event.getMatch().getRule().getName()
                        + "  将被解雇");
*/            }
            public void afterMatchFired(AfterMatchFiredEvent event) {
            	logger.debug("The rule 【"
                        + event.getMatch().getRule().getName()
                        + "】 执行成功！！");
            }
        });
		
		return kSession;
	}
	
	//findScannerSession
	/*public static KieSession  findKeepSession(String sessionName){
		KieSession kSession = KieSessionFactory.newScannerSession("com.zhsq.biz", "threeservices", "LATEST", sessionName);
		kSession.addEventListener( new DefaultAgendaEventListener() {
			public void afterMatchFired(AfterMatchFiredEvent event) {
				super.afterMatchFired( event );
					System.out.println( event.getMatch().getRule().getName()+" is fired" );
				}
			});
		
		return kSession;
	}*/
	
}
