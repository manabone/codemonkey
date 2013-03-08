package com.codemonkey.erp.service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.service.GenericService;

public interface DocumentService<T extends Document , E extends DocumentLine> extends GenericService<T>{

	void post(T doc);

}
