package com.klutzer.wineshop.dao;

import java.util.List;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.QueryBuilder;
import org.mentabean.jdbc.QueryBuilder.Alias;
import org.mentabean.sql.conditions.Equals;

import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.Venda;
import com.klutzer.wineshop.business.Vinho;

public class ItemVendaDAO extends AbstractDAO {

	public ItemVendaDAO(BeanSession session) {
		super(session);
	}
	
	public List<ItemVenda> listByVenda(Venda venda) {
		
		QueryBuilder builder = session.buildQuery();
		Alias<ItemVenda> aliasItem = builder.aliasTo(ItemVenda.class);
		Alias<Vinho> aliasVinho = builder.aliasTo(Vinho.class);
		
		ItemVenda item = aliasItem.pxy();
		aliasItem.setReturnMinus(item.getVenda().getId());
		
		return builder.select(aliasItem, aliasVinho)
				.from(aliasItem)
				.join(aliasVinho).pkOf(aliasVinho).in(aliasItem).inProperty(item.getVinho())
				.where()
				.clause(item.getVenda().getId())
				.condition(new Equals(venda.getId()))
				.executeQuery();
	}

}
