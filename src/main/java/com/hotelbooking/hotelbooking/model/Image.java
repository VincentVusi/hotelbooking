package com.hotelbooking.hotelbooking.model;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String data;

    public Image() {
    }

    public Image(byte[] data) {
        this.data = Base64.getEncoder().encodeToString(data);
    }

    public String getImageString() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = Base64.getEncoder().encodeToString(data);
    }

}
