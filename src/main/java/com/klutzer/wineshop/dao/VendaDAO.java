package com.klutzer.wineshop.dao;

import org.mentabean.BeanSession;

import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.Venda;

public class VendaDAO extends GenericDAO<Venda> {

	public VendaDAO(BeanSession session) {
		super(session);
	}
	
	@Override
	public Venda add(Venda venda) {
		for (ItemVenda item : venda.getItens()) {
			item.setVenda(venda);
			session.insert(item);
		}
		venda.calcularTotais();
		session.insert(venda);
		return venda;
	}

}
