package com.codemonkey.drools;

import org.springframework.stereotype.Component;

@Component
public class MessageRuleServiceImpl extends StatelessRuleService<Message> implements MessageRuleService{
	
	@Override
	Message getRuleDomainObject() {
		Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		return message;
	}

	@Override
	String getRulePath() {
		return "rules/Sample.drl";
	}
}
