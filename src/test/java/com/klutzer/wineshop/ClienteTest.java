package com.klutzer.wineshop;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.core.GenericType;

import org.junit.Test;

import com.klutzer.wineshop.business.Cliente;
import com.klutzer.wineshop.resources.BeanResponse;

public class ClienteTest extends AbstractTest {

	private static GenericType<List<Cliente>> genericListCliente = new GenericType<List<Cliente>>(){};
	
	@Test
	public void testAll() {
		
		Cliente cli = new Cliente();
		cli.setNome("Érico");
		
		// deve inserir
		Cliente resp = put("cliente", cli);
		assertEquals(1, resp.getId());
		
		List<Cliente> list = getList("cliente", genericListCliente);
		assertEquals(1, list.size());
		
		// deve alterar, pois já possui o código
		cli = new Cliente(1);
		cli.setNome("Érico Knapp Lutzer");
		resp = put("cliente", cli);
		assertEquals(1, resp.getId());
		
		list = getList("cliente", genericListCliente);
		assertEquals(1, list.size());
		assertEquals("Érico Knapp Lutzer", list.get(0).getNome());
		
		// deve salvar, pois não possui id
		cli = new Cliente();
		cli.setNome("Jéssica Silva Berni");
		resp = put("cliente", cli);
		assertEquals(2, resp.getId());
		
		// ... e deve retornar dois clientes
		list = getList("cliente", genericListCliente);
		assertEquals(2, list.size());
		assertEquals("Érico Knapp Lutzer", list.get(0).getNome());
		assertEquals("Jéssica Silva Berni", list.get(1).getNome());
		
		// buscando apenas pelo id deve voltar o cliente certo
		cli = getSingle("cliente/1", Cliente.class);
		assertEquals(1, cli.getId());
		assertEquals("Érico Knapp Lutzer", cli.getNome());
		
		// excluindo o cliente
		BeanResponse br = delete("cliente/1", BeanResponse.class);
		assertEquals("Cliente excluído", br.getMsg());
		
		// verificando que ele foi excluído
		cli = getSingle("cliente/1", Cliente.class);
		assertNull(cli);
		
		// excluindo nada
		br = delete("cliente/1", BeanResponse.class);
		assertEquals("Nada foi excluído", br.getMsg());
	}
	
}
