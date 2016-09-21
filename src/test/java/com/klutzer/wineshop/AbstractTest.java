package com.klutzer.wineshop;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.mentabean.BeanSession;
import org.mentabean.util.SQLUtils;

import com.klutzer.wineshop.App;
import com.klutzer.wineshop.ContextListener;
import com.klutzer.wineshop.JsonProvider;
import com.klutzer.wineshop.db.H2ConnectionManager;

@SuppressWarnings("deprecation")
public class AbstractTest extends JerseyTest {

	protected final BeanSession session() {
		return App.container().get(BeanSession.class);
	}
	
	@Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
		return new GrizzlyWebTestContainerFactory();
	}
	
	@Override
	protected DeploymentContext configureDeployment() {
		
		//for parallel tests
		forceSet(TestProperties.CONTAINER_PORT, "0");
		
		return ServletDeploymentContext
				.forServlet(new ServletContainer(new App(new H2ConnectionManager(false))))
				.addListener(ContextListener.class)
				.build();
	}
	
	@Override
	protected void configureClient(ClientConfig config) {
		//Doesn't work :(
		//config.register(LoggingFeature.class);
		config.register(new LoggingFilter(Logger.getLogger(getClass().getSimpleName()), true));
		config.register(JsonProvider.class);
	}
	
	protected void commit() {
		SQLUtils.commitTransaction(session().getConnection());
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T post(String path, T bean) {
		return (T) target(path).request().post(Entity.json(bean), bean.getClass());
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T put(String path, T bean) {
		return (T) target(path).request().put(Entity.json(bean), bean.getClass());
	}
	
	protected <T> T getSingle(String path, Class<T> responseType) {
		return target(path).request().get(responseType);
	}
	
	protected <T> List<T> getList(String path, GenericType<List<T>> type) {
		return target(path).request().get(type);
	}
	
	protected <T> T delete(String path, Class<T> responseType) {
		return target(path).request().delete(responseType);
	}
	
}
