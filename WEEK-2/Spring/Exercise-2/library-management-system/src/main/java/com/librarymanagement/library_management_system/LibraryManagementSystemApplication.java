// src/main/java/com/librarymanagement/library_management_system/LibraryManagementSystemApplication.java
package com.librarymanagement.library_management_system;

import com.librarymanagement.library_management_system.service.BookService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner run(BookService bookService) {
        return args -> {
            bookService.addBook("Spring in Action");
            bookService.addBook("Effective Java");
            bookService.addBook("Clean Code");
            bookService.addBook("Java Concurrency in Practice");
            bookService.addBook("Design Patterns");

            System.out.println("Books in the library:");
            for (String book : bookService.getAllBooks()) {
                System.out.println(book);
            }
        };
    }
}
