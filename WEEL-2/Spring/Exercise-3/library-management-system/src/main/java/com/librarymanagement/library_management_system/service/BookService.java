// src/main/java/com/librarymanagement/library_management_system/service/BookService.java
package com.librarymanagement.library_management_system.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    public List<String> getAllBooks() {
        return Arrays.asList("Spring in Action", "Effective Java", "Clean Code", "Java Concurrency in Practice", "Design Patterns");
    }

    public void addBook(String book) {
        // Simulate adding a book
        System.out.println(book + " added to the library.");
    }
}
