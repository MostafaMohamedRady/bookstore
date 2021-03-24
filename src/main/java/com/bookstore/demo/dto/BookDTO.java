package com.bookstore.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "Book Request", description = "Book details")
@Data
public class BookDTO implements Serializable {
    private static final long serialVersionUID = 2968754955675478063L;
    @ApiModelProperty(value = "book name", required = true)
    @NotNull(message = "name is required")
    private String name;
    private String description;
    private String author;
    @ApiModelProperty(value = "book Type/Classification", required = true)
    @NotNull(message = "Type/Classification is required")
    private String type;
    @ApiModelProperty(value = "book price", required = true)
    @NotNull(message = "book price is required")
    private Double price;
    @ApiModelProperty(value = "book isbn", required = true)
    @NotNull(message = "isbn is required")
    private String isbn;
}
