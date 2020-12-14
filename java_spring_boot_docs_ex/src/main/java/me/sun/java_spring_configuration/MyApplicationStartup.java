package me.sun.java_spring_configuration;

import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;

import java.util.function.Supplier;

public class MyApplicationStartup implements ApplicationStartup {
    @Override
    public StartupStep start(String name) {
        return new MyStartupStep();
    }

    static class MyStartupStep implements StartupStep {

        @Override
        public String getName() {
            return "myStartup";
        }

        @Override
        public long getId() {
            return 0;
        }

        @Override
        public Long getParentId() {
            return null;
        }

        @Override
        public StartupStep tag(String key, String value) {
            System.out.printf("### tag. key: %s, value: %s\n", key, value);
            return this;
        }

        @Override
        public StartupStep tag(String key, Supplier<String> value) {
            System.out.printf("### tag. key: %s, value: %s\n", key, value);
            return this;
        }

        @Override
        public Tags getTags() {
            System.out.println("### getTags");
            return null;
        }

        @Override
        public void end() {
            System.out.println("### end");
        }
    }
}
