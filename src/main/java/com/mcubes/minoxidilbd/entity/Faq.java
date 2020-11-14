package com.mcubes.minoxidilbd.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by A.A.MAMUN on 10/25/2020.
 */
@Entity
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_sequence")
    @SequenceGenerator(name = "faq_sequence", sequenceName = "faq_sequence", allocationSize = 1)
    private int id;
    @NotEmpty
    @Length(min = 2, max = 100)
    @Column(length = 100)
    private String question;
    @NotEmpty
    @Length(min = 2, max = 1000)
    @Column(length = 1000)
    private String answer;
    private boolean general;

    public Faq(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }

    @Override
    public String toString() {
        return "Faq{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", general=" + general +
                '}';
    }
}
