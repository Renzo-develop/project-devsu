package com.devsu.msusuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@SpringBootApplication
public class MsUsuarioApplication {

	@Autowired
	private DataSource dataSource;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("data.sql"));
		resourceDatabasePopulator.execute(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(MsUsuarioApplication.class, args);
	}

}
