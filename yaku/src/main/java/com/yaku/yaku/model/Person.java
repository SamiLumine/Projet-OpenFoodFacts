package com.yaku.yaku.model;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @Column(unique = true, nullable = false)
    private String email;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
