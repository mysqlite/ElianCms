package com.elian.cms.syst.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.elian.cms.syst.util.HibernateUtils;

/**
 * 手动管理hibernate事务和Session过滤器
 * 
 * @author Joe
 * 
 */
public class OpenSessionInViewFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		Transaction tx = null;
		Session session = null;
		try {
			session = HibernateUtils.getThreadLocalSession();
			tx = session.beginTransaction();
			filter.doFilter(request, response);
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {
			HibernateUtils.closeSession();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
