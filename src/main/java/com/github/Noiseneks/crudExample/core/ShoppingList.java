package com.github.Noiseneks.crudExample.core;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.StringJoiner;

public class ShoppingList {

    private final ArrayList<ShoppingListItem> itemsList = new ArrayList<>();
    private long currentId = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingList.class);

    public void addPosition(ShoppingListItem shoppingListItem) {
        shoppingListItem.setId(currentId++);
        itemsList.add(shoppingListItem);
        LOGGER.info("Added item {} to shopping list", shoppingListItem);
    }

    @Nullable
    public ShoppingListItem getPositionById(long id) {

        ShoppingListItem item = itemsList.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (item == null) {
            LOGGER.info("Not found list position with id = {}", id);
        }

        return item;
    }

    public void updatePosition(ShoppingListItem shoppingListItem) {

        long id = shoppingListItem.getId();
        ShoppingListItem existingListPosition = getPositionById(id);

        if (existingListPosition == null) {
            return;
        }

        existingListPosition.setName(shoppingListItem.getName());
        existingListPosition.setDescription(shoppingListItem.getDescription());

        LOGGER.info("Updated item in shopping list: {}", shoppingListItem);
    }

    public void deletePositionById(long id) {

        ShoppingListItem shoppingListItem = getPositionById(id);

        if (shoppingListItem == null) {
            return;
        }

        itemsList.remove(shoppingListItem);

        LOGGER.info("Removed list entity: {}", shoppingListItem);
    }

    public String getPrettyStringView() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        itemsList.forEach(item -> {
            stringJoiner.add(item.toPrettyString());
        });
        return stringJoiner.toString();
    }
}
