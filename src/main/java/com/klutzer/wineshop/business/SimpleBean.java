package com.klutzer.wineshop.business;

public abstract class SimpleBean extends BasicBean {
	
	protected String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
