package com.bookstore.demo.service;

import com.bookstore.demo.dto.BillResponseDTO;
import com.bookstore.demo.dto.CheckoutRequest;

public interface BillService {
    BillResponseDTO checkout(CheckoutRequest request);
}
