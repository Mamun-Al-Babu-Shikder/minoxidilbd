package com.mcubes.minoxidilbd.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Ads {

    @Id
    @Column(length = 25)
    private String id;
    @Length(min = 5, max = 255)
    private String image;
    @Length(min = 3, max = 50)
    @Column(length = 50)
    private String title;
    @Length(min = 3, max = 50)
    @Column(length = 50)
    private String subtitle;
    @NotBlank
    @Length(min = 5, max = 255)
    private String link;


}
