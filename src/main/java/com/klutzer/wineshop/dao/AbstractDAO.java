package com.klutzer.wineshop.dao;

import org.mentabean.BeanSession;

import com.klutzer.wineshop.App;

public abstract class AbstractDAO {

	protected final BeanSession session;
	
	public AbstractDAO(BeanSession session) {
		this.session = session;
	}
	
	protected static <DAO> DAO getDAO(Class<DAO> daoClass) {
		return App.container().get(daoClass);
	}
	
}
