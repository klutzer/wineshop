package com.klutzer.wineshop;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mentabean.BeanException;

import com.klutzer.wineshop.resources.BeanResponse;

@Provider
public class BeanExceptionMapper implements ExceptionMapper<BeanException> {

	@SuppressWarnings("serial")
	private static Map<String, String> msgs = new HashMap<String, String>() {{
		put("23503", "Registro possui dependências e não pode ser excluído/alterado");
		put("23505", "Este registro já existe");
	}};
	
	@Override
	public Response toResponse(BeanException ex) {

		Status status = Status.INTERNAL_SERVER_ERROR;
		String msg = "Erro interno";

		if (ex.getCause() instanceof SQLException) {
			SQLException sqlEx = (SQLException) ex.getCause();
			if (msgs.containsKey(sqlEx.getSQLState())) {
				status = Status.CONFLICT;
				msg = msgs.get(sqlEx.getSQLState());
			}else {
				msg = "Erro ao realizar operação com o banco de dados";
			}
		}

		return Response.status(status)
				.entity(new BeanResponse()
						.setMsg(msg)
						.setMsgDetail(ex.getMessage())
						.setSuccess(false))
				.type(MediaType.APPLICATION_JSON).build();
	}

}
