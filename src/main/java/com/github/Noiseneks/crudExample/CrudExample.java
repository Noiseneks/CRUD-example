package com.github.Noiseneks.crudExample;

import com.github.Noiseneks.crudExample.core.ShoppingList;
import com.github.Noiseneks.crudExample.core.ShoppingListItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class CrudExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingList shoppingList = new ShoppingList();

        String command = "";

        while (!command.equalsIgnoreCase("exit")) {
            System.out.println("Your current shopping list:\n" + shoppingList.getPrettyStringView());
            System.out.println("Commands:");
            System.out.println("add - add item to list");
            System.out.println("get - view a specific item on the list");
            System.out.println("edit - modify a item from the list");
            System.out.println("delete - delete a item from the list ");
            System.out.println("Enter the command: ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "add" -> {
                    System.out.println("Enter the item name:");
                    String name = scanner.nextLine();
                    if (StringUtils.isEmpty(name)) {
                        System.out.println("Item name can't be empty");
                        return;
                    }
                    System.out.println("Enter description (optional): ");
                    String description = scanner.nextLine();
                    shoppingList.addPosition(new ShoppingListItem()
                            .setName(name)
                            .setDescription(description));
                }
                case "get" -> {
                    System.out.println("Enter item ID: ");
                    try {

                        long id = Long.parseLong(scanner.nextLine());
                        ShoppingListItem shoppingListItem = shoppingList.getPositionById(id);

                        if (shoppingListItem == null) {
                            System.out.println("Can't find item with given ID");
                            return;
                        }

                        System.out.println(shoppingListItem.toPrettyString());

                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Not a number");
                        return;
                    }
                }
                case "edit" -> {
                    System.out.println("Enter item ID: ");
                    try {
                        long id = Long.parseLong(scanner.nextLine());
                        ShoppingListItem shoppingListItem = shoppingList.getPositionById(id);

                        if (shoppingListItem == null) {
                            System.out.println("Can't find item with given ID");
                            return;
                        }

                        System.out.println("Enter a new name (optional):");

                        String newName = scanner.nextLine();

                        System.out.println("Enter a new description (optional):");

                        String newDescription = scanner.nextLine();

                        shoppingListItem
                                .setName(StringUtils.isEmpty(newName) ? shoppingListItem.getName() : newName)
                                .setDescription(StringUtils.isEmpty(newDescription) ? shoppingListItem.getDescription() : newDescription);

                        shoppingList.updatePosition(shoppingListItem);

                        System.out.println("Item updated");
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Not a number");
                        return;
                    }
                }
                case "delete" -> {
                    System.out.println("Enter item ID: ");
                    try {
                        long id = Long.parseLong(scanner.nextLine());
                        ShoppingListItem shoppingListItem = shoppingList.getPositionById(id);

                        if (shoppingListItem == null) {
                            System.out.println("Can't find item with given ID");
                            return;
                        }

                        shoppingList.deletePositionById(id);

                        System.out.println("Item deleted");
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Not a number");
                        return;
                    }
                }
                default -> System.out.println("Unknown command");
            }
        }

    }

}
