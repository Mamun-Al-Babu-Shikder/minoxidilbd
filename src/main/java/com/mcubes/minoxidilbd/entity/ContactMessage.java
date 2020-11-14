package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_message_sequence")
    @SequenceGenerator(name = "contact_message_sequence", sequenceName = "contact_message_sequence", allocationSize = 1)
    private long id;
    private String date;
    @NotEmpty
    @Length(min = 1, max=100)
    @Column(length = 100)
    private String name;
    @NotEmpty
    @Email
    @Length(min = 1, max=100)
    @Column(length = 100)
    private String email;
    @NotEmpty
    @Length(min = 1, max=100)
    @Column(length = 100)
    private String subject;
    @NotEmpty
    @Length(min = 1, max=1000)
    @Column(length = 1000)
    private String message;
    private boolean seen;

    @SuppressWarnings("deprecation")
    public ContactMessage(){
        this.date = Calendar.getInstance().getTime().toLocaleString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    /*
    public void setDate(String date) {
        this.date = date;
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", seen=" + seen +
                '}';
    }
}
