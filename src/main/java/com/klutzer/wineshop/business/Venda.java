package com.klutzer.wineshop.business;

import static com.klutzer.wineshop.utils.ValuesUtils.bg;
import static com.klutzer.wineshop.utils.ValuesUtils.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Venda extends BasicBean {

	private Cliente cliente;
	private List<ItemVenda> itens;
	private BigDecimal distancia;
	private BigDecimal pesoTotal;
	private Money totalFrete;
	private Money totalVenda;
	
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
	
	public void add(ItemVenda item) {
		if (itens == null) {
			itens = new ArrayList<>();
		}
		item.setVenda(this);
		itens.add(item);
	}
	
	/**
	 * Calcula o valor total do pedido.<br>
	 * O cálculo é feito utilizando Money e BigDecimal, para evitar os
	 * problemas de arredondamento do Java, ao usar double ou Double
	 */
	public void calcularTotais() {
		totalVenda = Money.ZERO;
		pesoTotal = BigDecimal.ZERO;
		for (ItemVenda item : itens) {
			item.calcularSubtotal();
			totalVenda = totalVenda.plus(item.getSubtotal());
			pesoTotal = pesoTotal.add(item.getVinho().getPeso().multiply(item.getQtde()));
		}
		totalFrete = money(pesoTotal.multiply(bg(5)));
		if (distancia.compareTo(bg(100)) > 0) {
			totalFrete = money(totalFrete.multiply(distancia).getAmount()
					.divide(bg(100), 5, RoundingMode.HALF_DOWN));
		}
		totalVenda = totalVenda.plus(totalFrete);
	}
	
}
