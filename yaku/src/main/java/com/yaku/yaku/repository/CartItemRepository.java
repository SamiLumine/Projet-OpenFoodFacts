package com.yaku.yaku.repository;

import com.yaku.yaku.model.CartItem;
import com.yaku.yaku.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    // Recherche d'un CartItem en fonction de l'email et du barcode
    Optional<CartItem> findById_EmailAndId_Barcode(String email, String barcode);

    // Méthode pour supprimer un CartItem en fonction de l'email et du barcode
    void deleteById_EmailAndId_Barcode(String email, String barcode);

    // Méthode pour récupérer la quantity en fonction de l'email et du barcode
    @Query("SELECT c.quantity FROM CartItem c WHERE c.id.email = :email AND c.id.barcode = :barcode")
    Optional<Integer> findQuantityById_EmailAndId_Barcode(@Param("email") String email, @Param("barcode") String barcode);

}
