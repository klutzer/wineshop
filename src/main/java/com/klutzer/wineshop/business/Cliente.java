package com.klutzer.wineshop.business;

public class Cliente extends BasicBean {

	private String nome;
	
	public Cliente() {}
	
	public Cliente(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
