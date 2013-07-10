package com.codemonkey.drools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BottleRuleServiceImpl extends StatefulRuleService implements BottleRuleService{

	private BottleProblem bp = new BottleProblem();
	
	@Override
	Map<String, Object> getDomainObjects() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BottleProblem", bp);
		return map;
	}

	@Override
	String getRulePath() {
		return "rules/Bottle.drl";
	}
	
	public BottleProblem getBottleProblem(){
		return this.bp;
	}
	
}
