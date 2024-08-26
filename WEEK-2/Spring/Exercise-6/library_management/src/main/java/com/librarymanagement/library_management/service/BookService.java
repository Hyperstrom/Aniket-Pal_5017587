package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String book) {
        bookRepository.addBook(book);
    }

    public List<String> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}
