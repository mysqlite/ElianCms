package com.elian.cms.syst.filter;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.elian.cms.admin.data.exception.LogException;
import com.elian.cms.admin.model.Log;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.util.HibernateUtils;
import com.elian.cms.syst.util.LogParamUtils;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 日志记录，添加、删除、修改方法AOP
 * 
 * @author Joe
 */
public class LogAspect implements AfterReturningAdvice {
	/** 保存、添加 */
	public static final String SAVE = "save";
	public static final String ADD_CN = "添加";
	/** 更新 */
	public static final String UPDATE = "update";
	public static final String UPDATE_CN = "更新";
	/** 删除 */
	public static final String DELETE = "delete";
	public static final String DELETE_CN = "删除";

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		try {
			String methodName = method.getName();
			if (SAVE.equals(methodName) || UPDATE.equals(methodName)
					|| DELETE.equals(methodName)) {
				if (args.length > 0)
					resovleLogInfo(args[0], methodName);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分解日志对象回调方法信息
	 */
	public void resovleLogInfo(Object o, String methodName) {
		if (o instanceof PersistentLogInterface) {
			PersistentLogInterface obj = (PersistentLogInterface) o;
			// 如果调用的是delete方法
			if (DELETE.equals(methodName))
				createAndSaveLog(obj, LogParamUtils.OPERATION_DELETE, DELETE_CN);
			// 如果调用的是update方法或者是save方法，save方法中存在saveOrUpdate功能，必须判断好该操作对象是否持久化
			else if (UPDATE.equals(methodName))
				createAndSaveLog(obj, LogParamUtils.OPERATION_UPDATE, UPDATE_CN);
			// 如果调用的save方法，而且没有持久化
			else
				createAndSaveLog(obj, LogParamUtils.OPERATION_ADD, ADD_CN);
		}
	}

	/**
	 * 创建及缓存日志
	 */
	public void createAndSaveLog(PersistentLogInterface obj, int category,
			String operation) {
		String[] logs = obj.getSysLog();
		if (logs == null || logs.length != 2)
			throw new LogException("对象日志参数配置有误！");
		else {
			LogService logService = SpringUtils.getBean("logService");
			Log log = logService.createLog(category, logs[0] + operation,
					logs[1]);
			// 保存日志信息，因为logService的save方法也是在aspect所包含的切面中
			// 所以直接获取session进行插入操作
			HibernateUtils.getThreadLocalSession().save(log);
		}
	}
}
