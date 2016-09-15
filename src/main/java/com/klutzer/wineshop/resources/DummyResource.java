package com.klutzer.wineshop.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.business.Vinho;
import com.klutzer.wineshop.dao.DummyBeanDAO;

@Api("Dummies")
@Path("/dummy")
public class DummyResource {
	
	//Getting a DAO instance from container
	private DummyBeanDAO dummyDAO = App.container().get(DummyBeanDAO.class);
	
	@ApiOperation("Insert a new dummy")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vinho add(@ApiParam("The new dummy to add") Vinho bean) {
		return dummyDAO.add(bean);
	}
	
	@ApiOperation("Add or update a dummy")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vinho save(@ApiParam("A dummy to save (upsert)") Vinho bean) {
		return dummyDAO.save(bean);
	}
	
	@ApiOperation("Update a dummy")
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vinho update(@PathParam("id") long id, @ApiParam("A dummy to update") Vinho bean) {
		bean.setId(id);
		return dummyDAO.update(bean);
	}
	
	@ApiOperation("List all dummy beans")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vinho> listAll() {
		return dummyDAO.listByExample(new Vinho());
	}
	
	@ApiOperation("Get a specific dummy by id")
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Vinho getById(@PathParam("id") long id) {
		return dummyDAO.getById(new Vinho(id));
	}
	
	@ApiOperation("Delete dummy bean by id")
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BeanResponse delete(@PathParam("id") long id) {
		return new BeanResponse().setMsg(
				dummyDAO.delete(new Vinho(id)) ? "Bean deleted" : "Nothing was deleted");
	}

}
