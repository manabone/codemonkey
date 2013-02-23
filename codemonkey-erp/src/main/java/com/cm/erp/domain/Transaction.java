package com.cm.erp.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.codemonkey.domain.AbsEE;

@MappedSuperclass
public abstract class Transaction extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private DocumentLine docLine;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Document document;
	
	Transaction(DocumentLine docLine){
		setDocLine(docLine);
	}
	
	public DocumentLine getDocLine() {
		return docLine;
	}

	public void setDocLine(DocumentLine docLine) {
		this.docLine = docLine;
		this.document = docLine.getHeader();
	}

	public Document getDocument() {
		return document;
	}

}
