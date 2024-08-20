package com.github.Noiseneks.crudExample;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Noiseneks.crudExample.domain.dtos.FullListResponse;
import com.github.Noiseneks.crudExample.domain.dtos.IdDto;
import com.github.Noiseneks.crudExample.domain.dtos.ItemCreateRequestDto;
import com.github.Noiseneks.crudExample.domain.entity.ShoppingListItem;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(0)
    public void testCreate() throws Exception {

        List<ItemCreateRequestDto> itemsToCreate = List.of(
                new ItemCreateRequestDto()
                        .setName("Milk")
                        .setDescription("1 liter, 3.2%"),
                new ItemCreateRequestDto()
                        .setName("Tea")
                        .setDescription("Green"),
                new ItemCreateRequestDto()
                        .setName("Water")
        );

        for (ItemCreateRequestDto itemCreateRequestDto : itemsToCreate) {
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/create")
                    .content(objectMapper.writeValueAsString(itemCreateRequestDto))
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            assertEquals(HttpStatus.OK.value(), response.getStatus());
        }
    }

    @Test
    @Order(1)
    public void testRead() throws Exception {

        IdDto idDto = new IdDto().setId(1L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getById")
                .content(objectMapper.writeValueAsString(idDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        IdDto idDto2 = new IdDto().setId(4L);

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/getById")
                .content(objectMapper.writeValueAsString(idDto2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult2 = mvc.perform(requestBuilder2).andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response2.getStatus());
    }

    @Test
    @Order(2)
    public void testUpdate() throws Exception {

        String testDescription = "2 liters";

        ShoppingListItem shoppingListItem = new ShoppingListItem()
                .setId(1L)
                .setDescription(testDescription);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/update")
                .content(objectMapper.writeValueAsString(shoppingListItem))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ShoppingListItem shoppingListItemResponse = objectMapper.readValue(response.getContentAsString(), ShoppingListItem.class);

        assertEquals(testDescription, shoppingListItemResponse.getDescription());
    }

    @Test
    @Order(3)
    public void testDelete() throws Exception {
        IdDto idDto = new IdDto().setId(1L);

        RequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete("/deleteById")
                .content(objectMapper.writeValueAsString(idDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcDeleteResult = mvc.perform(deleteRequestBuilder).andReturn();
        MockHttpServletResponse deleteResultResponse = mvcDeleteResult.getResponse();

        assertEquals(HttpStatus.OK.value(), deleteResultResponse.getStatus());


        RequestBuilder getRequestBuilder = MockMvcRequestBuilders
                .get("/getById")
                .content(objectMapper.writeValueAsString(idDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcGetResult = mvc.perform(getRequestBuilder).andReturn();
        MockHttpServletResponse getResponse = mvcGetResult.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), getResponse.getStatus());
    }

    @Test
    @Order(4)
    public void testGetFulLList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getFullList")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        FullListResponse fullListResponse = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(2, fullListResponse.getItems().size());
    }
}
