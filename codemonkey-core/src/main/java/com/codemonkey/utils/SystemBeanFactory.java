/**
 * Date：2013年10月28日
 * Module：codemonkey-core
 * Description: 
 * Remark:  
 * author: 范广强
 * version: 2.0
 * Copyright dliit.com Corporation 2013 
 * 版权所有
 */
/**
 * 修改历史
 *序号 日期     修改人    修改原因
 *1
 */
package com.codemonkey.utils;

/**
 * 类描述：获取ApplicationContext对象
 * @author: 范广强
 * @createtime: 2013年10月28日 下午3:51:47 
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SystemBeanFactory implements ServletContextListener {

	private static WebApplicationContext springContext;

	public SystemBeanFactory() {
		super();
	}

	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent event) {
	}

	public static ApplicationContext getApplicationContext() {
		return springContext;
	}

	public static Object getBean(String name) {
		if (!name.endsWith("Impl")) {
			name = name + "Impl";
		}
		return getApplicationContext().getBean(name);
	}
}
