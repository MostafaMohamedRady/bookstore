package com.bookstore.demo.controller;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.BillResponseDTO;
import com.bookstore.demo.dto.CheckoutRequest;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.service.BillService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BillControllerTest {

    @InjectMocks
    private BillController controller;

    @Mock
    private BillService service;

    @Test
    void checkout() {
        BillResponseDTO responseDTO = new BillResponseDTO();
        responseDTO.setTotalAmount(100.0D);
        responseDTO.setNetAmount(80.0D);
        responseDTO.setDiscountAmount(20.0D);
        responseDTO.setBookList(Lists.newArrayList());

        Mockito.when(service.checkout(Mockito.isA(CheckoutRequest.class))).thenReturn(responseDTO);
        CheckoutRequest request = new CheckoutRequest();
        request.setPromoCode("PRO2020");
        request.setIsbnList(Arrays.asList("10-9-10", "20-20-10", "100-101-101"));
        ResponseEntity<CustomResponse<BillResponseDTO>> checkout = controller.checkout(request);
        assertNotNull(checkout);
        assertNotNull(checkout.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, checkout.getBody().getStatus());
    }
}