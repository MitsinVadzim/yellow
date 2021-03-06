package com.example.yellow.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Record implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please enter distance")
    //@Pattern(regexp="[\\d]{6}", message = "It's not number")
    @Digits(integer = 10, fraction = 0, message = "The distance must be no more than 100 characters")
    private Integer distance;

    @NotNull(message = "Please enter race time")
    //@Pattern(regexp="[\\d]{6}", message = "It's not number")
    private Double time;

    @NotBlank(message = "Please enter race date")
    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Record() {
    }

    public Record(int distance, double time, String date, User user) {
        this.distance = distance;
        this.time = time;
        this.date = date;
        this.author = user;
    }

    public String getAuthorName() {
        return author.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
