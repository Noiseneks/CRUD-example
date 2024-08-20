package com.github.Noiseneks.crudExample.controllers;

import com.github.Noiseneks.crudExample.core.ShoppingListModel;
import com.github.Noiseneks.crudExample.domain.dtos.FullListResponse;
import com.github.Noiseneks.crudExample.domain.dtos.IdDto;
import com.github.Noiseneks.crudExample.domain.dtos.ItemCreateRequestDto;
import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/")
public class ShoppingListController {

    private final ShoppingListModel shoppingListModel;

    @Autowired
    public ShoppingListController(ShoppingListModel shoppingListModel) {
        this.shoppingListModel = shoppingListModel;
    }

    @Operation(summary = "Create shopping list item",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item successfully created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShoppingListItem.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ShoppingListItem> create(@RequestBody ItemCreateRequestDto itemCreateRequestDto) {

        ShoppingListItem shoppingListItem = shoppingListModel.addItem(itemCreateRequestDto);

        return ResponseEntity.ok(shoppingListItem);
    }

    @Operation(summary = "Get shopping list item by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShoppingListItem.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item with given ID doesn't exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @GetMapping("/getById")
    public ResponseEntity<ShoppingListItem> read(@RequestBody IdDto idDto) {

        ShoppingListItem shoppingListItem = shoppingListModel.getItemById(idDto);

        return ResponseEntity.ok(shoppingListItem);
    }


    @Operation(summary = "Update shopping list item",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShoppingListItem.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Item with given ID doesn't exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @PatchMapping("/update")
    public ResponseEntity<ShoppingListItem> update(@RequestBody ShoppingListItem shoppingListItem) {

        ShoppingListItem responseItem = shoppingListModel.updateItem(shoppingListItem);

        return ResponseEntity.ok(responseItem);
    }

    @Operation(summary = "Delete shopping list item by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Item with given ID doesn't exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @DeleteMapping("/deleteById")
    public ResponseEntity<HashMap<String, String>> delete(@RequestBody IdDto idDto) {
        shoppingListModel.deleteItemById(idDto);

        HashMap<String, String> body = new HashMap<>();
        body.put("message", "Item deleted successfully");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get full list of items to buy",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ShoppingListItem.class))
                            )
                    )
            }
    )
    @GetMapping("/getFullList")
    public ResponseEntity<FullListResponse> readFullList() {

        FullListResponse fullListResponse = shoppingListModel.getFullListOfItems();

        return ResponseEntity.ok(fullListResponse);
    }
}
