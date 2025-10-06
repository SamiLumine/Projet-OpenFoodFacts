package com.yaku.yaku.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rule {

    @Id
    private Long id; // Change the type to Long
    private String name;
    private int points;
    private float minBound;
    private String component;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getMinBound() {
        return minBound;
    }

    public void setMinBound(float minBound) {
        this.minBound = minBound;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
