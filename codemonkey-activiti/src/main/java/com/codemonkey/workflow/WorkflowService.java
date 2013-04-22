package com.codemonkey.workflow;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface WorkflowService{

	void complete(String taskId, String toTransitionName);

	String deployBPMN20FileByClassPath(String path);

	List<Task> getActiveTasks(String businessKey);

	ProcessInstance startInstance(String processDefinitionKey , String businessKey);
	
}
