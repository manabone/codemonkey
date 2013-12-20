package com.codemonkey.erp.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.DocumentStatus;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.service.PhysicalServiceImpl;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public abstract class DocumentServiceImpl<T extends Document , E extends DocumentLine> extends PhysicalServiceImpl<T> implements DocumentService<T , E>{

	private static final String HEADER = "header";

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	
	@Autowired private ItemTransactionService itemTransactionService;
	
	@Autowired private CurrencyTransactionService currencyTransactionService;
	
	abstract DocumentLineService<E> getDocumentLineService();
	
	@Override
	protected Set<FieldValidation> validate(T doc){
		Set<FieldValidation> set = super.validate(doc);
		
		if(StringUtils.isBlank(doc.getCode())){
			set.add(new FormFieldValidation("code" , FieldValidation.EMPTY));
		}
		
		if(doc.getWarehouse() == null){
			set.add(new FormFieldValidation("warehouse" , FieldValidation.EMPTY));
		}
		
		return set;
	}
	
	protected Set<FieldValidation> validate4post(T doc){
		
		Set<FieldValidation> set = validate(doc);
		
		if(!DocumentStatus.Draft.equals(doc.getStatus())){
			set.add(new FormFieldValidation("status" , FieldValidation.NOT_DRAFT));
		}
		
		List<E> lines = getDocumentLineService().getLinesByHeader(doc);
		
		if(CollectionUtils.isEmpty(lines)){
			set.add(new FormFieldValidation("lines" , FieldValidation.EMPTY));
		}
		
		for(E line : lines){
			Set<FieldValidation> lineSet =  getDocumentLineService().validate4post(line);
			if(CollectionUtils.isNotEmpty(lineSet)){
				set.addAll(lineSet);
			}
		}
		
		return set;
	}
	
	public void post(T doc) {
		
		Set<FieldValidation> set = validate4post(doc);
		
		if(CollectionUtils.isNotEmpty(set)){
			throw new ValidationError(set);
		}
		
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
