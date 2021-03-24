package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.converter.PromoConverter;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.entity.PromoEntity;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.repository.PromoRepository;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PromoServiceImplTest {

    @InjectMocks
    private PromoServiceImpl service;

    @Spy
    private PromoConverter converter;

    @Mock
    private PromoRepository repository;


    @Test
    void save() {
        PromotionDTO dto = new PromotionDTO();
        dto.setCode("PRO2020");
        dto.setDiscount(Maps.newHashMap());
        Mockito.when(repository.save(Mockito.isA(PromoEntity.class))).thenReturn(new PromoEntity());
        service.save(dto);
        verify(repository, times(1)).save(Mockito.isA(PromoEntity.class));

    }

    @Test
    void findAll() {
        List<PromoEntity> list = new ArrayList<>();
        PromoEntity entity = new PromoEntity();
        entity.setPromoId(1L);
        entity.setCode("PRO2020");
        entity.setDiscount(Maps.newHashMap());
        list.add(entity);
        Mockito.when(repository.findAll()).thenReturn(list);
        List<PromotionDTO> all = service.findAll();
        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findAll_whenNoDataFound() {
        Mockito.when(repository.findAll()).thenReturn(null);
        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.findAll();
        });
        Assertions.assertEquals(BookStoreConstant.NO_DATA_FOUND, bookStoreException.getMessage());
    }

    @Test
    void findByCode() {
        PromoEntity entity = new PromoEntity();
        entity.setPromoId(1L);
        entity.setCode("PRO2020");
        entity.setDiscount(Maps.newHashMap());

        Mockito.when(repository.findFirstByCode(Mockito.isA(String.class))).thenReturn(entity);
        PromotionDTO pro2020 = service.findByCode("PRO2020");
        Assertions.assertNotNull(pro2020);
    }

    @Test
    void findByCode_whenNoDataMatched_returnNull() {
        Mockito.when(repository.findFirstByCode(Mockito.anyString())).thenReturn(null);
        PromotionDTO pro2020 = service.findByCode("PRO2020");
        Assertions.assertNull(pro2020);
    }

    @Test
    void delete() {
        Mockito.when(repository.findFirstByCode(Mockito.anyString())).thenReturn(new PromoEntity());
        service.delete("PRO2020");
        verify(repository, times(1)).delete(Mockito.isA(PromoEntity.class));
    }

    @Test
    void delete_whenNoDataFound_throwException() {
        Mockito.when(repository.findFirstByCode(Mockito.anyString())).thenReturn(null);

        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.delete("PRO2020");
        });
        Assertions.assertEquals(BookStoreConstant.NO_MATCHING_DATA, bookStoreException.getMessage());
        verify(repository, times(0)).delete(Mockito.isA(PromoEntity.class));

    }
}