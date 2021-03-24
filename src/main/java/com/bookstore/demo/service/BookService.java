package com.bookstore.demo.service;

import com.bookstore.demo.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();

    BookDTO findByIsbn(String isbn);

    BookDTO save(BookDTO book);

    BookDTO update(String isbn, BookDTO bookRequest);

    List<BookDTO> findAllByIsbnIn(List<String> isbnList);

    void delete(String isbn);
}
