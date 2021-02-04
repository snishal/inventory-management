package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.models.Inventory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTest {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void getAllInventories() throws Exception {
    ResponseEntity<List> response = this.testRestTemplate.getForEntity("/api/inventories", List.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
  }

  @Test
  public void getInventoryById() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");

    ResponseEntity<Inventory> response = this.testRestTemplate.getForEntity("/api/inventories/{id}", Inventory.class, inventory.getId());

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody().getName(), equalTo(inventory.getName()));
  }

  @Test
  public void createInventory() throws Exception {
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");
    inventory.setQuantity(100);

    ResponseEntity<Inventory> response = this.testRestTemplate.postForEntity("/api/inventories", inventory, Inventory.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertTrue(response.getBody().equals(inventory));
  }

  @Test
  public void deleteInventory() throws Exception {
    int id = 10;
    this.testRestTemplate.delete("/api/inventories/{id}", id);
  }

  @Test
  public void updateInventory() throws Exception {
    int id = 1;
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Chair");
    inventory.setQuantity(100);

    this.testRestTemplate.put("/api/inventories/{id}", inventory, id);
  }
}
