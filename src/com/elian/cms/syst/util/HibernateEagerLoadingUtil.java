package com.elian.cms.syst.util;

import java.util.List;

import com.elian.cms.syst.model.EagerLoading;

public class HibernateEagerLoadingUtil {

	@SuppressWarnings("unchecked")
	public static void eagerLoadFiled(Object obj) {
		if(obj==null) return;
		if(obj instanceof EagerLoading){
			EagerLoading el=(EagerLoading) obj;
			//Hibernate.initialize(el);
			//Object fileds=el.getLazyObject();
			el.getLazyObject();
			//eagerLoadFiled(fileds);
		}
		if(obj instanceof List<?>){
			List<Object> list=(List<Object>) obj;
//			if(!Hibernate.isInitialized(obj)) Hibernate.initialize(obj);
//			Hibernate.initialize(list);
			for(Object filed:list){
				eagerLoadFiled(filed);
			}
		}
		return;
	}
}
