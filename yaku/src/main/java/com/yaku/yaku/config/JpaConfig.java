package com.yaku.yaku.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.yaku.yaku.repository")
@EntityScan(basePackages = "com.yaku.yaku.model") // Ajoutez cette ligne pour scanner les entités
public class JpaConfig {
    // Configuration JPA
}

