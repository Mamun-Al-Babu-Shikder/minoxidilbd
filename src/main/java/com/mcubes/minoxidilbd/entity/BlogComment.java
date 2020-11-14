package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

/**
 * Created by A.A.MAMUN on 10/27/2020.
 */
@Entity
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_comment_sequence")
    @SequenceGenerator(name = "blog_comment_sequence", sequenceName = "blog_comment_sequence", allocationSize = 1)
    private long id;
    private long blogId;
    private String date;
    @NotEmpty
    @Length(min = 2, max = 50)
    @Column(length = 50)
    private String commenterName;
    @NotEmpty
    @Email
    private String commenterEmail;
    @NotEmpty
    @Length(min = 5, max = 500)
    @Column(length = 500)
    private String comment;

    @SuppressWarnings("deprecation")
    public BlogComment(){
        this.date = Calendar.getInstance().getTime().toLocaleString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterEmail() {
        return commenterEmail;
    }

    public void setCommenterEmail(String commenterEmail) {
        this.commenterEmail = commenterEmail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", date='" + date + '\'' +
                ", commenterName='" + commenterName + '\'' +
                ", commenterEmail='" + commenterEmail + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
