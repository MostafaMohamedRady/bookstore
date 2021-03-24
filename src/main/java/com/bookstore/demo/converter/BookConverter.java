package com.bookstore.demo.converter;

import com.bookstore.demo.dto.BookDTO;
import com.bookstore.demo.entity.BookEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookConverter {

    /**
     * convert book entity list to dtos
     * @param entityList
     * @return
     */
    public List<BookDTO> mapToDto(List<BookEntity> entityList) {
        List<BookDTO> dtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(entityList)) {
            for (BookEntity bookEntity : entityList) {
                dtoList.add(mapToDto(bookEntity));
            }
        }
        return dtoList;
    }

    /**
     * convert book entity to dto
     * @param entity
     * @return
     */
    public BookDTO mapToDto(BookEntity entity) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(entity, bookDTO);
        return bookDTO;
    }
}
