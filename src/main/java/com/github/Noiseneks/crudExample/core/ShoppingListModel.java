package com.github.Noiseneks.crudExample.core;

import com.github.Noiseneks.crudExample.core.repository.ShoppingListRepository;
import com.github.Noiseneks.crudExample.domain.dtos.FullListResponse;
import com.github.Noiseneks.crudExample.domain.dtos.IdDto;
import com.github.Noiseneks.crudExample.domain.dtos.ItemCreateRequestDto;
import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Configuration
public class ShoppingListModel {


    private final ShoppingListRepository shoppingListRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingListModel.class);

    @Autowired
    public ShoppingListModel(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    /**
     * This method adds provided item to shopping list
     *
     * @param itemCreateRequestDto — provided item
     */
    public ShoppingListItem addItem(ItemCreateRequestDto itemCreateRequestDto) {

        ShoppingListItem shoppingListItem = new ShoppingListItem()
                .setName(itemCreateRequestDto.getName())
                .setDescription(itemCreateRequestDto.getDescription());

        ShoppingListItem responseItem = shoppingListRepository.saveAndFlush(shoppingListItem);
        LOGGER.info("Added item {} to shopping list", shoppingListItem);

        return responseItem;
    }


    /**
     * @param idDto — DTO with id of a item you want to get
     * @return item with given id
     */
    @Nullable
    public ShoppingListItem getItemById(IdDto idDto) {

        Long id = idDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID can't be null");
        }

        ShoppingListItem shoppingListItem = shoppingListRepository.getById(id);

        if (shoppingListItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with given id not found");
        }

        return shoppingListItem;
    }

    /**
     * Update existing item in list
     *
     * @param shoppingListItem — DTO with id and parameters of item you want to update
     */
    public ShoppingListItem updateItem(ShoppingListItem shoppingListItem) {

        if (shoppingListItem.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID can't be null");
        }

        ShoppingListItem existingItem = shoppingListRepository.getById(shoppingListItem.getId());

        if (existingItem == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item with given id not found");
        }

        String newName = shoppingListItem.getName();

        if (newName != null) {
            existingItem.setName(newName);
        }

        String newDescription = shoppingListItem.getDescription();

        if (newDescription != null) {
            existingItem.setDescription(newDescription);
        }

        ShoppingListItem reposnseItem = shoppingListRepository.saveAndFlush(existingItem);
        LOGGER.info("Updated item in shopping list: {}", existingItem);

        return reposnseItem;
    }

    /**
     * Deletes item from shopping list
     *
     * @param idDto — DTO with id of item you want to delete
     */
    public void deleteItemById(IdDto idDto) {

        Long id = idDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID can't be null");
        }

        ShoppingListItem itemToDelete = shoppingListRepository.getById(id);

        if (itemToDelete == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item with given id doesn't exists");
        }

        shoppingListRepository.delete(itemToDelete);
        LOGGER.info("Removed list entity: {}", itemToDelete);
    }


    /**
     * @return all items in table
     */
    public FullListResponse getFullListOfItems() {

        return new FullListResponse()
                .setItems(shoppingListRepository.getAllItems());
    }
}
