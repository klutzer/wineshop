package com.klutzer.wineshop.dao;

import java.util.List;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.QueryBuilder;
import org.mentabean.jdbc.QueryBuilder.Alias;

import com.klutzer.wineshop.business.TipoVinho;
import com.klutzer.wineshop.business.Vinho;

public class VinhoDAO extends GenericDAO<Vinho> {

	public VinhoDAO(BeanSession session) {
		super(session);
	}

	public List<Vinho> listAll() {
		
		QueryBuilder builder = session.buildQuery();
		Alias<Vinho> aliasVinho = builder.aliasTo(Vinho.class);
		Alias<TipoVinho> aliasTipo = builder.aliasTo(TipoVinho.class);
		
		// proxy para construção da query
		Vinho vinho = aliasVinho.pxy();
		
		return builder.select(aliasVinho, aliasTipo)
				.from(aliasVinho)
				// faz join com os tipos e popula cada tipo dentro do respectivo vinho
				.join(aliasTipo).pkOf(aliasTipo).in(aliasVinho).inProperty(vinho.getTipo())
				.orderBy()
				.asc(aliasVinho, vinho.getTipo().getId(), vinho.getDescricao())
				.executeQuery();
				
	}
	
}
