package com.github.Noiseneks.crudExample.domain.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullListResponse {
    private List<ShoppingListItem> items;

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public FullListResponse setItems(List<ShoppingListItem> items) {
        this.items = items;
        return this;
    }
}
