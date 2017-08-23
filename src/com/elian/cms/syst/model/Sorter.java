package com.elian.cms.syst.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于对实体进行排序,反射和内省实现
 * 
 * @author Joe
 */
@SuppressWarnings({ "unchecked"})
public class Sorter implements Comparator {
	private Map<String, Boolean> propertyNameMap;

	public Sorter() {
		this(null);
	}

	public Sorter(String name) {
		this(name, true);
	}

	public Sorter(String name, boolean desc) {
		addPropertyName(name, desc);
	}

	public void addPropertyName(String name) {
		addPropertyName(name, true);
	}

	public void addPropertyName(String name, boolean desc) {
		if (name == null || "".equals(name))
			return;
		if (propertyNameMap == null)
			propertyNameMap = new LinkedHashMap<String, Boolean>();
		propertyNameMap.put(name, desc);
	}

	public int compare(Object o1, Object o2) {
		Object v1 = null;
		Object v2 = null;
		for (String name : propertyNameMap.keySet()) {
			v1 = getPropertyValue(o1, name);
			v2 = getPropertyValue(o2, name);
			boolean desc = propertyNameMap.get(name);
			if (v1 == null && v2 == null) {
				continue;
			}
			else if (v1 != null && v2 == null) {
				return coverResult(desc, -1);
			}
			else if (v1 == null && v2 != null) {
				return coverResult(desc, 1);
			}
			else {
				int temp = 0;
				if (v1.getClass().equals(v2.getClass())) {
					temp = ((Comparable) v2).compareTo(v1);
				}
				if (temp != 0) {
					return coverResult(desc, temp);
				}
			}
		}
		return 0;
	}

	private int coverResult(boolean desc, int result) {
		if (desc) {
			return result;
		}
		return -1 * result;
	}

	public Object getPropertyValue(Object o, String propertyName) {
		if (o == null)
			return null;
		BeanInfo beanInfo = null;
		String[] propertys = propertyName.split("\\.");
		Class<?> clazz = o.getClass();
		Method[] methods = new Method[propertys.length];
		Object temp = o;
		for (int i = 0, length = propertys.length; i < length; i++) {
			try {
				beanInfo = Introspector.getBeanInfo(clazz);
			}
			catch (IntrospectionException e1) {
				e1.printStackTrace();
			}
			PropertyDescriptor[] descriptions = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor des : descriptions) {
				if (propertys[i].equals(des.getName())) {
					Method method = des.getReadMethod();
					methods[i] = method;
					clazz = method.getReturnType();
					try {
						temp = method.invoke(temp);
						if (i == length - 1)
							return temp;
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return temp;
	}
}
