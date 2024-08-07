package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.repository.BookRepository;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    // Setter method for bookRepository
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Other methods...
    public void addBook(String book) {
        bookRepository.addBook(book);
    }

    public List<String> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}
