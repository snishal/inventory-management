package com.paytm.inventorymanagement.repositories;

import com.paytm.inventorymanagement.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
