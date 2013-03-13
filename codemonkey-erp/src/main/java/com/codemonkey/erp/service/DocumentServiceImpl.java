package com.codemonkey.erp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.DocumentStatus;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public abstract class DocumentServiceImpl<T extends Document , E extends DocumentLine> extends GenericServiceImpl<T> implements DocumentService<T , E>{

	private static final String HEADER = "header";

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	
	@Autowired private ItemTransactionService itemTransactionService;
	
	@Autowired private CurrencyTransactionService currencyTransactionService;
	
	abstract DocumentLineService<E> getDocumentLineService();
	
	abstract void validate4post(Document doc);
	
	public void post(T doc) {
		
		validate4post(doc);
		
		doc.setStatus(DocumentStatus.Posted);
		
		save(doc);
		
		List<?> lines = getDocumentLineService().getLinesByHeader(doc);
		
		List<Transaction> itemTrans = itemTransactionFactory.createTransactions(doc, lines);
		
		itemTransactionService.post(itemTrans);
		
		List<Transaction> currencyTrans = currencyTransactionFactory.createTransactions(doc, lines);
		
		currencyTransactionService.post(currencyTrans);
		
	}
	
	@Override
	public T doSave(JSONObject params , CustomConversionService ccService){
		T t = super.doSave(params , ccService);
		
		if(params.has(ExtConstant.TO_MODIFY_LINES)){
			JSONArray toModifyLines = params.getJSONArray(ExtConstant.TO_MODIFY_LINES);
			if(toModifyLines != null){
				for(int i = 0 ; i < toModifyLines.length() ; i++){
					JSONObject lineJo = toModifyLines.getJSONObject(i);
					lineJo.put(HEADER, t.getId());
					getDocumentLineService().doSave(lineJo , ccService);
				}
			}
		}
		
		if(params.has(ExtConstant.TO_DELETE_LINES)){
			JSONArray toDeleteLines = params.getJSONArray(ExtConstant.TO_DELETE_LINES);
			if(toDeleteLines != null){
				for(int i = 0 ; i < toDeleteLines.length() ; i++){
					if(toDeleteLines.getJSONObject(i).has(ExtConstant.ID)){
						getDocumentLineService().delete(toDeleteLines.getJSONObject(i).getLong(ExtConstant.ID));
					}
				}
			}
		}
		
		return t;
	}

}
