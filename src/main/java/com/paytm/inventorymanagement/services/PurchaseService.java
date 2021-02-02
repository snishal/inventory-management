package com.paytm.inventorymanagement.services;

import com.paytm.inventorymanagement.exceptions.InventoryNotFoundException;
import com.paytm.inventorymanagement.exceptions.NotEnoughQuantityOfInventoryException;
import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public void purchaseInventoryById(Integer id, Integer quantity) throws RuntimeException{
        if(inventoryRepository.existsById(id)){
            Inventory inventory = inventoryRepository.findById(id).get();
            if(quantity <= inventory.getQuantity()){
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);
            }else{
                throw new NotEnoughQuantityOfInventoryException("Not Enough Quantity of Inventory");
            }
        }else{
            throw new InventoryNotFoundException("Inventory with id " + id + " not found.");
        }
    }
}
