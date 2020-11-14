package com.mcubes.minoxidilbd.model;

/**
 * Created by A.A.MAMUN on 10/10/2020.
 */
public class CartItem {

    private long pid;
    private String productImage;
    private String productName;
    private double productPrice;
    private int quantity;

    public CartItem() {
    }

    public CartItem(long pid, String productImage, String productName, double productPrice, int quantity) {
        this.pid = pid;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "pid=" + pid +
                ", productImage='" + productImage + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                '}';
    }
}
