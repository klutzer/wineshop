package com.klutzer.wineshop;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.core.GenericType;

import static com.klutzer.wineshop.utils.ValuesUtils.*;

import org.junit.Test;

import com.klutzer.wineshop.business.Cliente;
import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.TipoVinho;
import com.klutzer.wineshop.business.Venda;
import com.klutzer.wineshop.business.Vinho;

public class VendaTest extends AbstractTest {

	@Test
	public void testIntegracaoPedido() throws Exception {

		// inserção dos tipos de vinho, diretamente no banco
		TipoVinho tipo1 = new TipoVinho("Tinto");
		TipoVinho tipo2 = new TipoVinho("Branco");
		session().insert(tipo1);
		session().insert(tipo2);
		commit();
		
		Vinho vinho1 = new Vinho();
		vinho1.setDescricao("Vinho Teste 1");
		vinho1.setPeso(bg(1));
		vinho1.setValor(money(100));
		vinho1.setTipo(tipo1);
		vinho1 = put("vinho", vinho1);
		assertEquals(1, vinho1.getId());
		
		Vinho vinho2 = new Vinho();
		vinho2.setDescricao("Vinho Teste 2");
		vinho2.setPeso(bg(0.5));
		vinho2.setValor(money(150));
		vinho2.setTipo(tipo2);
		vinho2 = put("vinho", vinho2);
		assertEquals(2, vinho2.getId());
		
		Cliente cliente = new Cliente();
		cliente.setNome("Érico");
		cliente = put("cliente", cliente);
		assertEquals(1, cliente.getId());
		
		// inicia itens da venda
		ItemVenda item1 = new ItemVenda();
		item1.setVinho(vinho1);
		item1.setQtde(bg(3)); // subtotal: 3 * 100 = 300,00 | peso: 3 * 1 = 3,00
		
		ItemVenda item2 = new ItemVenda();
		item2.setVinho(vinho2);
		item2.setQtde(bg(4)); // subtotal: 4 * 150 = 600,00 | peso: 4 * 0.5 = 2,00 
		
		// inicia a venda
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setDistancia(bg(400));
		venda.add(item1);
		venda.add(item2);
		venda = post("venda", venda);

		// conferindo dados
		assertEquals(1, venda.getId());
		assertNotNull(venda.getDataHora());
		
		// conferindo itens
		assertEquals(2, venda.getItens().size());
		assertEquals(vinho1, venda.getItens().get(0).getVinho());
		assertEquals(vinho2, venda.getItens().get(1).getVinho());
		//conferindo que itens vieram sem a venda, pois ocorreria recursão infinita ao serializar
		assertNull(venda.getItens().get(0).getVenda());
		assertNull(venda.getItens().get(1).getVenda());
		
		// conferindo totais
		assertEquals(money(900), venda.getValorItens());
		assertEquals(0, bg(5).compareTo(venda.getPesoTotal()));
		assertEquals(money(25 * 4), venda.getTotalFrete()); //25 ref total do peso * 5,00 | 4 ref 400/100 
		assertEquals(money(900 + 100), venda.getTotalVenda());
		
		// conferindo listagem
		List<Venda> vendas = getList("venda", new GenericType<List<Venda>>(){});
		assertEquals(1, vendas.size());
		assertEquals(2, vendas.get(0).getItens().size());
		assertEquals("Vinho Teste 1", vendas.get(0).getItens().get(0).getVinho().getDescricao());
	}
	
}
