package com.klutzer.wineshop.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.mentabean.BeanSession;
import org.mentabean.jdbc.QueryBuilder;
import org.mentabean.jdbc.QueryBuilder.Alias;

import com.klutzer.wineshop.business.Cliente;
import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.Venda;

public class VendaDAO extends GenericDAO<Venda> {

	public VendaDAO(BeanSession session) {
		super(session);
	}
	
	@Override
	public Venda add(Venda venda) {
		
		venda.setDataHora(new DateTime());
		venda.calcularTotais();
		session.insert(venda);
		
		for (ItemVenda item : venda.getItens()) {
			item.setVenda(venda);
			session.insert(item);
			// eliminando venda do item para não causar recursão infinita
			item.setVenda(null);
		}
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
		
		ItemVendaDAO itemDAO = getDAO(ItemVendaDAO.class);
		for (Venda v : vendas) {
			v.setItens(itemDAO.listByVenda(v));
		}
		
		return vendas;
	}

}
