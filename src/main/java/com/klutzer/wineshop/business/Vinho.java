package com.klutzer.wineshop.business;

import java.math.BigDecimal;

public class Vinho extends SimpleBean {
	
	private TipoVinho tipo;
	private BigDecimal peso;
	private Money valor;
	
	public Vinho() {}
	
	public Vinho(long id) {
		this.id = id;
	}

	public TipoVinho getTipo() {
		return tipo;
	}

	public void setTipo(TipoVinho tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public Money getValor() {
		return valor;
	}

	public void setValor(Money valor) {
		this.valor = valor;
	}

}
