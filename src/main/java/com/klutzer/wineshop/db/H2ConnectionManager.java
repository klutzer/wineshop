package com.klutzer.wineshop.db;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.H2BeanSession;
import org.mentabean.util.ScriptRunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class H2ConnectionManager extends ConnectionManager {

	private boolean insertInitialData;
	
	public H2ConnectionManager(boolean insertInitialData) {
		this.insertInitialData = insertInitialData;
	}
	
	@Override
	public HikariDataSource createPool() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
		long time = System.nanoTime();
		config.addDataSourceProperty("URL", "jdbc:h2:mem:dummytest"+time);
		config.setConnectionTimeout(5000);
		return new HikariDataSource(config);
	}
	
	@Override
	public Class<? extends BeanSession> getSessionClass() {
		return H2BeanSession.class;
	}
	
	@Override
	public void preRun(BeanSession session) {
		
		try (Reader initial = createReader("initial_script.sql");
			 Reader initialData = createReader("initial_data.sql");) {
			ScriptRunner script = new ScriptRunner(session.getConnection());
			script.runScript(initial);
			if (insertInitialData) {
				script.runScript(initialData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Reader createReader(String file) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		return new InputStreamReader(is, Charset.forName("utf-8"));
	}

}
