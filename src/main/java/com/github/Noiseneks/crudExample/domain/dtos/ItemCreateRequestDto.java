package com.github.Noiseneks.crudExample.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCreateRequestDto {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public ItemCreateRequestDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemCreateRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
