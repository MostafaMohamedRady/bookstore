package com.bookstore.demo.controller;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.service.PromoService;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PromotionControllerTest {

    @InjectMocks
    private PromotionController controller;

    @Mock
    private PromoService service;

    @Test
    void getPromoList() {
        List<PromotionDTO> list = new ArrayList<>();
        PromotionDTO dto = new PromotionDTO();
        dto.setCode("PRO2020");
        dto.setDiscount(new HashMap<String, Integer>() {{
            put("comic", 10);
            put("drama", 20);
        }});
        list.add(dto);

        Mockito.when(service.findAll()).thenReturn(list);

        ResponseEntity<CustomResponse<List<PromotionDTO>>> responseEntity = controller.getPromoList();
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
        assertEquals(1, responseEntity.getBody().getData().size());
    }

    @Test
    void addPromo() {
        PromotionDTO dto = new PromotionDTO();
        dto.setCode("PRO2020");
        dto.setDiscount(new HashMap<String, Integer>() {{
            put("comic", 10);
            put("drama", 20);
        }});

        ResponseEntity<CustomResponse<String>> responseEntity = controller.addPromo(dto);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
    }

    @Test
    void deletePromo() {
        ResponseEntity<CustomResponse<String>> responseEntity = controller.deletePromo("10-9-10");
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
    }
}