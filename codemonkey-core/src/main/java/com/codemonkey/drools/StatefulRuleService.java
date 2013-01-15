package com.codemonkey.drools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;

public abstract class StatefulRuleService extends RuleService{

	private KnowledgeBase kbase;
	
	private StatefulKnowledgeSession ksession;
	
//	private KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(getKsession(), "test");
	
	abstract Map<String , Object> getDomainObjects();
	
	public int run(){
		try {
			this.kbase = readKnowledgeBase();
			this.setKsession(kbase.newStatefulKnowledgeSession());
			insertDomainObject(getKsession());
			return executeRules(getKsession());
		} catch (Exception t) {
			t.printStackTrace();
		} finally{
//			logger.close();
			getKsession().dispose();
		}
		return -1;
	}
	
	int executeRules(StatefulKnowledgeSession ksession) {
		return ksession.fireAllRules();
	}

	protected void insertDomainObject(StatefulKnowledgeSession kSession){
		Map<String , Object> map = getDomainObjects();
		
		if(map == null || map.isEmpty()){
			return;
		}
		
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			kSession.insert(map.get(it.next()));
		}
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}
}
