package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.model.Book;
import com.library.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        System.out.println("\nBook saved: \n" + savedBook + "\n");
        return savedBook;
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println("\nBooks retrieved: \n" + books + "\n");
        return books;
    }

    public Book findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        System.out.println("\nBook found by ISBN: \n" + book + "\n");
        return book;
    }
}
