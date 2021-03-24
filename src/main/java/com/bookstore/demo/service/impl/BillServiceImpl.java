package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.dto.BillResponseDTO;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.dto.CheckoutRequest;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.service.BillService;
import com.bookstore.demo.service.BookService;
import com.bookstore.demo.service.PromoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BillServiceImpl implements BillService {
    @Autowired
    private BookService bookService;

    @Autowired
    private PromoService promoService;

    /**
     * receive isbn list and promo code
     * fetch all book details from db
     * fetch promo code details from db / discount per book type
     * calculate total amount for all books
     * calculate discount per type if applicable
     * @param request
     * @return
     */
    @Override
    public BillResponseDTO checkout(CheckoutRequest request) {
        log.info("isbnList {} :- get bill details started", request.getIsbnList());
        BillResponseDTO responseDTO = new BillResponseDTO();
        List<String> isbnList = request.getIsbnList();
        List<BookDTO> dtoList = bookService.findAllByIsbnIn(isbnList);
        log.info("checkout BOOK-LIST :- {}", dtoList);
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new BookStoreException(BookStoreConstant.NO_DATA_FOUND);
        }
        double total = dtoList.stream()
                .filter(b -> b != null &&
                        b.getPrice() != null).mapToDouble(BookDTO::getPrice)
                .sum();
        double discount = applyDiscount(dtoList, request.getPromoCode());
        responseDTO.setBookList(dtoList);
        responseDTO.setTotalAmount(total);
        responseDTO.setNetAmount(total - discount);
        responseDTO.setDiscountAmount(discount);
        return responseDTO;
    }

    private double applyDiscount(List<BookDTO> dtoList, String promoCode) {
        double discount = 0.0;
        if (!ObjectUtils.isEmpty(promoCode)) {
            PromotionDTO promotionDTO = promoService.findByCode(promoCode);
            if (promotionDTO != null && !CollectionUtils.isEmpty(promotionDTO.getDiscount())) {
                Map<String, Integer> discountDetails = promotionDTO.getDiscount();
                Map<String, Double> collect = dtoList.stream().filter(e -> discountDetails.get(e.getType()) != null).collect(Collectors.groupingBy(BookDTO::getType, Collectors.summingDouble(BookDTO::getPrice)));
                for (Map.Entry<String, Double> entry : collect.entrySet()) {
                    double value = discountDetails.get(entry.getKey());
                    discount += (value * entry.getValue()) / 100;
                }
            }
        }
        return discount;
    }


}
