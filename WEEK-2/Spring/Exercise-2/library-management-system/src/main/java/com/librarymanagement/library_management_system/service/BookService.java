// src/main/java/com/librarymanagement/library_management_system/service/BookService.java
package com.librarymanagement.library_management_system.service;

import com.librarymanagement.library_management_system.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String bookName) {
        bookRepository.save(bookName);
    }

    public List<String> getAllBooks() {
        return bookRepository.findAll();
    }
}
