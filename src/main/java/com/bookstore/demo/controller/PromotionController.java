package com.bookstore.demo.controller;


import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.dto.PromotionDTO;
import com.bookstore.demo.service.PromoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api("Promotion Controller")
@RestController
@RequestMapping("/v1/promo")
@Slf4j
public class PromotionController {

    @Autowired
    private PromoService service;

    @ApiOperation(value = "Promo List.", response = List.class)
    @ApiResponses({@ApiResponse(code = 200, message = "All Promo details found on system."),
            @ApiResponse(code = 404, message = "No Promo found."),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @GetMapping("/list")
    public ResponseEntity<CustomResponse<List<PromotionDTO>>> getPromoList() {
        List<PromotionDTO> promotionDTOList = service.findAll();
        CustomResponse<List<PromotionDTO>> response = CustomResponse.<List<PromotionDTO>>builder()
                .data(promotionDTOList)
                .message(BookStoreConstant.FETCH_SUCCESSFULLY)
                .status(ResponseStatusEnum.SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Add New Promo Code.")
    @ApiResponses({@ApiResponse(code = 200, message = "Promo added successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @PostMapping("/add")
    public ResponseEntity<CustomResponse<String>> addPromo(@RequestBody @Valid PromotionDTO dto) {
        service.save(dto);
        CustomResponse<String> response = CustomResponse.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message(BookStoreConstant.ADD_PROMO_SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Promo.")
    @ApiResponses({@ApiResponse(code = 200, message = "Promo deleted successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @DeleteMapping("/{code}/delete")
    public ResponseEntity<CustomResponse<String>> deletePromo(@PathVariable @NotNull String code) {
        service.delete(code);
        CustomResponse<String> response = CustomResponse.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message(BookStoreConstant.DELETE_SUCCESS).build();
        return ResponseEntity.ok(response);
    }

}
