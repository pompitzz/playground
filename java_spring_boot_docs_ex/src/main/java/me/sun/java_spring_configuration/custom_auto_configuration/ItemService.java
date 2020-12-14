package me.sun.java_spring_configuration.custom_auto_configuration;

import lombok.Getter;

@Getter
public class ItemService {
    private String name;

    public ItemService(String name) {
        this.name = name;
    }
}
