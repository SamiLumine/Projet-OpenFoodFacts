//package com.yaku.yaku.controller;
//
//import com.yaku.yaku.model.Cart;
//import com.yaku.yaku.model.Product;
//import com.yaku.yaku.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/cart")
//public class CartController {
//
//    private final CartService cartService;
//
//    @Autowired
//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    // Récupérer le panier d'un utilisateur par email
//    @GetMapping("/{email}")
//    public Cart getCartByPerson(@PathVariable String email) {
//        return cartService.getCartByPersonEmail(email);
//    }
//
//    // Ajouter un produit au panier d'un utilisateur
//    @PostMapping("/{email}/add")
//    public Cart addProductToCart(@PathVariable String email, @RequestBody Product product) {
//        return cartService.addProductToCart(email, product.getBarcode());
//    }
//
//    // Supprimer un produit du panier
//    @DeleteMapping("/{email}/remove/{productId}")
//    public Cart removeProductFromCart(@PathVariable String email, @PathVariable Long productId) {
//        return cartService.removeProductFromCart(email, productId);
//    }
//
//    // Obtenir la synthèse du panier (score nutritionnel)
//    @GetMapping("/{email}/summary")
//    public String getCartSummary(@PathVariable String email) {
//        return cartService.getCartSummary(email);
//    }
//}
