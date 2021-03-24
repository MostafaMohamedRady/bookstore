package com.bookstore.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "Bill Response", description = "Bill details contains total payable amount.")
@Data
public class BillResponseDTO {
    @ApiModelProperty(value = "total amount before discount")
    private Double totalAmount;
    @ApiModelProperty(value = "net amount after discount")
    private Double netAmount;
    @ApiModelProperty(value = "applicable discount amount")
    private Double discountAmount;
    @ApiModelProperty(value = "books details")
    private List<BookDTO> bookList;
}
