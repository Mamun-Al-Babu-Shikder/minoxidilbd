package com.mcubes.minoxidilbd.model;

/**
 * Created by A.A.MAMUN on 10/13/2020.
 */
public class WishItem {

    private long pid;
    private String productName;
    private String productImage;
    private double productPrice;

    public WishItem() {
    }

    public WishItem(long pid, String productName, String productImage, double productPrice) {
        this.pid = pid;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "WishItem{" +
                "pid=" + pid +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
