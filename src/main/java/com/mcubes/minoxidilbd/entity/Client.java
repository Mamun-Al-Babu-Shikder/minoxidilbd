package com.mcubes.minoxidilbd.entity;

import com.mcubes.minoxidilbd.annotation.Gender;
import com.mcubes.minoxidilbd.annotation.PhoneNumber;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Client {

    /* account access related information */
    @Id
    @Email
    @NotBlank
    private String email;
    @Length(min = 6, max = 225, message = "Minimum password length 6 max 225")
    private String password;
    private String role;
    private boolean enable;
    private boolean locked;
    private String validationToken;
    private String passwordResetToken;
    private Date createdDate;
    /* basic user information */
    @Length(min = 2, max = 30)
    @Column(length = 30)
    private String name;
    @Column(length = 260)
    private String image;
    @PhoneNumber
    private String phone;
    @Gender
    private String gender;
    @NotBlank
    private String dob;
    private String address;
    /* social media profile links */
    @URL
    private String facebook;
    @URL
    private String linkedin;
    @URL
    private String twitter;
    @URL
    private String instagram;

    public Client(String email, String password, String role, boolean enable, boolean locked){
        this.email = email;
        this.password = password;
        this.role = role;
        this.enable = enable;
        this.locked = locked;
    }

    public Client(String email, String password, String name, String phone, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    /*
    public static void main(String[] args) {
        String s = "http://www.hello.com";
        s = "hello...";
        System.out.println(s.matches("([\\w]|\\.)*"));

    }
     */
}
