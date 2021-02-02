package com.paytm.inventorymanagement.exceptions;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String exception){
        super(exception);
    }
}
