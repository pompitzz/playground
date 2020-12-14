package me.sun.java_spring_configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JavaSpringConfigurationApplicationTests {

	@Autowired
	private MyServerProperties myServerProperties;

	@Test
	void valueTest() {
		assertThat(myServerProperties.getHost()).isEqualTo("localhost");
		assertThat(myServerProperties.getPort()).isEqualTo(8081);
	}
}
