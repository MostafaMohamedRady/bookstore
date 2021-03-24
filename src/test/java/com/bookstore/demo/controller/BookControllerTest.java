package com.bookstore.demo.controller;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.dto.CustomResponse;
import com.bookstore.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService service;

    @Test
    void getBookList() {
        List<BookDTO> list = new ArrayList<>();
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        list.add(dto);

        Mockito.when(service.findAll()).thenReturn(list);

        ResponseEntity<CustomResponse<List<BookDTO>>> bookList = controller.getBookList();
        assertNotNull(bookList);
        assertNotNull(bookList.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, bookList.getBody().getStatus());
        assertEquals(1, bookList.getBody().getData().size());
    }

    @Test
    void getBook() {
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");

        Mockito.when(service.findByIsbn(Mockito.anyString())).thenReturn(dto);

        ResponseEntity<CustomResponse<BookDTO>> book = controller.getBook("10-9-10");
        assertNotNull(book);
        assertNotNull(book.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, book.getBody().getStatus());
        assertEquals("10-9-10", book.getBody().getData().getIsbn());
    }

    @Test
    void addBook() {
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");

        Mockito.when(service.save(Mockito.isA(BookDTO.class))).thenReturn(dto);

        ResponseEntity<CustomResponse<BookDTO>> responseEntity = controller.addBook(dto);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
    }

    @Test
    void updateBook() {
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");

        Mockito.when(service.update(Mockito.isA(String.class), Mockito.isA(BookDTO.class))).thenReturn(dto);

        ResponseEntity<CustomResponse<BookDTO>> responseEntity = controller.updateBook("10-9-10", dto);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
    }

    @Test
    void deleteBook() {
        ResponseEntity<CustomResponse<String>> responseEntity = controller.deleteBook("10-9-10");
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(ResponseStatusEnum.SUCCESS, responseEntity.getBody().getStatus());
    }
}