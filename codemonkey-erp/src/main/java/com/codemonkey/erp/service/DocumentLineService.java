package com.codemonkey.erp.service;

import java.util.List;
import java.util.Set;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.service.GenericService;

public interface DocumentLineService<T extends DocumentLine> extends GenericService<T>{

	List<T> getLinesByHeader(Document doc);
	
	Set<FieldValidation> validate4post(T line);

}
