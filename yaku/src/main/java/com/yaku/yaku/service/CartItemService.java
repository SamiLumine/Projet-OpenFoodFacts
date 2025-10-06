package com.yaku.yaku.service;

import com.yaku.yaku.model.CartItem;
import com.yaku.yaku.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // Recherche d'un CartItem par email et barcode
    public Optional<CartItem> findByEmailAndBarcode(String email, String barcode) {
        return cartItemRepository.findById_EmailAndId_Barcode(email, barcode);
    }

    // Sauvegarde un CartItem
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Suppression d'un CartItem par email et barcode
    @Transactional
    public void deleteByEmailAndBarcode(String email, String barcode) {
        cartItemRepository.deleteById_EmailAndId_Barcode(email, barcode);
    }

    // Récupère la quantity d'un CartItem par email et barcode
    public Optional<Integer> findQuantityByEmailAndBarcode(String email, String barcode) {
        return cartItemRepository.findQuantityById_EmailAndId_Barcode(email, barcode);
    }
}
