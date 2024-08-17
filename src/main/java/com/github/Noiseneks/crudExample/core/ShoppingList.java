package com.github.Noiseneks.crudExample.core;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.StringJoiner;

public class ShoppingList {

    private final ArrayList<ShoppingListItem> itemsList = new ArrayList<>();

    private long currentId = 1; // user-friendly counter starting from 1

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingList.class);

    /**
     * This method adds provided item to shopping list
     *
     * @param shoppingListItem — provided item
     */
    public void addPosition(ShoppingListItem shoppingListItem) {
        shoppingListItem.setId(currentId++);
        itemsList.add(shoppingListItem);
        LOGGER.info("Added item {} to shopping list", shoppingListItem);
    }


    /**
     * @param id — id of a item you want to get
     * @return item with given id
     */
    @Nullable
    public ShoppingListItem getItemById(long id) {

        ShoppingListItem item = itemsList.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (item == null) {
            LOGGER.info("Not found list position with id = {}", id);
        }

        return item;
    }

    /**
     * Update existing item in list
     *
     * @param shoppingListItem — item with parameters you want to update
     */
    public void updateItem(ShoppingListItem shoppingListItem) {

        long id = shoppingListItem.getId();
        ShoppingListItem existingListPosition = getItemById(id);

        if (existingListPosition == null) {
            return;
        }

        existingListPosition.setName(shoppingListItem.getName());
        existingListPosition.setDescription(shoppingListItem.getDescription());

        LOGGER.info("Updated item in shopping list: {}", shoppingListItem);
    }

    /**
     * Deletes item from shopping list
     *
     * @param id — id of item you want to delete
     */
    public void deleteItemById(long id) {

        ShoppingListItem shoppingListItem = getItemById(id);

        if (shoppingListItem == null) {
            return;
        }

        itemsList.remove(shoppingListItem);

        LOGGER.info("Removed list entity: {}", shoppingListItem);
    }

    /**
     * Returns pretty String view of shopping list item
     */
    public String getPrettyStringView() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        itemsList.forEach(item -> {
            stringJoiner.add(item.toPrettyString());
        });
        return stringJoiner.toString();
    }
}
