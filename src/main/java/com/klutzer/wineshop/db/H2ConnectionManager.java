package com.klutzer.wineshop.db;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.mentabean.BeanSession;
import org.mentabean.jdbc.H2BeanSession;
import org.mentabean.util.ScriptRunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class H2ConnectionManager extends ConnectionManager {

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
		ScriptRunner script = new ScriptRunner(session.getConnection());
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("initial_script.sql");) {
			script.runScript(new InputStreamReader(in, Charset.forName("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
