package com.yaku.yaku.repository;

import com.yaku.yaku.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    List<Rule> findByNameOrderByMinBoundAsc(String name);
}
