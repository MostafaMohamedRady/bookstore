package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.converter.BookConverter;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.entity.BookEntity;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.repository.BookRepository;
import com.bookstore.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookConverter converter;

    @Override
    public List<BookDTO> findAll() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        List<BookEntity> entityList = repository.findAll();
        log.info("fetch all book details from DB :- {}", entityList);
        if (CollectionUtils.isEmpty(entityList)) {
            throw new BookStoreException(BookStoreConstant.NO_DATA_FOUND);
        }
        for (BookEntity bookEntity : entityList) {
            BookDTO bookDTO = new BookDTO();
            BeanUtils.copyProperties(bookEntity, bookDTO);
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }

    @Override
    public BookDTO findByIsbn(String isbn) {
        BookDTO dto = new BookDTO();
        BookEntity entity = Optional.ofNullable(repository.findByIsbn(isbn)).orElseThrow(() -> new BookStoreException(BookStoreConstant.NO_DATA_FOUND));
        log.info("Book Id {} :- fetch book details from DB - {}", isbn, entity);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public BookDTO save(BookDTO dto) {
        BookEntity entity = new BookEntity();
        BeanUtils.copyProperties(dto, entity);
        BookEntity save = repository.save(entity);
        log.info("Book {} :- saved successfully to db", save);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(save, bookDTO);
        return bookDTO;
    }

    @Override
    public BookDTO update(String isbn, BookDTO dto) {
        BookEntity entity = Optional.ofNullable(repository.findByIsbn(isbn)).orElseThrow(() -> new BookStoreException(BookStoreConstant.NO_DATA_FOUND));
        BeanUtils.copyProperties(dto, entity, "id");
        BookEntity save = repository.save(entity);
        log.info("Book {} :- updated successfully to db", isbn);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(save, bookDTO);
        return bookDTO;
    }

    @Override
    public List<BookDTO> findAllByIsbnIn(List<String> isbnList) {
        List<BookEntity> entityList = repository.findAllByIsbnIn(isbnList);
        return converter.mapToDto(entityList);
    }

    @Override
    public void delete(String isbn) {
        List<BookEntity> allByIsbnIn = repository.findAllByIsbnIn(Arrays.asList(isbn));
        if (CollectionUtils.isEmpty(allByIsbnIn)){
            throw new BookStoreException(BookStoreConstant.NO_MATCHING_DATA);
        }
        repository.deleteAll(allByIsbnIn);
    }
}
