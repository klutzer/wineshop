package com.klutzer.wineshop;

import static org.junit.Assert.*;
import static com.klutzer.wineshop.utils.ValuesUtils.*;

import org.junit.Test;

import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.Venda;
import com.klutzer.wineshop.business.Vinho;

public class CalculoVendaTest {

	@Test
	public void testaCalculoVenda() throws Exception {
		
		Vinho vinho1 = new Vinho();
		vinho1.setDescricao("Teste1");
		vinho1.setPeso(bg(0.5));
		vinho1.setValor(money(50));
		
		Vinho vinho2 = new Vinho();
		vinho2.setDescricao("Teste2");
		vinho2.setPeso(bg(1.5));
		vinho2.setValor(money(30));
		
		ItemVenda item1 = new ItemVenda();
		item1.setVinho(vinho1);
		item1.setQtde(bg(2));
		
		ItemVenda item2 = new ItemVenda();
		item2.setVinho(vinho2);
		item2.setQtde(bg(4));
		
		Venda venda = new Venda();
		venda.add(item1);
		venda.add(item2);
		venda.setDistancia(bg(100));
		venda.calcularTotais();
		
		// 1,000 + 6,000
		assertEquals(0, bg(7).compareTo(venda.getPesoTotal()));
		// 7 * 5
		assertEquals(money(35), venda.getTotalFrete());
		assertEquals(money(255), venda.getTotalVenda());
	}
	
	@Test
	public void testaCalculoVendaComDistanciaGrande() throws Exception {
		
		Vinho vinho1 = new Vinho();
		vinho1.setDescricao("Teste1");
		vinho1.setPeso(bg(0.5));
		vinho1.setValor(money(50));
		
		Vinho vinho2 = new Vinho();
		vinho2.setDescricao("Teste2");
		vinho2.setPeso(bg(1.5));
		vinho2.setValor(money(30));
		
		ItemVenda item1 = new ItemVenda();
		item1.setVinho(vinho1);
		item1.setQtde(bg(2));
		
		ItemVenda item2 = new ItemVenda();
		item2.setVinho(vinho2);
		item2.setQtde(bg(4));
		
		Venda venda = new Venda();
		venda.add(item1);
		venda.add(item2);
		venda.setDistancia(bg(200));
		venda.calcularTotais();
		
		// 1,000 + 6,000
		assertEquals(0, bg(7).compareTo(venda.getPesoTotal()));
		// 7 * 5 * (200/100)
		assertEquals(money(70), venda.getTotalFrete());
		// 220,00 (total da venda) + 70,00 (frete)
		assertEquals(money(290), venda.getTotalVenda());
	}
	
}
