package com.mcubes.minoxidilbd.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Subscriber {
    @Id
    @Email
    private String email;
    private String date;
}
