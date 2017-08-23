package com.elian.cms.syst.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class JsonUtil {
	public static String json(Object obj,String... filed) {
		if(filed==null) return null;
		@SuppressWarnings("unused")
		List<Object> tree=new ArrayList<Object>();
		for(int i=0;i<filed.length;i++){
			//tree.
		}
		return null;
		
	}
	
	
	
	public static String json(StringBuffer jsonStr, Object obj,String... filed) {
//		Arrays.sort(filed);
		if(filed==null) return null;
		try{
			jsonStr.append("{");
			for(int i=0;i<filed.length;i++){
				if(filed[i].indexOf('.')>0){
					String filedName=filed[i].substring(0, filed[i].indexOf('.'));
					PropertyDescriptor property = new PropertyDescriptor(filedName,obj.getClass());
					Method  getMethod= property.getReadMethod();
					String json="";
					if(getMethod.invoke(obj)==null)
						json+="{}";
					else {
						
					}
						//json+=json(json,getMethod.invoke(obj),filed[i].substring(filed[i].indexOf('.')+1));
					jsonStr.append("\""+filedName+"\"").append(":").append(json);
				}else{
					PropertyDescriptor property = new PropertyDescriptor(filed[i],obj.getClass());
					Method  getMethod= property.getReadMethod();
					jsonStr.append("\""+filed[i]+"\"").append(":").append("\""+getMethod.invoke(obj)+"\"");
				}
				if(i+1!=filed.length)
					jsonStr.append(",");
			}
			jsonStr.append("}");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr.toString();
	}
	
	public static String jsonList(List<?> list,String... filed){
		if(CollectionUtils.isEmpty(list)) return null;
		StringBuffer json=new StringBuffer();
		Iterator<?> it= list.iterator();
		json.append("[");
		while (it.hasNext()) {
			Object obj= it.next();
			StringBuffer buf=new StringBuffer();
			json.append(json(buf,obj,filed));
			if(it.hasNext())
				json.append(",");
		}
		json.append("]");
		return json.toString();
	}
	
}
