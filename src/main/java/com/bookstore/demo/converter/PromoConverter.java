package com.bookstore.demo.converter;

import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.entity.PromoEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PromoConverter {

    /**
     * convert promo entity list to dtos
     * @param entityList
     * @return
     */
    public List<PromotionDTO> mapToDto(List<PromoEntity> entityList) {
        List<PromotionDTO> dtoList = new ArrayList<>();
        for (PromoEntity entity : entityList) {
            dtoList.add(mapToDto(entity));
        }
        return dtoList;
    }

    /**
     * convert promo entity to dto
     * @param entity
     * @return
     */
    public PromotionDTO mapToDto(PromoEntity entity) {
        PromotionDTO dto = new PromotionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
