package com.elian.cms.syst.converter;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

public class SqlServerDialectConverter extends SQLServerDialect {
	public SqlServerDialectConverter() {
		super();
		registerHibernateType(-9, Hibernate.STRING.getName());
	}
}
