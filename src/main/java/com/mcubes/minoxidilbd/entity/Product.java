package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private long id;
    private long category;
    private String name;
    private double currentPrice;
    private double oldPrice;
    @Transient
    private int discount;
    @Length(min = 10, max = 255)
    @Column(length = 255)
    private String description;
    @Length(min = 10, max = 1000)
    @Column(length = 1000)
    private String longDescription;
    @Column(length = 500)
    private String image;
    @Transient
    private MultipartFile imageFile;
    @OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private List<ProductImage> productImages;
    @Transient
    private MultipartFile[] imageFiles;
    private int stock;
    private int sell;
    private int views;
    private int rating;
    /**
     * product review list
    @OneToMany(targetEntity = ProductReview.class, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private List<ProductReview> productReviews;
    */


    public Product(){

    }

    public Product(long id, String image, String name, double currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.image = image;
    }

    public Product(long id, String name, double currentPrice, String image, int stock, String description) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.description = description;
        this.image = image;
        this.stock = stock;
    }

    public Product(long id, String image, String name, double currentPrice, double oldPrice, int rating, int stock,
                   String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.currentPrice = currentPrice;
        this.oldPrice = oldPrice;
        this.rating = rating;
        this.stock = stock;
        this.description = description;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public MultipartFile[] getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(MultipartFile[] imageFiles) {
        this.imageFiles = imageFiles;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        if(this.oldPrice==0 || this.currentPrice>=this.oldPrice)
            return 0;
        this.discount = (int) (((this.currentPrice - this.oldPrice)/oldPrice)*100);
        return this.discount;
    }

    /**
     * product review list setter-getter
    public List<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ProductReview> productReviews) {
        if(productReviews!=null && productReviews.size()!=0){
            int count = 0;
            for(ProductReview review : productReviews){
                count+=review.getRating();
            }
            rating = count/productReviews.size();
        }
        this.productReviews = productReviews;
    }
    */


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                ", oldPrice=" + oldPrice +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", image='" + image + '\'' +
                ", imageFile=" + imageFile +
                ", productImages=" + productImages +
                ", imageFiles=" + Arrays.toString(imageFiles) +
                ", stock=" + stock +
                ", sell=" + sell +
                ", views=" + views +
                ", rating=" + rating +
                '}';
    }
}
