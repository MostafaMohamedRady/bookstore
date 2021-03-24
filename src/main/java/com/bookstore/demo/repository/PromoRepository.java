package com.bookstore.demo.repository;

import com.bookstore.demo.entity.PromoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoRepository extends JpaRepository<PromoEntity, Long> {
    PromoEntity findFirstByCode(String code);
}
