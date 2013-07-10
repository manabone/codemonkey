package com.codemonkey.drools;

import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatelessKnowledgeSession;

public abstract class StatelessRuleService<T> extends RuleService{

	abstract T getRuleDomainObject();
	
	public T run(){
		KnowledgeRuntimeLogger logger = null;
		T t = null;
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			t = getRuleDomainObject();
			ksession.execute(t);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			logger.close();
		}
		return t;
	}
}
