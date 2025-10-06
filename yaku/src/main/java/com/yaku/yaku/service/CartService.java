package com.yaku.yaku.service;

import com.yaku.yaku.model.Cart;
import com.yaku.yaku.model.Person;
import com.yaku.yaku.model.Product;
import com.yaku.yaku.repository.CartRepository;
import com.yaku.yaku.repository.PersonRepository;
import com.yaku.yaku.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final PersonRepository personRepository;
    private final ProductRepository ProductRepository;

    @Autowired
    public CartService(CartRepository cartRepository, PersonRepository personRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.personRepository = personRepository;
        this.ProductRepository = productRepository;
    }

    // Récupérer le panier d'un utilisateur
    public Cart getCartByPersonEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // Attempt to retrieve the cart by email
        return cartRepository.findByPersonEmail(email).orElseGet(() -> {
            // If no cart found, find the person by email
            Person person = personRepository.findByEmail(email).orElseThrow(() ->
                    new RuntimeException("User not found with email: " + email)
            );

            // Create a new cart if the person exists
            Cart newCart = new Cart();
            newCart.setPerson(person);

            // Save and return the new cart
            return cartRepository.save(newCart);
        });
    }


    // Ajouter un produit au panier

    // Supprimer un produit du panier
    @Transactional
    public Cart removeProductFromCart(String email, String productId) {
        Cart cart = getCartByPersonEmail(email);
        cart.getProducts().removeIf(p -> p.getBarcode().equals(productId));
        return cartRepository.save(cart);
    }

    // Générer la synthèse du panier
    public String getCartSummary(String email) {
        Cart cart = getCartByPersonEmail(email);
        int totalScore = cart.getProducts().stream().mapToInt(Product::getNutritionScore).sum();
        return "Score nutritionnel total du panier : " + totalScore;
    }

    public List<Product> getProductsInCart(String email) {
        Cart cart = getCartByPersonEmail(email);
        return cart.getProducts();
    }

    public Cart addCart(String email) {
        return cartRepository.findByPersonEmail(email)
                .orElseGet(() -> {
                    Person person = personRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
                    Cart newCart = new Cart();
                    newCart.setPerson(person);
                    newCart.setProducts(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    public Cart addProductToCart(String email, String productBarcode) {
        Cart cart = cartRepository.findByPersonEmail(email)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Product product = ProductRepository.findByBarcode(productBarcode)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        if (!cart.getProducts().contains(product)) {
            cart.getProducts().add(product);
            cartRepository.save(cart); // Cela met à jour la table `cart_products`
        }

        return cart;
    }


}
