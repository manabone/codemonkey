package com.codemonkey.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.service.GenericServiceImpl;

@Service
public abstract class DocumentLineServiceImpl<T extends DocumentLine > extends GenericServiceImpl<T> implements DocumentLineService<T>{

	public List<T> getLinesByHeader(Document doc) {
		return findAllBy("header.id" , doc.getId());
	}
}
