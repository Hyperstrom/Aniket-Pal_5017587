// src/main/java/com/librarymanagement/library_management_system/repository/BookRepository.java
package com.librarymanagement.library_management_system.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private List<String> books = new ArrayList<>();

    public void save(String bookName) {
        books.add(bookName);
    }

    public List<String> findAll() {
        return books;
    }
}
