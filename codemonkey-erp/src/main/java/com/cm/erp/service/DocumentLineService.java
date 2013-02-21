package com.cm.erp.service;

import java.util.List;

import com.cm.erp.domain.Document;
import com.cm.erp.domain.DocumentLine;
import com.codemonkey.service.GenericService;

public interface DocumentLineService extends GenericService<DocumentLine>{

	List<DocumentLine> getLinesByHeader(Document doc);

}
