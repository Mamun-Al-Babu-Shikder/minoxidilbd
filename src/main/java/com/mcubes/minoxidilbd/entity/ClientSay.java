package com.mcubes.minoxidilbd.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ClientSay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_say_sequence")
    @SequenceGenerator(name = "client_say_sequence", sequenceName = "client_say_sequence", allocationSize = 1)
    private long id;
    @Length(min = 5, max = 255, message = "Invalid image url length")
    private String image;
    @Transient
    private MultipartFile imageFile;
    private String name;
    private String designation;
    @Length(min = 10, max = 500)
    @Column(length = 500)
    private String comment;

}
