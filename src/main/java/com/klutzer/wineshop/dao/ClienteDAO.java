package com.klutzer.wineshop.dao;

import org.mentabean.BeanSession;

import com.klutzer.wineshop.business.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO(BeanSession session) {
		super(session);
	}

}
