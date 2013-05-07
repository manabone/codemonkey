package com.codemonkey.domain.seq;

import javax.persistence.Entity;

@Entity
public class YearedSequenceCreator extends SequenceCreator{

	public YearedSequenceCreator(String prefix , Integer year) {
		super(prefix);
		this.year = year;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer year;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}
