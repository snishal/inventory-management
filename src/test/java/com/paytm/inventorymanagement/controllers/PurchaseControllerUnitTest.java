package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.exceptions.InventoryNotFoundException;
import com.paytm.inventorymanagement.exceptions.NotEnoughQuantityOfInventoryException;
import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.services.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerUnitTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PurchaseService purchaseService;

  @Test
  public void purchaseInventoryById() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");
    inventory.setQuantity(10);

    int quantity = 5;

    given(purchaseService.purchaseInventoryById(inventory.getId(), quantity)).willReturn(inventory);

    mockMvc.perform(get("/api/purchase/{id}/{quantity}", inventory.getId(), quantity)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(inventory.getName())));

    verify(purchaseService, times(1)).purchaseInventoryById(inventory.getId(), quantity);
    verifyNoMoreInteractions(purchaseService);
  }

  @Test
  public void purchaseInventoryByIdWhenInventoryNotFound() throws Exception {
    int id = 1;
    int quantity = 5;

    given(purchaseService.purchaseInventoryById(id, quantity)).willThrow(new InventoryNotFoundException("Inventory Not Found"));

    mockMvc.perform(get("/api/purchase/{id}/{quantity}", id, quantity)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ResponseStatusException));

    verify(purchaseService, times(1)).purchaseInventoryById(id, quantity);
    verifyNoMoreInteractions(purchaseService);
  }

  @Test
  public void purchaseInventoryByIdWhenNotEnoughQuantity() throws Exception {
    int id = 1;
    int quantity = 5;

    given(purchaseService.purchaseInventoryById(id, quantity)).willThrow(new NotEnoughQuantityOfInventoryException("Not Enough Quantity"));

    mockMvc.perform(get("/api/purchase/{id}/{quantity}", id, quantity)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ResponseStatusException));

    verify(purchaseService, times(1)).purchaseInventoryById(id, quantity);
    verifyNoMoreInteractions(purchaseService);
  }
}
