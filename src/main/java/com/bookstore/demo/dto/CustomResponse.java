package com.bookstore.demo.dto;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {
    private ResponseStatusEnum status;
    private String message;
    private T data;
}
