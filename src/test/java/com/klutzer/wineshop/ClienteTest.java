package com.klutzer.wineshop;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;

import org.junit.Test;

import com.klutzer.wineshop.business.Cliente;

public class ClienteTest extends AbstractTest {

	@Test
	public void testAdd() {
		Cliente cli = new Cliente();
		cli.setNome("Érico");
		
		Cliente resp = target("cliente")
				.request()
				.put(Entity.json(cli), Cliente.class);
		assertEquals(1, resp.getId());
	}
	
}
