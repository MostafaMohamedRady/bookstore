package com.bookstore.demo.controller;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.BillResponseDTO;
import com.bookstore.demo.dto.CheckoutRequest;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Bill Controller")
@RestController
@RequestMapping("/v1/bill")
@Slf4j
public class BillController {

    @Autowired
    private BillService service;

    @ApiOperation(value = "Checkout method will take list of books as parameters plus optional promotion code and return total price after discount (if applicable).")
    @ApiResponses({@ApiResponse(code = 200, message = "successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @PostMapping("/Checkout")
    public ResponseEntity<CustomResponse<BillResponseDTO>> checkout(@RequestBody @Valid CheckoutRequest request) {
        BillResponseDTO billResponseDTO = service.checkout(request);
        CustomResponse<BillResponseDTO> response = CustomResponse.<BillResponseDTO>builder()
                .data(billResponseDTO)
                .status(ResponseStatusEnum.SUCCESS).build();
        return ResponseEntity.ok(response);
    }
}
