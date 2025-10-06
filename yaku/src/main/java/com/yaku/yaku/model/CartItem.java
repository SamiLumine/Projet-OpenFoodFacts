package com.yaku.yaku.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    private int quantity;

    public CartItem() {}

    public CartItem(CartItemId id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public CartItemId getId() {
        return id;
    }

    public void setId(CartItemId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
