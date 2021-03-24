package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.dto.BillResponseDTO;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.dto.CheckoutRequest;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.service.BookService;
import com.bookstore.demo.service.PromoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @InjectMocks
    private BillServiceImpl service;

    @Mock
    private BookService bookService;

    @Mock
    private PromoService promoService;

    @Test
    void checkout() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("comic");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        bookDTOList.add(dto);

        Mockito.when(bookService.findAllByIsbnIn(Mockito.anyList())).thenReturn(bookDTOList);

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setCode("PRO2020");
        promotionDTO.setDiscount(new HashMap<String, Integer>() {{
            put("comic", 10);
            put("drama", 20);
        }});
        Mockito.when(promoService.findByCode(Mockito.anyString())).thenReturn(promotionDTO);

        CheckoutRequest request = new CheckoutRequest();
        request.setPromoCode("PRO2020");
        request.setIsbnList(Arrays.asList("10-9-10", "20-20-10", "100-101-101"));

        BillResponseDTO checkout = service.checkout(request);
        assertNotNull(checkout);
        assertEquals(100.0, checkout.getTotalAmount());
        assertEquals(90.0, checkout.getNetAmount());
        assertEquals(10.0, checkout.getDiscountAmount());

    }

    @Test
    void checkout_whenBookListNull_throwException() {

        Mockito.when(bookService.findAllByIsbnIn(Mockito.anyList())).thenReturn(null);

        CheckoutRequest request = new CheckoutRequest();
        request.setPromoCode("PRO2020");
        request.setIsbnList(Arrays.asList("10-9-10", "20-20-10", "100-101-101"));

        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.checkout(request);
        });
        Assertions.assertEquals(BookStoreConstant.NO_DATA_FOUND, bookStoreException.getMessage());

    }

    @Test
    void checkout_whenPromoCodeNotSubmitted() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("comic");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        bookDTOList.add(dto);

        Mockito.when(bookService.findAllByIsbnIn(Mockito.anyList())).thenReturn(bookDTOList);

        CheckoutRequest request = new CheckoutRequest();
        request.setIsbnList(Arrays.asList("10-9-10", "20-20-10", "100-101-101"));

        BillResponseDTO checkout = service.checkout(request);
        assertNotNull(checkout);
        assertEquals(100.0, checkout.getTotalAmount());
        assertEquals(100.0, checkout.getNetAmount());
        assertEquals(0.0, checkout.getDiscountAmount());
        verify(promoService, times(0)).findByCode(Mockito.isA(String.class));
    }

    @Test
    void checkout_whenNoPromoCodeMatchingInDB() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("comic");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        bookDTOList.add(dto);

        Mockito.when(bookService.findAllByIsbnIn(Mockito.anyList())).thenReturn(bookDTOList);

        Mockito.when(promoService.findByCode(Mockito.anyString())).thenReturn(null);

        CheckoutRequest request = new CheckoutRequest();
        request.setPromoCode("PRO2020");
        request.setIsbnList(Arrays.asList("10-9-10", "20-20-10", "100-101-101"));

        BillResponseDTO checkout = service.checkout(request);
        assertNotNull(checkout);
        assertEquals(100.0, checkout.getTotalAmount());
        assertEquals(100.0, checkout.getNetAmount());
        assertEquals(0.0, checkout.getDiscountAmount());
        verify(promoService, times(1)).findByCode(Mockito.isA(String.class));
    }

    @Test
    void checkout_multiDiscount() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("comic");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        bookDTOList.add(dto);

        dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("drama");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-20");
        bookDTOList.add(dto);

        dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-30");
        bookDTOList.add(dto);

        Mockito.when(bookService.findAllByIsbnIn(Mockito.anyList())).thenReturn(bookDTOList);

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setCode("PRO2020");
        promotionDTO.setDiscount(new HashMap<String, Integer>() {{
            put("comic", 10);
            put("drama", 20);
            put("fiction", 10);
        }});
        Mockito.when(promoService.findByCode(Mockito.anyString())).thenReturn(promotionDTO);

        CheckoutRequest request = new CheckoutRequest();
        request.setPromoCode("PRO2020");
        request.setIsbnList(Arrays.asList("10-9-10", "10-9-20", "10-9-30"));

        BillResponseDTO checkout = service.checkout(request);
        assertNotNull(checkout);
        assertEquals(300.0, checkout.getTotalAmount());
        assertEquals(260.0, checkout.getNetAmount());
        assertEquals(40.0, checkout.getDiscountAmount());

        verify(bookService, times(1)).findAllByIsbnIn(Mockito.anyList());
        verify(promoService, times(1)).findByCode(Mockito.isA(String.class));

    }

}