// src/main/java/com/librarymanagement/library_management_system/LibraryManagementSystemApplication.java
package com.librarymanagement.library_management_system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Bean;
import com.librarymanagement.library_management_system.service.BookService;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            bookService.addBook("Spring Boot in Action");
            bookService.getAllBooks().forEach(System.out::println);
        };
    }
}
