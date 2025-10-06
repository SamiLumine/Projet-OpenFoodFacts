package com.yaku.yaku.repository;

import com.yaku.yaku.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByPersonEmail(String email);
}
