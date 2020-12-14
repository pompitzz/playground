package me.sun.java_spring_configuration.custom_auto_configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(ItemService.class)
@Configuration
public class ItemServiceAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public ItemService itemService() {
        return new ItemService("autoConfiguration");
    }
}
