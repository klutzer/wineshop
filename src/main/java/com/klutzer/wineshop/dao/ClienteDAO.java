package com.klutzer.wineshop.dao;

import java.util.List;

import org.mentabean.BeanSession;
import org.mentabean.util.OrderBy;
import org.mentabean.util.PropertiesProxy;

import com.klutzer.wineshop.business.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO(BeanSession session) {
		super(session);
	}
	
	public List<Cliente> listAll() {
		Cliente proxy = PropertiesProxy.create(Cliente.class);
		return session.loadList(new Cliente(), new OrderBy().orderByAsc(proxy.getNome()));
	}

}
