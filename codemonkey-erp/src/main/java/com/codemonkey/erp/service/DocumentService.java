package com.codemonkey.erp.service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.service.GenericService;

public interface DocumentService extends GenericService<Document>{

	void post(Document doc);

}
