package com.codemonkey.erp.service;

import java.util.List;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.service.GenericService;

public interface DocumentLineService extends GenericService<DocumentLine>{

	List<DocumentLine> getLinesByHeader(Document doc);

}
