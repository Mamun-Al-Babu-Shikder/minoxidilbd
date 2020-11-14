package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

/**
 * Created by A.A.MAMUN on 10/26/2020.
 */
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_sequence")
    @SequenceGenerator(name = "blog_sequence", sequenceName = "blog_sequence", allocationSize = 1)
    private long id;
    @NotEmpty
    @Length(min = 5, max = 300)
    @Column(length = 300)
    private String image;
    @Transient
    private MultipartFile imageFile;
    @NotEmpty
    @Length(min = 10, max = 100)
    @Column(length = 100)
    private String title;
    private String date;
    @NotEmpty
    @Length(min = 50, max = 500)
    @Column(length = 500)
    private String preNoteDescription;
    private String note;
    @Column(length = 1000)
    private String postNoteDescription;
    private int numberOfComment;

    @SuppressWarnings("deprecation")
    public Blog(){
        this.date = Calendar.getInstance().getTime().toLocaleString();
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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPreNoteDescription() {
        return preNoteDescription;
    }

    public void setPreNoteDescription(String preNoteDescription) {
        this.preNoteDescription = preNoteDescription;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPostNoteDescription() {
        return postNoteDescription;
    }

    public void setPostNoteDescription(String postNoteDescription) {
        this.postNoteDescription = postNoteDescription;
    }

    public int getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(int numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", imageFile=" + imageFile +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", preNoteDescription='" + preNoteDescription + '\'' +
                ", note='" + note + '\'' +
                ", postNoteDescription='" + postNoteDescription + '\'' +
                ", numberOfComment=" + numberOfComment +
                '}';
    }
}
