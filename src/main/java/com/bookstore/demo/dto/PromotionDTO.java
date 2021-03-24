package com.bookstore.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@ApiModel(value = "Promotion Request", description = "object contains the details for promotion")
@Data
public class PromotionDTO implements Serializable {
    private static final long serialVersionUID = -7617283880866158630L;
    @ApiModelProperty(value = "promo code", required = true)
    @NotNull(message = "promo code is required")
    private String code;
    @ApiModelProperty(value = "promo details type,discount", required = true)
    @NotNull(message = "promo details is required")
    private Map<String, Integer> discount;
}
