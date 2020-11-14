package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Entity
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_review_sequence")
    @SequenceGenerator(name = "product_review_sequence", sequenceName = "product_review_sequence", allocationSize = 1)
    private long id;
    private long productId;
    @Column(length = 100)
    @Range(min = 1, max = 5, message = "Invalid rating")
    private int rating;
    @NotEmpty
    @Column(length = 55)
    @Length(min = 1, max = 50)
    private String title;
    @Column(length = 1500)
    @Length(min = 1, max = 1500, message = "Invalid review length")
    private String review;
    @NotEmpty
    @Email(message = "Invalid email address")
    private String reviewerEmail;
    @Column(length = 55)
    @Length(min=1, max=50)
    private String reviewerName;
    private String date;

    @SuppressWarnings("deprecation")
    public ProductReview(){
        Calendar calendar = Calendar.getInstance();
        this.date = Calendar.getInstance().getTime().toLocaleString();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getDate() {
        return date;
    }

    @SuppressWarnings("deprecation")
    public void setDate(String date) {
        if(date==null || date.length()==0) {
            Calendar calendar = Calendar.getInstance();
            this.date = Calendar.getInstance().getTime().toLocaleString();
        }else {
            this.date = date;
        }
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", productId=" + productId +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", review='" + review + '\'' +
                ", reviewerEmail='" + reviewerEmail + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
