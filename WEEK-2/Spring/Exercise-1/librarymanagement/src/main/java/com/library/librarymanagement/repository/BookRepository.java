package com.library.librarymanagement.repository;

import com.library.librarymanagement.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<Book> books = new ArrayList<>();

    public void save(Book book) {
        books.add(book);
        System.out.println("Added book: " + book);
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }
}
