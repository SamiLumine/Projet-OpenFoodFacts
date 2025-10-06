package com.yaku.yaku.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartItemId implements Serializable {
    private String email;
    private String barcode;

    public CartItemId() {}

    public CartItemId(String email, String barcode) {
        this.email = email;
        this.barcode = barcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}