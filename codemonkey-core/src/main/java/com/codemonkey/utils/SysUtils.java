package com.codemonkey.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
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
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.codemonkey.domain.AbsEntity;
import com.codemonkey.domain.AppUser;

/**
 * 类描述：系统工具类
 */
@Component
public class SysUtils {

	public static final String CURRENCT_USER = "current_user";
//	public static final String CURRENCT_USER_LOGININFO = "currenct_user_logininfo";
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

	public static Logger getLog(Class<?> clazz) {
		return Logger.getLogger(clazz);
	}

	/**
	 * 方法描述：取得实体键值
	 * 
	 * @param: entity 实体
	 * @return: 实体键值
	 * @author: wy
	 */
	public static String idString(AbsEntity entity) {
		if (entity != null) {
			return entity.getId().toString();
		}
		return "";
	}

	/**
	 * 方法描述：字符串trim处理
	 * 
	 * @param: str 字符串
	 * @return: trim处理
	 * @author: wy
	 */
	public static String trimString(String str) {
		if (str == null || "null".equals(str)) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 方法描述：设置系统属性
	 * 
	 * @param: key 键
	 * @param: value 值
	 * @return: void
	 * @author: wy
	 */
	public static void putAttribute(String key, Object value) {
		initThreadLocalMap();
		threadLocal.get().put(key, value);
	}

	/**
	 * 方法描述：初期化ThreadLocal
	 * 
	 * @return: void
	 * @author: wy
	 */
	private static void initThreadLocalMap() {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
	}

	/**
	 * 方法描述：取得系统属性
	 * 
	 * @param: key 键
	 * @param: value 值
	 * @return: void
	 * @author: wy
	 */
	public static Object getAttribute(String key) {
		initThreadLocalMap();
		return threadLocal.get().get(key);
	}

	/**
	 * 方法描述：取得本地化
	 * 
	 * @return: 本地化
	 * @author: wy
	 */
	public static Locale getCurrentLocale() {
		Locale locale = (Locale) SysUtils.getAttribute(ExtConstant.LOCALE);
		if (locale == null) {
			locale = new Locale(ExtConstant.DEFAULT_LOCALE);
		}
		return locale;
	}

	/**
	 * 方法描述：取得主题
	 * 
	 * @param: session 会话
	 * @return: 主题
	 * @author: wy
	 */
	public static String getCurrentTheme(HttpSession session) {
		String theme = (String) session.getAttribute(ExtConstant.THEME);
		if (theme == null) {
			theme = ExtConstant.DEFAULT_THEME;
		}
		return theme;
	}




	/**
	 * 方法描述：把资源数据写入数据库
	 * 
	 * @param: datasource 数据源
	 * @param: filePath 文件路径
	 * @param: op 数据操作
	 * @return: void
	 * @author: wy
	 */
	
	public static void loadDataToDB(DriverManagerDataSource datasource, String filePath) {
		loadDataToDB(datasource, filePath , DatabaseOperation.REFRESH);
	}
	
	public static void loadDataToDB(DriverManagerDataSource datasource, String filePath, DatabaseOperation op) {

		IDatabaseConnection connection = null;
		try {
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

			Resource[] resources = patternResolver.getResources(filePath);
			
			if(resources == null){
				return;
			}

			for(int i = 0 ; i < resources.length ; i++){
				if (resources[i].exists()) {
					// initialize your database connection here
					connection = new DatabaseConnection(datasource.getConnection(), null);

					// initialize your dataset here
					IDataSet dataSet = createDataSet(resources[i]);

					op.execute(connection, dataSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 方法描述：创建数据集
	 * 
	 * @param: resource 资源
	 * @return: IDataSet
	 * @author: wy
	 */
	protected static IDataSet createDataSet(Resource resource)
			throws DataSetException, IOException {
		IDataSet dataSet = null;
		if (resource.getFilename().endsWith(".xml")) {
			dataSet = new XmlDataSet(new FileInputStream(resource.getFile()));
		} else if (resource.getFilename().endsWith(".xls")) {
			dataSet = new XlsDataSet(new FileInputStream(resource.getFile()));
		} else {
			throw new DataSetException("unsupport data type");
		}
		return dataSet;
	}

	// public static void sendEmail(String subject , String msg , String to){
	// try {
	// Email email = new SimpleEmail();
	// email.setFrom(getAppSetting("mail.default.from"));
	// email.setHostName(getAppSetting("mail.host"));
	// //
	// email.setSmtpPort(Integer.valueOf(resourceUtils.getAppSetting("mail.smtp.port")));
	// String username = getAppSetting("mail.username");
	// String password = getAppSetting("mail.password");
	// email.setAuthenticator(new DefaultAuthenticator(username , password));
	// //
	// email.setTLS(Boolean.valueOf(resourceUtils.getAppSetting("mail.tls")));
	// email.setSubject(subject);
	// email.setMsg(msg);
	// email.addTo(to);
	// email.send();
	// } catch (EmailException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 方法描述：格式化日期
	 * 
	 * @param: date 日期
	 * @return: 格式化后日期
	 * @author: wy
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 方法描述：格式化日期
	 * 
	 * @param: date 日期
	 * @param: format 格式
	 * @return: 格式化后日期
	 * @author: wy
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 方法描述：格式化小数
	 * 
	 * @param: value 数值
	 * @return: 格式化后小数
	 * @author: wy
	 */
	public static String formatDouble(Double value) {
		if (value == null) {
			return "";
		}
		NumberFormat sdf = NumberFormat.getInstance();
		return sdf.format(value);
	}

	/**
	 * 方法描述：把字符串转换成日期
	 * 
	 * @param: dateString 字符串
	 * @return: 日期
	 * @author: wy
	 */
	public static Date toDate(String dateString) {

		return toDate(dateString, "yyyy-MM-dd");
	}

	/**
	 * 方法描述：把字符串转换成日期
	 * 
	 * @param: dateString 字符串
	 * @param: fmt 格式
	 * @return: 日期
	 * @author: wy
	 */
	public static Date toDate(String dateString, String fmt) {
		try {
			if (StringUtils.isBlank(dateString)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void handleException(Exception e) {
		e.printStackTrace();
	}

	public static String formatString(String s, Object... args) {
		if (args == null || args.length == 0) {
			return s;
		}

		String msg = s;

		String regex = "\\{(\\d+)\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		int i = 0;
		while (m.find()) {
			String part = m.group();
			if (i >= args.length) {
				break;
			}
			msg = msg.replace(part, args[i] != null ? args[i].toString() : "");
			i++;
		}
		return msg;
	}

	/**
	 * 方法描述：从会话中获得当前用户工号
	 * 
	 * @return: 工号
	 * @author: wy
	 */
	public static String getCurrentUsername() {
		AppUser user = getCurrentUser();

		return user != null ? user.getUsername() : null;
	}

	/**
	 * 方法描述：从会话中获得当前用户
	 * 
	 * @return: 用户
	 * @author: wy
	 */
	public static AppUser getCurrentUser() {
		return (AppUser) SysUtils.getAttribute(CURRENCT_USER);
	}

	/**
	 * 方法描述：json转换成Map
	 * 
	 * @param: jo json
	 * @return: Map对象
	 * @author: wy
	 */
	public static Map<String, Object> jsonToMap(JSONObject jo) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (jo == null) {
			return map;
		}

		Iterator<?> it = jo.keys();
		while (it.hasNext()) {
			String key = (String) it.next();

			if (jo.optJSONObject(key) != null) {
				map.put(key, jsonToMap(jo.optJSONObject(key)));
			} else if (jo.optJSONArray(key) != null) {
				map.put(key, jsonToMap(jo.optJSONArray(key)));
			} else {
				map.put(key, jo.get(key));
			}
		}
		return map;
	}

	/**
	 * 方法描述：JSONArray转换成List<Map>
	 * 
	 * @param: ja JSONArray
	 * @return: List<Map>对象
	 * @author: wy
	 */
	public static List<Map<String, Object>> jsonToMap(JSONArray ja) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (ja == null) {
			return list;
		}

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.optJSONObject(i);
			if (jo != null) {
				Map<String, Object> map = jsonToMap(jo);
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * 方法描述：Map转换成json对象
	 * 
	 * @param: map map对象
	 * @return: json对象
	 * @author: wy
	 */
	public static JSONObject mapToJson(Map<String, Object> map) {

		JSONObject jo = new JSONObject();

		if (map == null) {
			return jo;
		}

		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = (Entry<String, Object>) it.next();
			jo.put(entry.getKey(), entry.getValue());
		}
		return jo;
	}

	/**
	 * 方法描述：List<Map>转换成json对象
	 * 
	 * @param: list 查询结果
	 * @return: json对象
	 * @author: wy
	 */
	public static JSONArray mapToJson(List<Map<String, Object>> list) {

		JSONArray ja = new JSONArray();

		if (CollectionUtils.isEmpty(list)) {
			return ja;
		}

		for (Map<String, Object> map : list) {
			JSONObject jo = mapToJson(map);
			ja.put(jo);
		}

		return ja;
	}
	

	public static String propToColumn(String prop) {
		if (StringUtils.isBlank(prop)) {
			return "";
		}
		// replace aA to _a
		Pattern p1 = Pattern.compile("[a-z]{1}[A-Z]{1}");
		Matcher m1 = p1.matcher(prop);

		while (m1.find()) {
			prop = prop.replace(m1.group(), m1.group().substring(0,1) + "_" + m1.group().substring(1).toLowerCase());
		}

		// replace _1 to 1
		Pattern p2 = Pattern.compile("[a-zA-Z]{1}\\d{1}");
		Matcher m2 = p2.matcher(prop);

		while (m2.find()) {
			prop = prop.replace(m2.group(), m2.group().substring(0,1) + "_" + m2.group().substring(1));
		}
		return prop;
	}

	public static String columnToProp(String col) {

		if (StringUtils.isBlank(col)) {
			return "";
		}
		// replace _a to A
		Pattern p1 = Pattern.compile("_[a-z]{1}");
		Matcher m1 = p1.matcher(col);

		while (m1.find()) {
			col = col
					.replace(m1.group(), m1.group().substring(1).toUpperCase());
		}

		// replace _1 to 1
		Pattern p2 = Pattern.compile("_\\d{1}");
		Matcher m2 = p2.matcher(col);

		while (m2.find()) {
			col = col
					.replace(m2.group(), m2.group().substring(1).toUpperCase());
		}
		return col;

	}

	/**
	 * 方法描述：获取代理对象的目标对象
	 * 
	 * @param: proxy 代理
	 * @return: 代理对象
	 * @author: wy
	 */
	public static Object getTarget(Object proxy) {

		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;// 不是代理对象
		}

		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { // cglib
			return getCglibProxyTargetObject(proxy);
		}

	}

	private static Object getCglibProxyTargetObject(Object proxy) {
		try {
			Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
			h.setAccessible(true);
			Object dynamicAdvisedInterceptor = h.get(proxy);

			Field advised = dynamicAdvisedInterceptor.getClass()
					.getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport) advised
					.get(dynamicAdvisedInterceptor)).getTargetSource()
					.getTarget();

			return target;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object getJdkDynamicProxyTargetObject(Object proxy) {
		Field h;
		try {
			h = proxy.getClass().getSuperclass().getDeclaredField("h");
			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);

			Field advised = aopProxy.getClass().getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport) advised.get(aopProxy))
					.getTargetSource().getTarget();
			return target;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
