// src/main/java/com/managementsystem/book_management_system/mapper/BookMapper.java
package com.managementsystem.book_management_system.mapper;

import com.managementsystem.book_management_system.dto.BookDTO;
import com.managementsystem.book_management_system.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toBookDTO(Book book);
    Book toBook(BookDTO bookDTO);
}
