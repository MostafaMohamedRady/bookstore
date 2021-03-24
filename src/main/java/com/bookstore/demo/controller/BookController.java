package com.bookstore.demo.controller;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.service.BookService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api("Book Controller")
@RestController
@RequestMapping("/v1/book")
@Slf4j
public class BookController {

    @Autowired
    private BookService service;

    @ApiOperation(value = "Book List.", response = List.class)
    @ApiResponses({@ApiResponse(code = 200, message = "All Book details found on system."),
            @ApiResponse(code = 404, message = "No Books found."),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @GetMapping("/list")
    public ResponseEntity<CustomResponse<List<BookDTO>>> getBookList() {
        List<BookDTO> bookList = service.findAll();
        CustomResponse<List<BookDTO>> response = CustomResponse.<List<BookDTO>>builder()
                .data(bookList)
                .status(ResponseStatusEnum.SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Book Details.", response = BookDTO.class)
    @ApiResponses({@ApiResponse(code = 200, message = "book details found on system."),
            @ApiResponse(code = 404, message = "book not found."),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @GetMapping("/{isbn}")
    public ResponseEntity<CustomResponse<BookDTO>> getBook(@PathVariable @NotNull String isbn) {
        BookDTO bookDTO = service.findByIsbn(isbn);
        CustomResponse<BookDTO> response = CustomResponse.<BookDTO>builder()
                .data(bookDTO)
                .status(ResponseStatusEnum.SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Add New Book.")
    @ApiResponses({@ApiResponse(code = 200, message = "Book added successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @PostMapping("/add")
    public ResponseEntity<CustomResponse<BookDTO>> addBook(@RequestBody @Valid BookDTO book) {
        BookDTO bookDTO = service.save(book);
        CustomResponse<BookDTO> response = CustomResponse.<BookDTO>builder()
                .data(bookDTO)
                .message(BookStoreConstant.ADD_BOOK_SUCCESS)
                .status(ResponseStatusEnum.SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Book.")
    @ApiResponses({@ApiResponse(code = 200, message = "Book updated successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @PutMapping("/{isbn}/update")
    public ResponseEntity<CustomResponse<BookDTO>> updateBook(@PathVariable @NotNull String isbn, @RequestBody @Valid BookDTO bookRequest) {
        BookDTO bookDTO = service.update(isbn, bookRequest);
        CustomResponse<BookDTO> response = CustomResponse.<BookDTO>builder()
                .data(bookDTO)
                .status(ResponseStatusEnum.SUCCESS)
                .message(BookStoreConstant.UPDATE_BOOK_SUCCESS).build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Book.")
    @ApiResponses({@ApiResponse(code = 200, message = "Book deleted successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Please contact admin.")})
    @DeleteMapping("/{isbn}/delete")
    public ResponseEntity<CustomResponse<String>> deleteBook(@PathVariable @NotNull String isbn) {
        service.delete(isbn);
        CustomResponse<String> response = CustomResponse.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message(BookStoreConstant.DELETE_SUCCESS).build();
        return ResponseEntity.ok(response);
    }
}
