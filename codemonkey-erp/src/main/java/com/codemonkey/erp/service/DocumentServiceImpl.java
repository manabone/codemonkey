package com.codemonkey.erp.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public abstract class DocumentServiceImpl<T extends Document , E extends DocumentLine> extends GenericServiceImpl<T> implements DocumentService<T , E>{

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	
	@Autowired private ItemTransactionService itemTransactionService;
	
	@Autowired private CurrencyTransactionService currencyTransactionService;
	
	abstract DocumentLineService<E> getDocumentLineService();
	
	abstract void validate4post(Document doc);
	
	public void post(T doc) {
		
		save(doc);
		
		validate4post(doc);
		
		List<?> lines = getDocumentLineService().getLinesByHeader(doc);
		
		List<Transaction> itemTrans = itemTransactionFactory.createTransactions(doc, lines);
		
		itemTransactionService.post(itemTrans);
		
		List<Transaction> currencyTrans = currencyTransactionFactory.createTransactions(doc, lines);
		
		currencyTransactionService.post(currencyTrans);
		
	}
	
	@Override
	public JSONObject doSave(JSONObject params , CustomConversionService ccService){
		JSONObject result = super.doSave(params , ccService);
		
		if(params.has(ExtConstant.TO_MODIFY_LINES)){
			JSONArray toModifyLines = params.getJSONArray(ExtConstant.TO_MODIFY_LINES);
			if(toModifyLines != null){
				for(int i = 0 ; i < toModifyLines.length() ; i++){
					JSONObject lineJo = toModifyLines.getJSONObject(i);
					lineJo.put("salesOrder", params.get(ExtConstant.ID));
					getDocumentLineService().doSave(lineJo , ccService);
				}
			}
		}
		
		if(params.has(ExtConstant.TO_DELETE_LINES)){
			JSONArray toDeleteLines = params.getJSONArray(ExtConstant.TO_DELETE_LINES);
			if(toDeleteLines != null){
				for(int i = 0 ; i < toDeleteLines.length() ; i++){
					getDocumentLineService().delete(toDeleteLines.getJSONObject(i).getLong(ExtConstant.ID));
				}
			}
		}
		
		return result;
	}

}
