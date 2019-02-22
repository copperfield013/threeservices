package com.zhsq.biz.common;

import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class SessionFactory {
	private static Logger logger = Logger.getLogger(SessionFactory.class);
	public static KieSession  findSession(String sessionName){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieClasspathContainer();
		KieSession kSession = kContainer
				.newKieSession(sessionName);
//		kSession.addEventListener( new DebugRuleRuntimeEventListener() );
		
		kSession.addEventListener( new DefaultAgendaEventListener() {
			public void afterMatchFired(AfterMatchFiredEvent event) {
			super.afterMatchFired( event );
			logger.debug( event.getMatch().getRule().getName()+" is fired" );
			}
			});
		
		return kSession;
	}
	
	public static KieSession  findSessionKeepContainer(String sessionName){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer
				.newKieSession(sessionName);
//		kSession.addEventListener( new DebugRuleRuntimeEventListener() );
		
		kSession.addEventListener( new DefaultAgendaEventListener() {
			public void afterMatchFired(AfterMatchFiredEvent event) {
				super.afterMatchFired( event );
				logger.debug( event.getMatch().getRule().getName()+" is fired" );
				}
			});
		
		kSession.addEventListener(new AgendaEventListener() {
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " can be fired in agenda");
            }
            public void matchCancelled(MatchCancelledEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " cannot b in agenda");
            }
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " will be fired");
            }
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " has be fired");
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
	
	public static KieSession  findScannerSession(String sessionName){
		KieServices kieServices = KieServices.Factory.get();
		
		/*groupId=com.drools.web.test
				artifactId=drools_web_class
				version=1.6.10
				1.6.9-SNAPSHOT*/
				
		//ReleaseId releaseId = kieServices.newReleaseId("com.drools.web.test", "drools_web_class", "1.6.9-SNAPSHOT");
				
		ReleaseId releaseId = kieServices.newReleaseId("com.zhsq.biz", "threeservices", "0.0.33");
				
		KieContainer kieContainer = kieServices.newKieContainer(releaseId);
		KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
		
		kieScanner.start(10000L);//10秒
		KieSession kSession = kieContainer
				.newKieSession(sessionName);
//		kSession.addEventListener( new DebugRuleRuntimeEventListener() );
		
		kSession.addEventListener( new DefaultAgendaEventListener() {
			public void afterMatchFired(AfterMatchFiredEvent event) {
			super.afterMatchFired( event );
			logger.debug( event.getMatch().getRule().getName()+" is fired" );
			}
			});
		
		return kSession;
	}
	
	
	/*public static void main(String[] args) throws InterruptedException {
		KieSession session = SessionFactory.findScannerSession("");
		
		session.fireAllRules();
		
		Thread.sleep(1000L);
	}*/

}
