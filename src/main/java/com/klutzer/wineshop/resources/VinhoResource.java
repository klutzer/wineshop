package com.klutzer.wineshop.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.business.Vinho;
import com.klutzer.wineshop.dao.VinhoDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Vinhos")
@Path("/vinho")
public class VinhoResource {

	private VinhoDAO vinhoDAO = App.container().get(VinhoDAO.class);
	
	@ApiOperation("Lista todos os vinhos cadastrados")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vinho> listAll() {
		return vinhoDAO.listAll();
	}
	
	@ApiOperation("Insere/altera um vinho")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vinho add(Vinho vinho) {
		return vinhoDAO.save(vinho);
	}
}
