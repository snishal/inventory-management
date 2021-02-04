package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.models.Inventory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PurchaseControllerIntegrationTest {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void purchaseInventoryById() {
    int id = 1, quantity = 1;

    ResponseEntity<Inventory> response = this.testRestTemplate.getForEntity("/api/purchase/{id}/{quantity}", Inventory.class, id, quantity);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void purchaseInventoryByIdWhenInventoryNotFound() {
    int id = 10000000, quantity = 1;

    ResponseEntity<Inventory> response = this.testRestTemplate.getForEntity("/api/purchase/{id}/{quantity}", Inventory.class, id, quantity);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  public void purchaseInventoryByIdWhenNotEnoughQuantityOfInventory() {
    int id = 1, quantity = 1000000;

    ResponseEntity<Inventory> response = this.testRestTemplate.getForEntity("/api/purchase/{id}/{quantity}", Inventory.class, id, quantity);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
  }
}
