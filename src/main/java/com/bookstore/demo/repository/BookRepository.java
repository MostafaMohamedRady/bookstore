package com.bookstore.demo.repository;

import com.bookstore.demo.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByIsbnIn(List<String> list);
    BookEntity findByIsbn(String isbn);
}
