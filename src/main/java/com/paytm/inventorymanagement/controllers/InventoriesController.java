package com.paytm.inventorymanagement.controllers;

import com.paytm.inventorymanagement.models.Inventory;
import com.paytm.inventorymanagement.repositories.InventoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoriesController {
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<Inventory> getAll(){
        return inventoryRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Inventory get(@PathVariable Integer id){
        return inventoryRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory create(@RequestBody final Inventory inventory){
        return inventoryRepository.saveAndFlush(inventory);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        inventoryRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Inventory update(@PathVariable Integer id, @RequestBody Inventory inventory){
        Inventory existingInventory = inventoryRepository.getOne(id);
        BeanUtils.copyProperties(inventory, existingInventory, "id");
        return inventoryRepository.saveAndFlush(existingInventory);
    }
}
