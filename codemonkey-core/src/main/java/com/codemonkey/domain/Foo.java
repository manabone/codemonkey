package com.codemonkey.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.annotation.SkipBuild;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Audited
public class Foo extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String fstring;
	
	private Double fnumber;
	
	private Boolean fbool;
	
	@Enumerated(EnumType.STRING)
	private Status fstatus;
	
	private Date fdate;
	
	@ManyToOne
	private AppRole appRole;
	
	@ManyToOne
	private AppUserGroup appUserGroup;
	
	@SkipBuild
	private String skipBuild;
	
	@OneToMany(mappedBy="foo")
	private List<Bar> bars;

	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("fstring", OgnlUtils.stringValue("fstring", this));
		jo.put("fnumber",  OgnlUtils.stringValue("fnumber", this));
		jo.put("fbool", OgnlUtils.stringValue("fbool", this));
		jo.put("fstatus", OgnlUtils.stringValue("fstatus", this));
		jo.put("fdate", OgnlUtils.stringValue("fdate", this));
		jo.put("appRole", OgnlUtils.stringValue("appRole.id", this));
		jo.put("appRole_text", OgnlUtils.stringValue("appRole.name", this));
		jo.put("appUserGroup", OgnlUtils.stringValue("appUserGroup.code", this));
		return jo;
	}
	
	@Override
	public JSONObject detailJson() {
		return listJson();
	}


	public String getFstring() {
		return fstring;
	}


	public void setFstring(String fstring) {
		this.fstring = fstring;
	}


	public Double getFnumber() {
		return fnumber;
	}


	public void setFnumber(Double fnumber) {
		this.fnumber = fnumber;
	}


	public Boolean getFbool() {
		return fbool;
	}


	public void setFbool(Boolean fbool) {
		this.fbool = fbool;
	}


	public Status getFstatus() {
		return fstatus;
	}


	public void setFstatus(Status fstatus) {
		this.fstatus = fstatus;
	}


	public Date getFdate() {
		return fdate;
	}


	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}


	public AppRole getAppRole() {
		return appRole;
	}


	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}


	public AppUserGroup getAppUserGroup() {
		return appUserGroup;
	}


	public void setAppUserGroup(AppUserGroup appUserGroup) {
		this.appUserGroup = appUserGroup;
	}

	public String getSkipBuild() {
		return skipBuild;
	}

	public void setSkipBuild(String skipBuild) {
		this.skipBuild = skipBuild;
	}

	public List<Bar> getBars() {
		return bars;
	}

	public void setBars(List<Bar> bars) {
		this.bars = bars;
	}
	
}
