package com.github.Noiseneks.crudExample.core.repository;

import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingListRepository {

    private final ShoppingListRepositoryAccessor shoppingListRepositoryAccessor;

    public ShoppingListRepository(ShoppingListRepositoryAccessor shoppingListRepositoryAccessor) {
        this.shoppingListRepositoryAccessor = shoppingListRepositoryAccessor;
    }

    public ShoppingListItem getById(Long id) {
        return shoppingListRepositoryAccessor
                .findById(id)
                .orElse(null);
    }

    public void delete(ShoppingListItem shoppingListItem) {
        shoppingListRepositoryAccessor.delete(shoppingListItem);
    }

    public ShoppingListItem saveAndFlush(ShoppingListItem shoppingListItem) {
        return shoppingListRepositoryAccessor.saveAndFlush(shoppingListItem);
    }

    public List<ShoppingListItem> getAllItems() {
        return shoppingListRepositoryAccessor.findAll();
    }

}
