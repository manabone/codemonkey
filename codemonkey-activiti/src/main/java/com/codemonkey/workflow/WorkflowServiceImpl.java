package com.codemonkey.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceImpl implements WorkflowService{
	
	private static final String TRANSITION_NAME = "transitionName";
	
	@Autowired private ProcessEngine processEngine;
	
	public String deployBPMN20FileByClassPath(String path) {
		Deployment deploy = getRepositoryService().createDeployment().addClasspathResource(path).deploy();
		return "Id : " + deploy.getId() + " - Name : " + deploy.getName() + " - Time : " + deploy.getDeploymentTime();
	}

	public ProcessInstance startInstance(String processDefinitionKey , String businessKey) {
		ProcessInstance processInstance = getInstance(businessKey);
	    if(processInstance == null) {
		    Map<String, Object> variables = new HashMap<String, Object>();
		    variables.put("businessKey",businessKey);
		    processInstance = getRuntimeService().startProcessInstanceByKey(processDefinitionKey , businessKey, variables);
	    }
		return processInstance;	
	}
	
	private ProcessInstance getInstance(String businessKey) {
		ProcessInstance instance = null;
		if(StringUtils.isNotBlank(businessKey)) {
			instance = getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		}
		return instance;
	}
	
	public void complete(String taskId, String toTransitionName){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put(TRANSITION_NAME, toTransitionName);
		getTaskService().complete(taskId, variables);
	}
	
	public List<Task> getActiveTasks(String businessKey){
		ProcessInstance instance = getInstance(businessKey);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getTaskService().createTaskQuery().processInstanceId(instance.getId()).list();
		}
		return list;
	}
	
	public List<Task> getActiveTasksByUserId(String businessKey, String userId){
		ProcessInstance instance = getInstance(businessKey);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getActiveTasksListByUser(instance, list, userId);
		}
		return list;
	}
	
	private List<Task> getActiveTasksListByUser(ProcessInstance instance, List<Task> list, String userId){
		if(list == null) {
			return new ArrayList<Task>();
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<Task> taskList = getTaskService().createTaskQuery().processInstanceId(instance.getId()).taskCandidateUser(userId).list();
			for (Task task : taskList) {
				list.add(task);
			}
			ProcessInstance subInstance = getRuntimeService().createProcessInstanceQuery().subProcessInstanceId(instance.getId()).singleResult();
			if(subInstance != null){
				list = getActiveTasksListByUser(subInstance, list, userId);
			}
		}
		return list;
	}
	
	private RuntimeService getRuntimeService() {
		return processEngine.getRuntimeService();
	}	
	
	private RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	private TaskService getTaskService() {
		return processEngine.getTaskService();
	}
	
}
