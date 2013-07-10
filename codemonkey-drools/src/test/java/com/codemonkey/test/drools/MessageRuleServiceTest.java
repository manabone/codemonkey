package com.codemonkey.test.drools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.drools.Message;
import com.codemonkey.drools.MessageRuleService;

public class MessageRuleServiceTest extends  AbsDroolsServiceTest {

	@Autowired MessageRuleService messageRuleService;
	
	@Test
	public void test(){
		Message message = messageRuleService.run();
		
		assertEquals("Goodbye cruel world" , message.getMessage());
		assertEquals( Message.GOODBYE , message.getStatus());
		
	}
}
