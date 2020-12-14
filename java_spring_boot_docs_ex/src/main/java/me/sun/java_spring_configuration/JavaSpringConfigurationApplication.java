package me.sun.java_spring_configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Properties;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class JavaSpringConfigurationApplication {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("my.server.name", "defaultProperty");
        properties.setProperty("spring.profiles.active", "server4");
//        properties.setProperty("debug", "true");
        new SpringApplicationBuilder(JavaSpringConfigurationApplication.class)
                .listeners(new EventListenerService())
                .properties(properties)
//				.applicationStartup(new MyApplicationStartup())
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public ApplicationRunner applicationRunner(MyServerProperties myServerProperties) {
        return args -> {
            System.out.println(myServerProperties.getName());
        };
    }

    @EventListener
    public void applicationStartedEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("=========== ApplicationStartedEvent ============");
    }

    @EventListener
    public void applicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("=========== ApplicationReadyEvent ============");
    }
}
