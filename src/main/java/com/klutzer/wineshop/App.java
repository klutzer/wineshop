package com.klutzer.wineshop;

import static org.mentacontainer.impl.SingletonFactory.*;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.sql.Connection;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.mentabean.BeanConfig;
import org.mentabean.BeanManager;
import org.mentabean.BeanSession;
import org.mentabean.util.PropertiesProxy;
import org.mentacontainer.Container;
import org.mentacontainer.Scope;
import org.mentacontainer.impl.MentaContainer;

import com.klutzer.wineshop.business.Cliente;
import com.klutzer.wineshop.business.ItemVenda;
import com.klutzer.wineshop.business.TipoVinho;
import com.klutzer.wineshop.business.Venda;
import com.klutzer.wineshop.business.Vinho;
import com.klutzer.wineshop.db.ConnectionManager;
import com.klutzer.wineshop.db.DBTypes;
import com.klutzer.wineshop.db.H2ConnectionManager;

@SuppressWarnings("deprecation")
@ApplicationPath("/api/*")
public class App extends ResourceConfig {

	private static Container container;
	private final BeanManager beanManager;
	private final ConnectionManager connectionManager;
	
	public App(ConnectionManager connectionManager) {
		
		this.connectionManager = connectionManager;
		this.beanManager = new BeanManager();
		container = new MentaContainer();
		
		//Mapping recursively by package name
		packages(getClass().getPackage().getName());
		
		setUpSwagger();
		beans();
		ioc();
		executePreRun();
	}
	
	public App() {
		this(new H2ConnectionManager(true));
		register(new LoggingFilter(Logger.getLogger(getClass().getSimpleName()), true));
	}
	
	public static Container container() {
		return container;
	}
	
	public static void releaseAndShutdown() {
		ConnectionManager connectionManager = container.get(ConnectionManager.class);
		container.clear(Scope.THREAD);
		container.clear(Scope.SINGLETON);
		connectionManager.shutdown();
	}
	
	private void ioc() {
		container.ioc(ConnectionManager.class, singleton(connectionManager));
		container.ioc(BeanManager.class, singleton(beanManager));
		container.ioc(Connection.class, connectionManager, Scope.THREAD);
		container.ioc(BeanSession.class, connectionManager.getSessionClass(), Scope.THREAD)
			.addConstructorDependency(BeanManager.class)
			.addConstructorDependency(Connection.class);
		container.autowire(BeanSession.class);
	}
	
	private void executePreRun() {
		BeanSession session = container.get(BeanSession.class);
		connectionManager.preRun(session);
		container.clear(Scope.THREAD);
	}
	
	// configuração programática de beans no MentaBean.. 
	private void beans() {
		
		// proxy, que é ótimo pra refatorações futuras
		Vinho vinho = PropertiesProxy.create(Vinho.class);
		BeanConfig vinhoCfg = new BeanConfig(Vinho.class, "vinhos")
				.pk(vinho.getId(), "idvinhos", DBTypes.AUTOINCREMENT)
				.field(vinho.getTipo().getId(), "idtipos", DBTypes.LONG)
				.field(vinho.getDescricao(), DBTypes.STRING)
				.field(vinho.getPeso(), DBTypes.BIGDECIMAL)
				.field(vinho.getValor(), DBTypes.MONEY);
		
		TipoVinho tipo = PropertiesProxy.create(TipoVinho.class);
		BeanConfig tipoCfg = new BeanConfig(TipoVinho.class, "tipos")
				.pk(tipo.getId(), "idtipos", DBTypes.AUTOINCREMENT)
				.field(tipo.getDescricao(), DBTypes.STRING);
		
		Cliente cliente = PropertiesProxy.create(Cliente.class);
		BeanConfig clienteCfg = new BeanConfig(Cliente.class, "clientes")
				.pk(cliente.getId(), "idclientes", DBTypes.AUTOINCREMENT)
				.field(cliente.getNome(), DBTypes.STRING);
		
		ItemVenda item = PropertiesProxy.create(ItemVenda.class);
		BeanConfig itemCfg = new BeanConfig(ItemVenda.class, "itens")
				.pk(item.getVenda().getId(), "idvendas", DBTypes.LONG)
				.pk(item.getVinho().getId(), "idvinhos", DBTypes.LONG)
				.field(item.getQtde(), DBTypes.BIGDECIMAL)
				.field(item.getSubtotal(), DBTypes.MONEY);
		
		Venda venda = PropertiesProxy.create(Venda.class);
		BeanConfig vendaCfg = new BeanConfig(Venda.class, "vendas")
				.pk(venda.getId(), "idvendas", DBTypes.AUTOINCREMENT)
				.field(venda.getCliente().getId(), "idclientes", DBTypes.LONG)
				.field(venda.getDistancia(), DBTypes.BIGDECIMAL)
				.field(venda.getPesoTotal(), DBTypes.BIGDECIMAL)
				.field(venda.getTotalFrete(), DBTypes.MONEY)
				.field(venda.getTotalVenda(), DBTypes.MONEY);
		
		beanManager.addBeanConfig(vinhoCfg);
		beanManager.addBeanConfig(tipoCfg);
		beanManager.addBeanConfig(clienteCfg);
		beanManager.addBeanConfig(itemCfg);
		beanManager.addBeanConfig(vendaCfg);
	}
	
	private void setUpSwagger() {
		
		registerClasses(SwaggerSerializers.class, ApiListingResource.class);
		
		io.swagger.jaxrs.config.BeanConfig conf = new io.swagger.jaxrs.config.BeanConfig();
		conf.setTitle("WineShop API");
		conf.setDescription("Documentação interativa da API WineShop");
        conf.setVersion("v1");
        conf.setResourcePackage(getClass().getPackage().getName()+".resources");
        conf.setPrettyPrint(true);
        conf.setBasePath("/wineshop/api");
        conf.setScan(true);
	}
	
}
