package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    @Autowired
    private InventoryRepository inventoryRepository;

    @RequestMapping("{id}/{quantity}")
    public void purchase(@PathVariable Integer id, @PathVariable Integer quantity){
        if(inventoryRepository.existsById(id)){
            Inventory inventory = inventoryRepository.getOne(id);
            if(quantity <= inventory.getQuantity()){
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Enough Quantity of Inventory");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory Not Found");
        }
    }
}
