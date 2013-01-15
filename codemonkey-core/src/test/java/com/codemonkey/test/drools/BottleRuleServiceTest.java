package com.codemonkey.test.drools;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.drools.BottleProblem;
import com.codemonkey.drools.BottleRuleService;
import com.codemonkey.drools.Message;
import com.codemonkey.test.service.GenericServiceTest;

public class BottleRuleServiceTest extends  GenericServiceTest<Message>{

	@Autowired BottleRuleService bottleRuleService;
	
	@Test
	public void test(){
		bottleRuleService.run();
		BottleProblem bp = bottleRuleService.getBottleProblem();
		assertEquals(99 , bp.totalDrinked);
	}
}
