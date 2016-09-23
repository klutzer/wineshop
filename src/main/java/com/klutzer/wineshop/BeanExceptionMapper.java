package com.klutzer.wineshop;

import java.sql.SQLException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mentabean.BeanException;

import com.klutzer.wineshop.resources.BeanResponse;

@Provider
public class BeanExceptionMapper implements ExceptionMapper<BeanException> {

	@Override
	public Response toResponse(BeanException ex) {

		Status status = Status.INTERNAL_SERVER_ERROR;
		String msg = "Erro interno: "+ex.getMessage();

		if (ex.getCause() instanceof SQLException) {
			SQLException sqlEx = (SQLException) ex.getCause();
			if (sqlEx.getErrorCode() == 23503) {
				status = Status.CONFLICT;
				msg = "Registro possui dependências e não pode ser excluído/alterado";
			}
		}

		return Response.status(status)
				.entity(new BeanResponse()
						.setMsg(msg)
						.setSuccess(false))
				.type(MediaType.APPLICATION_JSON).build();
	}

}
