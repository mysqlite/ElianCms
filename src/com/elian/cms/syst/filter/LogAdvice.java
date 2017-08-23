package com.elian.cms.syst.filter;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.elian.cms.syst.util.StringUtils;
@SuppressWarnings("unchecked")
public class LogAdvice implements MethodBeforeAdvice, AfterReturningAdvice {
	private final static Log logger = LogFactory.getLog(LogAdvice.class);
	private final static SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void before(Method method, Object[] values, Object object)throws Throwable {
		Object[] objs=retruns(values);
		logger.warn("--------执行" +object.getClass().getSimpleName()+ "中的" + method.getName()+ "方法," 
				+((objs!=null&&objs.length>0)?"参数为:"+Arrays.toString(objs) :"")+ "。");
	}
	
	public void afterReturning(Object object, Method method, Object[] values,Object object2) {
		long heapSize = Runtime.getRuntime().totalMemory(); 
		long heapFreeSize = Runtime.getRuntime().freeMemory(); 
	    long heapMaxSize = Runtime.getRuntime().maxMemory(); 
		String obj = null;
		List<Object> objList=null;
		try {
			objList = (List<Object>) object;
			if(objList!=null)
	    		if (objList.size()>0) 
	    			obj =objList.get(0).getClass().toString().replace("class com.elian.cms.", "");
		}
		catch (Exception e) {
			   obj=object.getClass().toString();    
		}
		Object[] objs=retruns(values);
		logger.warn("=结束" +StringUtils.getEN(object2.getClass().toString())+ "中的" + method.getName()+ "方法,"+
				((objs!=null&&objs.length>0)?"参数为:"+Arrays.toString(objs) :"")+ (objList!=null?",返回值:" +objList.size()+"条["+ obj+"]":(obj!=null?"返回值:"+obj:"")));
		logger.warn("="+StringUtils.getOperatorAction()+"【最大内存为:"+(heapMaxSize/(1024*1024))+"MB,已使用"+((heapSize-heapFreeSize)/(1024*1024))+"MB】\n");
	}
	
	private Object[] retruns(Object[] values) {
		Object[] objs=null;
		List<Object> objsList=null;
		String size="";
		if(values!=null&&values.length>0) {
			objs=new Object[values.length];
    		for (int i = 0; i < values.length; i++) {
				try {
					objsList = (List<Object>) values[i];
					size="_["+objsList.size()+"条]";
					objs[i]=objsList.get(0).getClass().toString().replace("class com.elian.cms.","")+size;
				}
				catch (Exception e) {
					if(values[i]==null)
						objs[i]=values[i];
				    else if(values[i] instanceof Date)
	    				objs[i]=sf.format(values[i]);
	    			else if(values[i].getClass().toString().contains("class com.elian.cms."))
	    				objs[i]=values[i].getClass().toString().replace("class com.elian.cms.","");
	    			else
	    				objs[i]=values[i];
				}
    		}
		}
		return objs;
	}
}
