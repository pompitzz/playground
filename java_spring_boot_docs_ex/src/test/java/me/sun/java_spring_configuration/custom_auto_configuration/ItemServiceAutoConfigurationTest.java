package me.sun.java_spring_configuration.custom_auto_configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ItemServiceAutoConfiguration.class))
            .withInitializer(new ConditionEvaluationReportLoggingListener());

    @Test
    void autoConfiguration_should_be_active() throws Exception {
        runner.run(context -> {
                    assertThat(context).hasSingleBean(ItemService.class);
                    assertThat(context.getBean(ItemService.class).getName()).isEqualTo("autoConfiguration");
                });
    }

    @Test
    void autoConfiguration_should_notBeActive_when_exist_bean() throws Exception {
        runner.withUserConfiguration(ItemServiceConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(ItemService.class);
                    assertThat(context.getBean(ItemService.class).getName()).isEqualTo("configuration");
                });
    }

    @Test
    void autoConfiguration_should_beIgnored_if_ItemService_isNotPresent() throws Exception {
        runner.withClassLoader(new FilteredClassLoader(ItemService.class))
                .run(context -> assertThat(context).doesNotHaveBean(ItemService.class));
    }

    @Configuration
    static class ItemServiceConfiguration {
        @Bean
        public ItemService itemService() {
            return new ItemService("configuration");
        }
    }
}
