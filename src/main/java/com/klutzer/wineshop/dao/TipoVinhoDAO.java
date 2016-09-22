package com.klutzer.wineshop.dao;

import org.mentabean.BeanSession;

import com.klutzer.wineshop.business.TipoVinho;

public class TipoVinhoDAO extends GenericDAO<TipoVinho> {

	public TipoVinhoDAO(BeanSession session) {
		super(session);
	}

}
