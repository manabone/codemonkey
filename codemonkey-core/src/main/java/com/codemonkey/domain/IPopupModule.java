package com.codemonkey.domain;

public interface IPopupModule {
	
	static final String LINK_PREFIX = "LINK_";
	
	//部门
	static final String BS_GROUP_INFO_FORM_MODULE = "bsGroupInfoFormModule";
	
	//地区
	static final String BS_REGION_INFO_FORM_MODULE = "bsRegionInfoFormModule";
	
	//人员信息
	static final String HR_PERSON_INFO_FORM_MODULE = "hrPersonInfoFormModule";
	
	//工作组
	static final String HR_JOBGROUP_INFO_FORM_MODULE = "hrJobGroupFormModule";
	
	//设备
	static final String HR_DEVICEGROUP_INFO_FORM_MODULE = "hrDeviceGroupFormModule";
	
	Long getId();
	String getLinkText();
	String getModuleId();
	
}
