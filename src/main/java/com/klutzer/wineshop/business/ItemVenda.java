package com.klutzer.wineshop.business;

import java.math.BigDecimal;

public class ItemVenda {
	
	private Venda venda;
	private Vinho vinho;
	private BigDecimal qtde;
	private Money subtotal;
	
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public Vinho getVinho() {
		return vinho;
	}
	public void setVinho(Vinho vinho) {
		this.vinho = vinho;
	}
	public BigDecimal getQtde() {
		return qtde;
	}
	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}
	public Money getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Money subtotal) {
		this.subtotal = subtotal;
	}
	
	public void calcularSubtotal() {
		subtotal = vinho.getValor().multiply(qtde);
	}

}
