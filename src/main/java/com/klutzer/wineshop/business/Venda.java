package com.klutzer.wineshop.business;

import static com.klutzer.wineshop.utils.ValuesUtils.bg;
import static com.klutzer.wineshop.utils.ValuesUtils.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Venda extends BasicBean {

	private DateTime dataHora;
	private Cliente cliente;
	private List<ItemVenda> itens;
	private BigDecimal distancia;
	private BigDecimal pesoTotal;
	private Money valorItens;
	private Money totalFrete;
	private Money totalVenda;
	
	public DateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(DateTime dataHora) {
		this.dataHora = dataHora;
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
	public Money getValorItens() {
		return valorItens;
	}
	public void setValorItens(Money valorItens) {
		this.valorItens = valorItens;
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
		valorItens = Money.ZERO;
		pesoTotal = BigDecimal.ZERO;
		for (ItemVenda item : itens) {
			item.calcularSubtotal();
			valorItens = valorItens.plus(item.getSubtotal());
			pesoTotal = pesoTotal.add(item.getVinho().getPeso().multiply(item.getQtde()));
		}
		totalFrete = money(pesoTotal.multiply(bg(5)));
		if (distancia.compareTo(bg(100)) > 0) {
			totalFrete = money(totalFrete.multiply(distancia).getAmount()
					.divide(bg(100), 5, RoundingMode.HALF_DOWN));
		}
		totalVenda = valorItens.plus(totalFrete);
	}
	
}
