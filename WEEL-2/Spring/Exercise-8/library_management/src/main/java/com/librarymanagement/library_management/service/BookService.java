package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String book, String author, int year) {
        bookRepository.addBook(book, author, year);
    }

    public List<String> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}
