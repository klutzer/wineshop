package com.klutzer.wineshop.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.business.Venda;
import com.klutzer.wineshop.dao.VendaDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Vendas/Pedidos")
@Path("/venda")
public class VendaResource {

	private VendaDAO vendaDAO = App.container().get(VendaDAO.class);
	
	@ApiOperation("Insere uma venda")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Venda add(Venda venda) {
		return vendaDAO.add(venda);
	}
	
	@ApiOperation("Calcula os totais da venda informada")
	@POST
	@Path("calcularTotais")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Venda calcularTotais(Venda venda) {
		venda.calcularTotais();
		return venda;
	}
	
	@ApiOperation("Lista todas as vendas")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Venda> listAll() {
		return vendaDAO.listAll();
	}
}
