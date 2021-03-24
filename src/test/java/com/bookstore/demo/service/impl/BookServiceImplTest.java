package com.bookstore.demo.service.impl;

import com.bookstore.demo.constant.BookStoreConstant;
import com.bookstore.demo.converter.BookConverter;
import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.entity.BookEntity;
import com.bookstore.demo.exception.BookStoreException;
import com.bookstore.demo.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl service;
    @Mock
    private BookRepository repository;
    @Spy
    private BookConverter converter;

    @Test
    void findAll() {
        List<BookEntity> entityList = new ArrayList<>();
        BookEntity entity = new BookEntity();
        entity.setName("name");
        entity.setDescription("desc");
        entity.setAuthor("author");
        entity.setType("comic");
        entity.setPrice(100.0D);
        entity.setIsbn("10-9-10");
        entityList.add(entity);

        Mockito.when(repository.findAll()).thenReturn(entityList);
        List<BookDTO> all = service.findAll();
        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findAll_whenNoDataFound() {
        Mockito.when(repository.findAll()).thenReturn(null);
        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.findAll();
        });
        Assertions.assertEquals(BookStoreConstant.NO_DATA_FOUND, bookStoreException.getMessage());
    }

    @Test
    void findByIsbn() {
        BookEntity entity = new BookEntity();
        entity.setName("name");
        entity.setDescription("desc");
        entity.setAuthor("author");
        entity.setType("comic");
        entity.setPrice(100.0D);
        entity.setIsbn("10-9-10");

        Mockito.when(repository.findByIsbn(Mockito.anyString())).thenReturn(entity);
        BookDTO byIsbn = service.findByIsbn("10-9-10");
        Assertions.assertNotNull(byIsbn);
    }

    @Test
    void findByIsbn_whenNoMatchingData_throwException() {
        Mockito.when(repository.findByIsbn(Mockito.anyString())).thenReturn(null);
        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.findByIsbn("10-9-10");
        });
        Assertions.assertEquals(BookStoreConstant.NO_DATA_FOUND, bookStoreException.getMessage());
    }

    @Test
    void save() {
        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");

        Mockito.when(repository.save(Mockito.isA(BookEntity.class))).thenReturn(new BookEntity());
        BookDTO save = service.save(dto);
        Assertions.assertNotNull(save);
    }

    @Test
    void update() {

        Mockito.when(repository.findByIsbn(Mockito.anyString())).thenReturn(new BookEntity());
        Mockito.when(repository.save(Mockito.isA(BookEntity.class))).thenReturn(new BookEntity());

        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");
        BookDTO update = service.update("10-9-10", dto);

        Assertions.assertNotNull(update);
    }

    @Test
    void update_whenNoMatchingDataFound_throwException() {

        Mockito.when(repository.findByIsbn(Mockito.anyString())).thenReturn(null);

        BookDTO dto = new BookDTO();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setAuthor("author");
        dto.setType("fiction");
        dto.setPrice(100.0D);
        dto.setIsbn("10-9-10");

        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.update("10-9-10", dto);
        });
        Assertions.assertEquals(BookStoreConstant.NO_DATA_FOUND, bookStoreException.getMessage());
        verify(repository, times(0)).save(Mockito.isA(BookEntity.class));

    }

    @Test
    void findAllByIsbnIn() {
        List<String> list = Arrays.asList("10-9");
        List<BookDTO> allByIsbnIn = service.findAllByIsbnIn(list);
        Assertions.assertNotNull(allByIsbnIn);
    }

    @Test
    void delete() {
        List<BookEntity> entityList = new ArrayList<>();
        BookEntity entity = new BookEntity();
        entity.setName("name");
        entity.setDescription("desc");
        entity.setAuthor("author");
        entity.setType("comic");
        entity.setPrice(100.0D);
        entity.setIsbn("10-9-10");
        entityList.add(entity);

        Mockito.when(repository.findAllByIsbnIn(Mockito.anyList())).thenReturn(entityList);
        service.delete("10-9-10");
        verify(repository, times(1)).deleteAll(Mockito.anyIterable());

    }

    @Test
    void delete_whenNoDataFound_throwException() {
        Mockito.when(repository.findAllByIsbnIn(Mockito.anyList())).thenReturn(null);

        BookStoreException bookStoreException = Assertions.assertThrows(BookStoreException.class, () -> {
            service.delete("10-9-10");
        });
        Assertions.assertEquals(BookStoreConstant.NO_MATCHING_DATA, bookStoreException.getMessage());
        verify(repository, times(0)).deleteAll(Mockito.anyIterable());

    }
}