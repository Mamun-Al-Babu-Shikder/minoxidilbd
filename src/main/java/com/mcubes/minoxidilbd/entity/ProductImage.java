package com.mcubes.minoxidilbd.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_sequence")
    @SequenceGenerator(name = "product_image_sequence", sequenceName = "product_image_sequence", allocationSize = 1)
    private long id;
    @Column(length = 500)
    private String image;

    public ProductImage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", image='" + image + '\'' +
                '}';
    }
}
