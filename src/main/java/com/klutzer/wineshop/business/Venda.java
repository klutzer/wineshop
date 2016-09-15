package com.klutzer.wineshop.business;

import java.math.BigDecimal;
import java.util.List;

public class Venda {

	private long id;
	private Cliente cliente;
	private List<ItemVenda> itens;
	private BigDecimal distancia;
	private BigDecimal pesoTotal;
	private Money totalFrete;
	private Money totalVenda;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<ItemVenda> getItens() {
		return itens;
	}
	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	public BigDecimal getDistancia() {
		return distancia;
	}
	public void setDistancia(BigDecimal distancia) {
		this.distancia = distancia;
	}
	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}
	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	public Money getTotalFrete() {
		return totalFrete;
	}
	public void setTotalFrete(Money totalFrete) {
		this.totalFrete = totalFrete;
	}
	public Money getTotalVenda() {
		return totalVenda;
	}
	public void setTotalVenda(Money totalVenda) {
		this.totalVenda = totalVenda;
	}
	
}
