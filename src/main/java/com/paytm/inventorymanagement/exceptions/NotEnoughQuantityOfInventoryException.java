package com.paytm.inventorymanagement.exceptions;

public class NotEnoughQuantityOfInventoryException extends RuntimeException{
    public NotEnoughQuantityOfInventoryException(String exception){
        super(exception);
    }
}
