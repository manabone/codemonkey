package com.codemonkey.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * 类描述：资源工具类
 */
@Component
public class ResourceUtils {
	
	@Autowired
	private MessageSource appSettingBundle;

	@Autowired
	private MessageSource messageBundle;
	

	
	/**
	 * 方法描述：取得APP设置
	 * 
	 * @param: key 键
	 * @return: APP设置
	 * @author: wy
	 */
	public String getAppSetting(String key) {
		String setting = "";
		if (isWindows()) {
			setting = appSettingBundle.getMessage(key, null, new Locale("windows"));
		} else {
			setting = appSettingBundle.getMessage(key, null, new Locale("linux"));
		}
		return setting;
	}
	
	/**
	 * 方法描述：取得消息
	 * 
	 * @param: key 键
	 * @return: 消息
	 * @author: wy
	 */
	public String msg(String key) {
		return msg(key, null);
	}


	public String msg(String key, Object[] args) {
		return msg(key, null, SysUtils.getCurrentLocale());
	}

	public String msg(String key, Object[] args, String locale) {
		try {
			return  messageBundle.getMessage(key, args, new Locale(locale));
		} catch (NoSuchMessageException e) {
			return "?" + key + "?";
		} 
	}
	
	private static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
	}

}
