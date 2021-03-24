package com.bookstore.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Checkout Request", description = "Checkout Request contains list od isbn and promo code")
@Data
public class CheckoutRequest implements Serializable {

    private static final long serialVersionUID = -4452925075584455947L;

    @ApiModelProperty(value = "list of isbn", required = true)
    @NotEmpty(message = "book list is required")
    private List<String> isbnList;
    @ApiModelProperty(value = "promo code", required = false)
    private String promoCode;
}
