package com.codemonkey.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class DocumentLineServiceImpl extends GenericServiceImpl<DocumentLine> implements DocumentLineService{

	public List<DocumentLine> getLinesByHeader(Document doc) {
		return findAllBy("header.id" , doc.getId());
	}

}
