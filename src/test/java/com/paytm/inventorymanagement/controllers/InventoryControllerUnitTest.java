package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InventoryController.class)
public class InventoryControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InventoryRepository inventoryRepository;

  @Test
  public void getAllInventories() throws Exception{
    Inventory table = new Inventory();
    table.setId(1);
    table.setName("table");
    table.setQuantity(1);

    List<Inventory> allInventories = Arrays.asList(table);

    given(inventoryRepository.findAll()).willReturn(allInventories);

    mockMvc.perform(get("/api/inventories")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].name", is(table.getName())));
  }

  @Test
  public void getAllInventories2() throws Exception{
    mockMvc.perform(get("/api/inventories"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().json("[]"));

    verify(inventoryRepository, times(1)).findAll();
  }
}
