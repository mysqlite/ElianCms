package com.elian.cms.syst.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * hibernate session管理
 * 
 * @author Joe
 * 
 */
public class HibernateUtils {
	private static SessionFactory sessionFactory = SpringUtils
			.getBean("sessionFactory");
	private static ThreadLocal<Session> localSessionMap = new ThreadLocal<Session>();

	public static Session getThreadLocalSession() {
		Session s = localSessionMap.get();
		if (s == null) {
			s = getSession();
			localSessionMap.set(s);
		}
		return s;
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}

	public static void closeSession() {
		Session session = (Session) localSessionMap.get();
		localSessionMap.set(null);
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
}
