package com.paytm.inventorymanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.inventorymanagement.exceptions.InventoryNotFoundException;
import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InventoryController.class)
public class InventoryControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InventoryRepository inventoryRepository;

  @Test
  public void getAllInventories() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("table");
    inventory.setQuantity(1);

    List<Inventory> allInventories = Arrays.asList(inventory);

    given(inventoryRepository.findAll()).willReturn(allInventories);

    mockMvc.perform(get("/api/inventories")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].name", is(inventory.getName())));

    verify(inventoryRepository, times(1)).findAll();
    verifyNoMoreInteractions(inventoryRepository);
  }

  @Test
  public void getInventoryById() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("table");
    inventory.setQuantity(1);

    given(inventoryRepository.findById(inventory.getId())).willReturn(java.util.Optional.of(inventory));

    mockMvc.perform(get("/api/inventories/{id}", inventory.getId())
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(inventory.getName())))
      .andExpect(jsonPath("$.quantity", is(inventory.getQuantity())));

    verify(inventoryRepository, times(1)).findById(inventory.getId());
    verifyNoMoreInteractions(inventoryRepository);
  }

  @Test
  public void createInventory() throws Exception {
    Inventory table = new Inventory();
    table.setId(1);
    table.setName("table");
    table.setQuantity(1);

    given(inventoryRepository.save(Mockito.any(Inventory.class))).willReturn(table);

    mockMvc.perform(post("/api/inventories")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(table)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name", is(table.getName())))
      .andExpect(jsonPath("$.quantity", is(table.getQuantity())));

    verify(inventoryRepository, times(1)).save(Mockito.any(Inventory.class));
    verifyNoMoreInteractions(inventoryRepository);
  }

  @Test
  public void deleteInventory() throws Exception {
    int id = 1;

    mockMvc.perform(delete("/api/inventories/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

    verify(inventoryRepository, times(1)).deleteById(id);
    verifyNoMoreInteractions(inventoryRepository);
  }

  @Test
  public void updateInventory() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");
    inventory.setQuantity(1);

    Inventory updatedInventory = new Inventory();
    updatedInventory.setId(10);
    updatedInventory.setName("Door");
    updatedInventory.setQuantity(10);

    given(inventoryRepository.getOne(inventory.getId())).willReturn(inventory);
    given(inventoryRepository.save(Mockito.any(Inventory.class))).willReturn(updatedInventory);

    mockMvc.perform(put("/api/inventories/{id}", inventory.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(updatedInventory)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(updatedInventory.getName())))
      .andExpect(jsonPath("$.quantity", is(updatedInventory.getQuantity())));

    verify(inventoryRepository, times(1)).getOne(inventory.getId());
    verify(inventoryRepository, times(1)).save(Mockito.any(Inventory.class));
    verifyNoMoreInteractions(inventoryRepository);
  }
}
