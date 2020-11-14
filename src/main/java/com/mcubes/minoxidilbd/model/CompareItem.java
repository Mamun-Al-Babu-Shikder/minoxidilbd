package com.mcubes.minoxidilbd.model;

/**
 * Created by A.A.MAMUN on 10/13/2020.
 */
public class CompareItem {

    private long pid;
    private String productName;
    private double productPrice;
    private String productImage;
    private int stock;
    private String description;


    public CompareItem(long pid, String productName, double productPrice, String productImage, int stock,
                       String description) {
        this.pid = pid;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.stock = stock;
        this.description = description;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "CompareItem{" +
                "pid=" + pid +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productImage='" + productImage + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}
