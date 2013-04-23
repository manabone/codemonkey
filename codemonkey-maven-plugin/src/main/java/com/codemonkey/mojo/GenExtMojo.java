package com.codemonkey.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.EnumUtils;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal genext
 * 
 * @phase process-sources
 */
public class GenExtMojo extends GenMojo {
	
	private static final String CONFIG2 = "config";
	private static final String MODEL = "model";
	private static final String XTYPE = "xtype";

	protected Map<String, Object> buildBinding() {
		Map<String, Object> binding = super.buildBinding();
		
		Class<?> clazz = loadClass(getEntityName());
		List<Field> fields = ClassHelper.getAllFields(clazz);
		JSONArray fieldsJa = new JSONArray();
		
		List<Field> stringFields = new ArrayList<Field>();
		List<Field> numberFields = new ArrayList<Field>();
		List<Field> dateFields = new ArrayList<Field>();
		List<Field> booleanFields = new ArrayList<Field>();
		List<Field> enumFields = new ArrayList<Field>();
		List<Field> relationFields = new ArrayList<Field>();
		
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				if(f.getType().equals(String.class)){
					stringFields.add(f);
				}else if(f.getType().equals(Date.class)){
					dateFields.add(f);
				}else if(f.getType().equals(Boolean.class)){
					booleanFields.add(f);
				}else if(f.getType().isEnum()){
					enumFields.add(f);
				}else if(f.getType().equals(Double.class) || f.getType().equals(Integer.class) 
						|| f.getType().equals(Long.class)){
					numberFields.add(f);
				}else if( f.getType().equals(Short.class) || f.getType().equals(Float.class) ){
					numberFields.add(f);
				}else if(f.getAnnotation(ManyToOne.class) != null){
					relationFields.add(f);
				}
			}
		}
		
		buildStringFields(stringFields , fieldsJa);
		buildNumberFields(numberFields , fieldsJa);
		buildDateFields(dateFields , fieldsJa);
		buildBooleanFields(booleanFields , fieldsJa);
		buildEnumFields(enumFields , fieldsJa);
		buildRelationFields(relationFields , fieldsJa);
		
		binding.put("fieldsJson", unescapeUnicode(fieldsJa.toString()));
		
		//build columns
		binding.put("columnsJson", unescapeUnicode(buildColumnsJson(fields)));
		return binding;
	}

	private String buildColumnsJson(List<Field> fields) {
		JSONArray columnsJson = new JSONArray();
		if(CollectionUtils.isNotEmpty(fields)){
			for(Field f : fields){
				JSONObject jo = new JSONObject();
				jo.put("header" , label(f));
				jo.put("dataIndex" , f.getName());
				jo.put("flex" , 1);
				columnsJson.put(jo);
			}
		}
		return columnsJson.toString();
	}

	private void buildRelationFields(List<Field> fields,
			JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "searchingselect");
				JSONObject config = new JSONObject();
				
				if(isImplementsOfEE(f.getType())){
					config.put(MODEL, f.getType().getSimpleName() + "List");
				}else{
					config.put(MODEL, f.getType().getSimpleName());
				}
				
				fieldjo.put(CONFIG2, config);
				fieldsJson.put(fieldjo);
			}
		}
	}

	private boolean isImplementsOfEE(Class<?> type) {
		Class<?> type1 = type;
		while(!type1.equals(Object.class)){
			Class<?>[] interfaces = type1.getInterfaces();
			if(interfaces != null){
				for(int i = 0 ; i < interfaces.length ; i++ ){
					if(interfaces[i].getSimpleName().equals("EE")){
						return true;
					}
				}
			}
			type1 = type1.getSuperclass();
		}
		return false;
	}

	private void buildEnumFields(List<Field> fields,
			JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "selectfield");
				fieldjo.put("data", EnumUtils.getEnmuDataByClazz(f.getType()));
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildBooleanFields(List<Field> fields,
			JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "checkbox");
				fieldsJson.put(fieldjo);
			}
		}
	}

	private void buildDateFields(List<Field> fields,
			JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "datefield");
				fieldjo.put("format", "Y-m-d");
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildNumberFields(List<Field> fields,
			JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "numberfield");
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildStringFields(List<Field> fields, JSONArray fieldsJson) {
		
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "textfield");
				fieldsJson.put(fieldjo);
			}
		}
	}

	private JSONObject buildField(Field f) {
		JSONObject jo = new JSONObject();
//		jo.put("id", f.getName());
		jo.put("name", f.getName());
		jo.put("fieldLabel", label(f));
		buildValidation(jo , f);
		return jo;
	}

	private void buildValidation(JSONObject jo, Field field) {
		
		if(field.getAnnotation(NotNull.class) != null){
			jo.put("allowBlank", false);
		}
		if(field.getAnnotation(Min.class) != null){
			jo.put("minValue", field.getAnnotation(Min.class).value());
		}
		if(field.getAnnotation(Max.class) != null){
			jo.put("maxValue", field.getAnnotation(Max.class).value());
		}
		if(field.getAnnotation(Email.class) != null){
			jo.put("vtype", "email");
		}
		if(field.getAnnotation(URL.class) != null){
			jo.put("vtype", "url");
		}
	}
}
