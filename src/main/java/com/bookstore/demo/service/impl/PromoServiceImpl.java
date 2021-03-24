package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.converter.PromoConverter;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.entity.PromoEntity;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.repository.PromoRepository;
import com.bookstore.demo.service.PromoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoRepository repository;

    @Autowired
    private PromoConverter converter;

    @Override
    public void save(PromotionDTO dto) {
        PromoEntity entity = new PromoEntity();
        BeanUtils.copyProperties(dto, entity);
        PromoEntity save = repository.save(entity);
        log.info("Promo {} :- saved successfully to db", save);
    }

    @Override
    public List<PromotionDTO> findAll() {
        List<PromoEntity> entityList = repository.findAll();
        log.info("fetch all promo details from DB :- {}", entityList);
        if (CollectionUtils.isEmpty(entityList)) {
            throw new BookStoreException(BookStoreConstant.NO_DATA_FOUND);
        }
        return converter.mapToDto(entityList);
    }

    @Override
    public PromotionDTO findByCode(String promoCode) {
        PromoEntity entity = repository.findFirstByCode(promoCode);
        if (entity != null) {
            PromotionDTO dto = new PromotionDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }

    @Override
    public void delete(String code) {
        PromoEntity byCode = repository.findFirstByCode(code);
        if (ObjectUtils.isEmpty(byCode)) {
            throw new BookStoreException(BookStoreConstant.NO_MATCHING_DATA);
        }
        repository.delete(byCode);
    }
}
