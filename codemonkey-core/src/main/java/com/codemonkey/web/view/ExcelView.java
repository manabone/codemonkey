package com.codemonkey.web.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExcelUtils;

public class ExcelView extends AbstractExcelView{

	private String fileName;
	
	private List<IEntity> list;
	
	private JSONArray cols;
	
	public ExcelView(JSONArray cols, List<IEntity> list , String fileName){
		this.cols = cols;
		this.list = list;
		this.fileName = StringUtils.isBlank(fileName) ? String.valueOf(new Date().getTime()) : fileName;
	}
	
	@Override
	public void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workBook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setHeader("Content-Disposition", "attachment;Filename=" + fileName.toString()); 
		
		ExcelUtils.exportExcel(workBook, cols, list);
	}

}
