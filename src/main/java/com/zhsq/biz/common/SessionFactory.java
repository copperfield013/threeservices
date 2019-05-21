package com.zhsq.biz.common;

import java.io.File;

import org.apache.log4j.Logger;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.kiebase.KieBaseEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;


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
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
            }
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
            }
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
            }
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
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
