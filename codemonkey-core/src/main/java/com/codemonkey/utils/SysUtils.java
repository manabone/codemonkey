package com.codemonkey.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.codemonkey.domain.AbsEntity;

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
			
			// initialize your database connection here
            connection = new DatabaseConnection(datasource.getConnection() , null);
            
            // initialize your dataset here
            IDataSet dataSet = createDataSet(resource);
            
            op.execute(connection,dataSet);
            
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
	
	
	protected static IDataSet createDataSet(Resource resource) throws DataSetException, FileNotFoundException, IOException {
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
		return (String) getAttribute(SysUtils.CURRENCT_USER);
	}

}
