package com.kaist.gachanono.gachanonoserver.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kaist.gachanono.gachanonoserver"})
public class AppConfig extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppConfig.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

}

