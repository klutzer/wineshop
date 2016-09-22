package com.klutzer.wineshop.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.business.TipoVinho;
import com.klutzer.wineshop.dao.TipoVinhoDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Tipos de Vinhos")
@Path("/tipo")
public class TipoVinhoResource {

	private TipoVinhoDAO tipoDAO = App.container().get(TipoVinhoDAO.class);
	
	@ApiOperation("Lista todos os tipos de vinhos disponíveis")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoVinho> listAll() {
		return tipoDAO.listByExample(new TipoVinho());
	}
	
	@ApiOperation("Insere/altera um tipo")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TipoVinho save(TipoVinho tipo) {
		return tipoDAO.save(tipo);
	}
	
}
