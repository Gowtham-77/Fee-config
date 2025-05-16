package com.core.io.fees.repository;

import com.core.io.fees.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    Optional<Rule> findByToCountryCodeAndProductSchemaAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqual(
            String toCountryCode, String productSchema, double amount1, double amount2);
}

