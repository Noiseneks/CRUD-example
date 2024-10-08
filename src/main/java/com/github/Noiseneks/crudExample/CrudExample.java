package com.github.Noiseneks.crudExample;

import com.github.Noiseneks.crudExample.core.ShoppingList;
import com.github.Noiseneks.crudExample.core.ShoppingListItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
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

                    parseLongOptional(scanner.nextLine()).ifPresent(id -> {
                        ShoppingListItem shoppingListItem = shoppingList.getItemById(id);

                        if (shoppingListItem == null) {
                            System.out.println("Can't find item with given ID");
                            return;
                        }

                        System.out.println(shoppingListItem.toPrettyString());

                    });
                }
                case "edit" -> {
                    System.out.println("Enter item ID: ");

                    parseLongOptional(scanner.nextLine()).ifPresent(id -> {
                        ShoppingListItem shoppingListItem = shoppingList.getItemById(id);

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

                        shoppingList.updateItem(shoppingListItem);

                        System.out.println("Item updated");
                    });
                }
                case "delete" -> {
                    System.out.println("Enter item ID: ");

                    parseLongOptional(scanner.nextLine()).ifPresent(id -> {
                                ShoppingListItem shoppingListItem = shoppingList.getItemById(id);

                                if (shoppingListItem == null) {
                                    System.out.println("Can't find item with given ID");
                                    return;
                                }

                                shoppingList.deleteItemById(id);

                                System.out.println("Item deleted");
                            }
                    );
                }
                default -> System.out.println("Unknown command");
            }
        }
    }

    private static Optional<Long> parseLongOptional(String line) {
        try {
            return Optional.of(Long.parseLong(line));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
            return Optional.empty();
        }
    }
}
