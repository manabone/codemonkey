package com.codemonkey.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.domain.IEntity;

public class ExcelUtils {

	/**
	 * 方法描述：读取excel数据
	 * 
	 * @param: path
	 * @return: List
	 * @author: dj
	 */
	public static List<List<String>> readExcel(String path) {
		List<List<String>> list = new ArrayList<List<String>>();
		try {  
            File file = new File(path); // 创建文件对象   
            InputStream is = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(is); // 从文件流中获取Excel工作区对象（WorkBook）   
            HSSFSheet sheet = wb.getSheetAt(0); // 从工作区中取得页（Sheet）   
              
            for (int i = 1 ; i < sheet.getLastRowNum() ; i++) { // 循环打印Excel表中的内容   
                HSSFRow row = sheet.getRow(i);
                List<String> rowList = new ArrayList<String>();
            	for (short j = 0 ; j < row.getLastCellNum() ; j++) {  
            		HSSFCell cell = row.getCell(j);
            		rowList.add(cell.getStringCellValue());
                }
            	list.add(rowList);
            } 
        } catch (Exception e) {
            e.printStackTrace();  
        }
		return list;
	}
	
	/**
	 * 方法描述：把数据导入到excel
	 * 
	 * @param: workBook
	 * @param: cols
	 * @param: list
	 * @return: void
	 * @author: wy
	 */
	public static void exportExcel(HSSFWorkbook workBook, JSONArray cols, List<IEntity> list) {
		
		HSSFSheet sheet = workBook.createSheet();
		generateHeaderRow(sheet, cols);

		if (CollectionUtils.isNotEmpty(list)) {
			generateDataRow(sheet, cols, list);
		}
	}

	private static void generateDataRow(HSSFSheet sheet, JSONArray cols,
			List<IEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		for (short i = 0; i < list.size(); i++) {
			JSONObject detailJson = list.get(i).detailJson();
			HSSFRow row = sheet.createRow(i + 1);
			short columnNum = 0;
			for (short j = 0; j < cols.length();j++) {
				if(cols.getJSONObject(j).has("hidden") && !cols.getJSONObject(j).getBoolean("hidden") && !"rownumberer".equals(cols.getJSONObject(j).getString("xtype"))){
					HSSFCell cell = row.createCell(columnNum);
					cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
					if("linkedcolumn".equals(cols.getJSONObject(j).getString("xtype"))){
						String data = detailJson.getString(cols.getJSONObject(j).getString("dataIndex"));
						JSONObject jo;
						try {
							jo = new JSONObject(data);
							cell.setCellValue(jo.getString("linkText"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else{
						cell.setCellValue(detailJson.optString(cols.getJSONObject(j).getString("dataIndex")));
					}
					columnNum++;
				}
			}
		}
	}

	private static void generateHeaderRow(HSSFSheet sheet, JSONArray cols) {
		HSSFRow headerRow = sheet.createRow(0);
		short columnNum = 0;
		for (short i = 0; i < cols.length(); i++) {
			JSONObject headerObj = cols.getJSONObject(i);
			if(headerObj.has("hidden") && !headerObj.getBoolean("hidden")  && !"rownumberer".equals(headerObj.getString("xtype"))){
				HSSFCell cell = headerRow.createCell(columnNum);
				try {
					cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
					cell.setCellValue(java.net.URLDecoder.decode(headerObj.getString("header"), "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				columnNum++;
			}
			
		}
	}
}
