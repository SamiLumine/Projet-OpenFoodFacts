package com.yaku.yaku.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private String barcode; // Le code-barres devient l'identifiant

    private String name;
    private int nutritionScore;

    // Getters et Setters
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNutritionScore() {
        return nutritionScore;
    }

    public void setNutritionScore(int nutritionScore) {
        this.nutritionScore = nutritionScore;
    }


}



