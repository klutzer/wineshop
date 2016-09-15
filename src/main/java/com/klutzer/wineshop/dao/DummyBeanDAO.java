package com.klutzer.wineshop.dao;

import org.mentabean.BeanSession;

import com.klutzer.wineshop.business.Vinho;

public class DummyBeanDAO extends GenericDAO<Vinho> {

	public DummyBeanDAO(BeanSession session) {
		super(session);
	}

}
