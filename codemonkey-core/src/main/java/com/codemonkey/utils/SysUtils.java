package com.codemonkey.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.codemonkey.domain.AbsEntity;
import com.codemonkey.domain.AppUser;

@Component
public class SysUtils {

	public static final String CURRENCT_USER = "current_user";

	@Autowired private MessageSource appSettingBundle;
	
	@Autowired private MessageSource messageBundle;
	
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();
	
	public static Logger getLog(Class<?> clazz){
		return Logger.getLogger(clazz);
	}
	
	public static String idString(AbsEntity entity){
		if(entity != null){
			return entity.getId().toString();
		}
		return "";
	}
	
	public String getAppSetting(String key){
		String setting = "";
		if(isWindows()){
			setting = appSettingBundle.getMessage(key, null, new Locale("windows"));
		}else{
			setting = appSettingBundle.getMessage(key, null, new Locale("linux"));
		}
		return setting;
	}
	
	public static void putAttribute(String key , Object value){
		initThreadLocalMap();
		threadLocal.get().put(key, value);
	}

	private static void initThreadLocalMap() {
		Map<String , Object> map = threadLocal.get();
		if(map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
	}
	
	public static Object getAttribute(String key){
		initThreadLocalMap();
		return threadLocal.get().get(key);
	}
	
	public static Locale getCurrentLocale(){
		Locale locale = (Locale) SysUtils.getAttribute(ExtConstant.LOCALE);
		if(locale == null){
			locale = new Locale(ExtConstant.DEFAULT_LOCALE);
		}
		return locale;
	}
	
	public static String getCurrentTheme(HttpSession session) {
		String theme = (String) session.getAttribute(ExtConstant.THEME);
		if(theme == null){
			theme = ExtConstant.DEFAULT_THEME;
		}
		return theme;
	}
	
	public String msg(String key){
		return msg(key , null);
	}
	
	public String msg(String key , Object[] args){
		return msg(key , null , SysUtils.getCurrentLocale());
	}
	
	public String msg(String key , Object[] args , Locale locale){
		try{
			String msg = messageBundle.getMessage(key, args, locale);
			return new String(msg.getBytes("iso-8859-1") , "utf-8");
		}catch(NoSuchMessageException e){
			return "?" + key + "?";
		} catch (UnsupportedEncodingException e) {
			return "?" + key + "?";
		}
	}
	
	private static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		//windows
	    return (os.indexOf( "win" ) >= 0); 
	}
	
	public static void loadDataToDB( DriverManagerDataSource datasource , String filePath , DatabaseOperation op){
		
		IDatabaseConnection connection = null;
		
		try{
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			
			Resource resource = patternResolver.getResource(filePath);
			
			if(resource.exists()){
				// initialize your database connection here
	            connection = new DatabaseConnection(datasource.getConnection() , null);
	            
	            // initialize your dataset here
	            IDataSet dataSet = createDataSet(resource);
	            
	            op.execute(connection,dataSet);
			}
            
		} catch(Exception e){
        	e.printStackTrace();
        }finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	
	protected static IDataSet createDataSet(Resource resource) throws DataSetException, IOException {
		IDataSet dataSet = null;
		if(resource.getFilename().endsWith(".xml")){
			dataSet = new XmlDataSet( new FileInputStream(resource.getFile()));
		}else if(resource.getFilename().endsWith(".xls")){
			dataSet = new XlsDataSet( new FileInputStream(resource.getFile()));
		}else {
			throw new DataSetException("unsupport data type");
		}
		return dataSet;
	}
	
//	public static void sendEmail(String subject , String msg , String to){
//		try {
//			Email email = new SimpleEmail();
//			email.setFrom(getAppSetting("mail.default.from"));
//			email.setHostName(getAppSetting("mail.host"));
////			email.setSmtpPort(Integer.valueOf(resourceUtils.getAppSetting("mail.smtp.port")));
//			String username = getAppSetting("mail.username");
//			String password = getAppSetting("mail.password");
//			email.setAuthenticator(new DefaultAuthenticator(username , password));
////			email.setTLS(Boolean.valueOf(resourceUtils.getAppSetting("mail.tls")));
//			email.setSubject(subject);
//			email.setMsg(msg);
//			email.addTo(to);
//			email.send();
//		} catch (EmailException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static String formatDate(Date date){
		if(date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static String formatDouble(Double value){
		if(value == null){
			return "";
		}
		NumberFormat sdf = NumberFormat.getInstance();
		return sdf.format(value);
	}
	
	public static Date toDate(String dateString){
		try {
			if(StringUtils.isBlank(dateString)){
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void handleException(Exception e){
		e.printStackTrace();
	}
	
	public static String formatString(String s , Object... args){
		if(args == null || args.length == 0){
			return s;
		}
		
		String msg = s;
		
		String regex = "\\{(\\d+)\\}";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(s);
		int i = 0;
		while(m.find()){
			String part = m.group();
			if(i >=  args.length){
				break;
			}
			msg = msg.replace(part , args[i] != null ? args[i].toString() : "");
			i++;
		}
		return msg;
	}

	public static String getCurrentUsername() {
		AppUser user = (AppUser) SysUtils.getAttribute(CURRENCT_USER);
		
		return user !=null ? user.getUsername() : null;
	}

	public static Map<String, Object> jsonToMap(JSONObject jo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(jo == null){
			return map;
		}
		
		Iterator<?> it = jo.keys();
		while(it.hasNext()){
			String key = (String) it.next();
			
			if(jo.optJSONObject(key) != null){
				map.put(key, jsonToMap(jo.optJSONObject(key)));
			}else if(jo.optJSONArray(key) != null){
				map.put(key, jsonToMap(jo.optJSONArray(key)));
			}else{
				map.put(key , jo.get(key));
			}
		}
		return map;
	}
	
	public static List<Map<String, Object>> jsonToMap(JSONArray ja) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if(ja == null){
			return list;
		}
		
		for(int i = 0 ; i < ja.length() ; i++){
			JSONObject jo = ja.optJSONObject(i);
			if(jo != null){
				Map<String , Object> map = jsonToMap(jo);
				list.add(map);
			}
		}
		
		return list;
	}
	
	public static JSONObject mapToJson(Map<String , Object> map) {
		
		JSONObject jo = new JSONObject();
		
		if(map == null){
			return jo;
		}
		
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String , Object> entry = (Entry<String , Object>) it.next();
			jo.put(entry.getKey(), entry.getValue());
		}
		return jo;
	}
	
	public static JSONArray mapToJson(List<Map<String , Object>> list) {
		
		JSONArray ja = new JSONArray();
		
		if(CollectionUtils.isEmpty(list)){
			return ja;
		}
		
		for(Map<String , Object> map : list){
			JSONObject jo = mapToJson(map);
			ja.put(jo);
		}
		
		return ja;
	}
	
	public static String columnToProp(String col){
		
		if(StringUtils.isBlank(col)) return "";
		//replace _a to A
		Pattern p1= Pattern.compile("_[a-z]{1}");
		Matcher m1 = p1.matcher(col);
		
		while(m1.find()){
			col = col.replace(m1.group(), m1.group().substring(1).toUpperCase());
		}
		
		//replace _1 to 1
		Pattern p2= Pattern.compile("_\\d{1}");
		Matcher m2 = p2.matcher(col);
		
		while(m2.find()){
			col = col.replace(m2.group(), m2.group().substring(1).toUpperCase());
		}
		return col;
		
	}

}
