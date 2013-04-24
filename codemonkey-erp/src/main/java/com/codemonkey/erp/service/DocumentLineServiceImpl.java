package com.codemonkey.erp.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.RowFieldValidation;
import com.codemonkey.service.GenericServiceImpl;

@Service
public abstract class DocumentLineServiceImpl<T extends DocumentLine > extends GenericServiceImpl<T> implements DocumentLineService<T>{

	public List<T> getLinesByHeader(Document doc) {
		return findAllBy("header.id" , doc.getId());
	}
	
	@Override
	protected Set<FieldValidation> validate(T line) {
		Set<FieldValidation> set = super.validate(line);
		
		if(line.getItem() == null){
			set.add(new RowFieldValidation("item" , FieldValidation.EMPTY , getType().getSimpleName()));
		}
		
		if(line.getQty() == null){
			set.add(new RowFieldValidation("qty" , FieldValidation.EMPTY , getType().getSimpleName()));
		}else if(line.getQty() < 0){
			set.add(new RowFieldValidation("qty" , FieldValidation.LESS_THAN_ZERO , getType().getSimpleName()));
		}
		
		return set;
	}
	
	public Set<FieldValidation> validate4post(T line){
		
		Set<FieldValidation> set = validate(line);
		
		return set;
		
	}
}
