package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_member_sequence")
    @SequenceGenerator(name = "team_member_sequence", sequenceName = "team_member_sequence", allocationSize = 1)
    private long id;
    @NotEmpty
    @Length(min = 5, max = 500)
    @Column(length = 500)
    private String image;
    @NotEmpty
    @Length(min = 2, max = 50)
    @Column(length = 50)
    private String name;
    @NotEmpty
    @Length(min = 2, max = 50)
    @Column(length = 50)
    private String designation;
    @NotEmpty
    @Length(min = 1, max = 500)
    @Column(length = 500)
    private String facebook, twitter, linkedin, instagram;

    public TeamMember(){

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", instagram='" + instagram + '\'' +
                '}';
    }
}
