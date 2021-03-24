package com.bookstore.demo.service;

import com.bookstore.demo.dto.PromotionDTO;

import java.util.List;

public interface PromoService {
    void save(PromotionDTO dto);

    List<PromotionDTO> findAll();

    PromotionDTO findByCode(String promoCode);

    void delete(String code);
}
