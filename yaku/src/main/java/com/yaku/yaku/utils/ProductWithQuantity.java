package com.yaku.yaku.utils;

import com.yaku.yaku.model.Product;

// Création de la classe pour combiner le produit et la quantité
public class ProductWithQuantity {
    private Product product;
    private int quantity;

    public ProductWithQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
