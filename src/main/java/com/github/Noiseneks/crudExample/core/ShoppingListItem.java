package com.github.Noiseneks.crudExample.core;

import org.apache.commons.lang3.StringUtils;

public class ShoppingListItem {
    private Long id;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public ShoppingListItem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingListItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ShoppingListItem setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String toPrettyString() {
        String result = this.getId() + ". " + this.getName();
        if (StringUtils.isNotEmpty(result)) {
            result += "\n " + this.getDescription();
        }
        return result;
    }
}
