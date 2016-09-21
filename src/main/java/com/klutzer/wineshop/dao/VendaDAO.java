package com.klutzer.wineshop.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.mentabean.BeanSession;
import org.mentabean.jdbc.QueryBuilder;
import org.mentabean.jdbc.QueryBuilder.Alias;
import org.mentabean.util.PropertiesProxy;

import com.klutzer.wineshop.business.Cliente;
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
		venda.setDataHora(new DateTime());
		venda.calcularTotais();
		session.insert(venda);
		return venda;
	}
	
	public List<Venda> listAll() {
		
		QueryBuilder builder = session.buildQuery();
		
		Alias<Venda> aliasVenda = builder.aliasTo(Venda.class);
		Alias<Cliente> aliasCliente = builder.aliasTo(Cliente.class);
		
		Venda venda = aliasVenda.pxy();
		
		List<Venda> vendas = builder.select(aliasVenda, aliasCliente)
				.from(aliasVenda)
				.join(aliasCliente).pkOf(aliasCliente).in(aliasVenda).inProperty(venda.getCliente())
				.orderBy()
				.desc(aliasVenda, venda.getDataHora())
				.executeQuery();
		
		ItemVenda itemProxy = PropertiesProxy.create(ItemVenda.class);
		for (Venda v : vendas) {
			ItemVenda proto = new ItemVenda();
			proto.setVenda(v);
			v.setItens(session.loadListMinus(proto, itemProxy.getVenda().getId()));
		}
		
		return vendas;
	}

}
