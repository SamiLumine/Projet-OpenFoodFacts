package com.yaku.yaku.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yaku.yaku.model.Product;
import com.yaku.yaku.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final OpenFoodFactsService openFoodFactsService;
    private final ProductRepository ProductRepository;

    public ProductService(OpenFoodFactsService openFoodFactsService, ProductRepository productRepository) {
        this.openFoodFactsService = openFoodFactsService;
        this.ProductRepository = productRepository;
    }

    public void addProduct(Product product) {
        Optional<Product> existingProduct = ProductRepository.findByBarcode(product.getBarcode());

        if (existingProduct.isPresent()) {
            // Ne rien faire si le produit existe déjà
            return;
        }

        // Ajouter le produit seulement s'il n'existe pas
        ProductRepository.save(product);
    }
}

