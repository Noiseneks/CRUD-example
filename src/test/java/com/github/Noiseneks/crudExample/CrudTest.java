package com.github.Noiseneks.crudExample;

import com.github.Noiseneks.crudExample.core.ShoppingList;
import com.github.Noiseneks.crudExample.core.ShoppingListItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {

    @Test
    public void testCreate() {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Milk")
                .setDescription("1 liter, 3.2%"));

        ShoppingListItem item = shoppingList.getItemById(1);

        assertNotNull(item);
    }

    @Test
    public void testRead() {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Milk")
                .setDescription("1 liter, 3.2%"));

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Tea")
                .setDescription("Green"));

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Water"));

        ShoppingListItem item1 = shoppingList.getItemById(1);
        assertNotNull(item1);

        ShoppingListItem item2 = shoppingList.getItemById(2);
        assertNotNull(item2);

        ShoppingListItem item3 = shoppingList.getItemById(3);
        assertNotNull(item3);

        ShoppingListItem item4 = shoppingList.getItemById(4);
        assertNull(item4);
    }

    @Test
    public void testUpdate() {
        ShoppingList shoppingList = new ShoppingList();

        String testDescription = "2 liters";

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Water"));

        ShoppingListItem item = shoppingList.getItemById(1);

        assertNotNull(item);
        assertNotEquals(testDescription, item.getDescription());

        item.setDescription(testDescription);
        shoppingList.updateItem(item);


        ShoppingListItem newItem = shoppingList.getItemById(1);

        assertNotNull(newItem);
        assertEquals(testDescription, newItem.getDescription());
    }

    @Test
    public void testDelete() {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.addPosition(new ShoppingListItem()
                .setName("Tea")
                .setDescription("Green"));

        ShoppingListItem item = shoppingList.getItemById(1);
        assertNotNull(item);

        shoppingList.deleteItemById(1);
        ShoppingListItem newItem = shoppingList.getItemById(1);
        assertNull(newItem);
    }

}
