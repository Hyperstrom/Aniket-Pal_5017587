package com.librarymanagement.library_management.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<String> books = new ArrayList<>();

    public void addBook(String book, String author, int year) {
        books.add(book + " by " + author + " (Published: " + year + ")");
    }

    public List<String> getAllBooks() {
        return books;
    }
}
