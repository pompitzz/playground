package me.sun.java_spring_jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class JavaSpringJpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(JavaSpringJpaApplication.class)
				.web(WebApplicationType.SERVLET)
				.run(args);
	}

}
