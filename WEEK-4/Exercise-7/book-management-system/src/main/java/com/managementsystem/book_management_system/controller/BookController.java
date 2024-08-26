// src/main/java/com/managementsystem/book_management_system/controller/BookController.java
package com.managementsystem.book_management_system.controller;

import com.managementsystem.book_management_system.dto.BookDTO;
import com.managementsystem.book_management_system.entity.Book;
import com.managementsystem.book_management_system.mapper.BookMapper;
import com.managementsystem.book_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> new ResponseEntity<>(bookMapper.toBookDTO(b), HttpStatus.OK))
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(bookMapper.toBookDTO(savedBook), HttpStatus.CREATED);
    }
}
