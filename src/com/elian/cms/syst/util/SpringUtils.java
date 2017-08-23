package com.elian.cms.syst.util;

import java.beans.Introspector;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.elian.cms.syst.service.Service;

/**
 * 获取Spring管理的Service实现
 * 
 * @author Joe
 * 
 */
@SuppressWarnings("unchecked")
public final class SpringUtils {

	/**
	 * 通过实体名称得它对应的service
	 * 
	 * @param beanName
	 */
	public static <T> T getBean(String beanName) {
		// 获取当前运行环境下Spring上下文
		WebApplicationContext webApp = ContextLoader
				.getCurrentWebApplicationContext();
		return (T) webApp.getBean(beanName);
	}

	/**
	 * 通过实体类型得它对应的service
	 * 
	 * @param clazz
	 */
	public static Service getEntityService(Class clazz) {
		String className = clazz.getSimpleName();
		String serviceName = Introspector.decapitalize(className).concat("Service");
		return getBean(serviceName);
	}
}
