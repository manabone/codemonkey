package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;


@Entity
public class PowerTree extends AbsEE{
	
	//常量
	private static final long serialVersionUID = 5454155825314635342L;
	
	//变量
	@Label("父级权限")
	@ManyToOne
	@JoinColumn(name="parent_id")
	private PowerTree parent;
	
	@Label("权重")
	private Integer weight;
	
	@Transient
	private String parentCode;
	
	//抽象方法
	
	//构造方法
	
	PowerTree(){}
	
	public PowerTree(String code , String name , Integer weight , String parentCode){
		this.setCode(code);
		this.setName(name);
		this.parentCode = parentCode;
		this.weight = weight;
	}

	//覆盖或重载方法
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("parent", OgnlUtils.stringValue("parent.id", this));
		jo.put("parent_name", OgnlUtils.stringValue("parent_name", this));
		return jo;
	}

	//getset
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public PowerTree getParent() {
		return parent;
	}

	public void setParent(PowerTree parent) {
		this.parent = parent;
	}
	
}

