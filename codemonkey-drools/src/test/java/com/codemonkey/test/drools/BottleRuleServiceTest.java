package com.codemonkey.test.drools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.drools.BottleProblem;
import com.codemonkey.drools.BottleRuleService;

public class BottleRuleServiceTest extends  AbsDroolsServiceTest{

	@Autowired BottleRuleService bottleRuleService;
	
	@Test
	public void test(){
		bottleRuleService.run();
		BottleProblem bp = bottleRuleService.getBottleProblem();
		assertEquals(99 , bp.totalDrinked);
	}
}
