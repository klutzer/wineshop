package com.klutzer.wineshop.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.business.Cliente;
import com.klutzer.wineshop.dao.ClienteDAO;

@Api("Clientes")
@Path("/cliente")
public class ClienteResource {
	
	// IoC.. Container é responsável por instanciar
	private ClienteDAO clienteDAO = App.container().get(ClienteDAO.class);
	
	@ApiOperation("Insere ou altera um cliente")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente save(@ApiParam("O cliente a ser inserido ou alterado") Cliente cliente) {
		return clienteDAO.save(cliente);
	}
	
	@ApiOperation("Lista os clientes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> listAll() {
		return clienteDAO.listByExample(new Cliente());
	}
	
	@ApiOperation("Busca um cliente específico")
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente getById(@PathParam("id") long id) {
		return clienteDAO.getById(new Cliente(id));
	}
	
	@ApiOperation("Exclui um cliente")
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BeanResponse delete(@PathParam("id") long id) {
		return new BeanResponse().setMsg(
				clienteDAO.delete(new Cliente(id)) ? "Cliente excluído" : "Nada foi excluído");
	}

}
