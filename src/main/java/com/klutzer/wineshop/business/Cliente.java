package com.klutzer.wineshop.business;

public class Cliente {

	private long id;
	private String nome;
	
	public Cliente() {}
	
	public Cliente(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
