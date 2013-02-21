package com.cm.erp.service;

import com.cm.erp.domain.Document;
import com.codemonkey.service.GenericService;

public interface DocumentService extends GenericService<Document>{

	void post(Document doc);

}
