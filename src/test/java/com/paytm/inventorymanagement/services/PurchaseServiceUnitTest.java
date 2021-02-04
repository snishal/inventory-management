package com.paytm.inventorymanagement.services;

import com.paytm.inventorymanagement.exceptions.InventoryNotFoundException;
import com.paytm.inventorymanagement.exceptions.NotEnoughQuantityOfInventoryException;
import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceUnitTest {
  @InjectMocks private PurchaseService purchaseService;

  @Mock private InventoryRepository inventoryRepository;

  @Test
  public void purchaseInventoryByIdWhenInventoryNotFound() {
    int id = 1, quantity = 10;

    given(inventoryRepository.existsById(id)).willReturn(false);

    assertThrows(InventoryNotFoundException.class, () -> purchaseService.purchaseInventoryById(id, quantity));
  }

  @Test
  public void purchaseInventoryByIdWhenNotEnoughQuantityOfInventory() {
    int quantity = 20;
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");
    inventory.setQuantity(10);

    given(inventoryRepository.existsById(inventory.getId())).willReturn(true);
    given(inventoryRepository.findById(inventory.getId())).willReturn(java.util.Optional.of(inventory)); //What if we had MockBean instead of Mock

    assertThrows(NotEnoughQuantityOfInventoryException.class, () -> purchaseService.purchaseInventoryById(inventory.getId(), quantity));
  }

  @Test
  public void purchaseInventoryById() {
    int quantity = 5;
    Inventory inventory = new Inventory();
    inventory.setId(1);
    inventory.setName("Table");
    inventory.setQuantity(10);

    given(inventoryRepository.existsById(inventory.getId())).willReturn(true);
    given(inventoryRepository.findById(inventory.getId())).willReturn(java.util.Optional.of(inventory)); //What if we had MockBean instead of Mock
    given(inventoryRepository.save(Mockito.any(Inventory.class))).willReturn(inventory);

    int remainingQuantity = inventory.getQuantity() - quantity;
    assertEquals(remainingQuantity, purchaseService.purchaseInventoryById(inventory.getId(), quantity).getQuantity());
  }
}
