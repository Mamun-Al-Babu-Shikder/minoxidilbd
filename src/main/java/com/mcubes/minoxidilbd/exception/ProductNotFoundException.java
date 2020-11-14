package com.mcubes.minoxidilbd.exception;

/**
 * Created by A.A.MAMUN on 10/23/2020.
 */

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(){
        super("Product not found.");
    }

    public ProductNotFoundException(long pid){
        super("Product not found according to product id : " + pid);
    }
}
