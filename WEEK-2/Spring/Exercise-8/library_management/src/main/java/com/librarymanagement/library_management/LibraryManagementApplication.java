package com.librarymanagement.library_management;

import com.librarymanagement.library_management.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        // Load the Spring context
        @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the bookService bean
        BookService bookService = context.getBean("bookService", BookService.class);

        // Test method calls to trigger AOP logging
        bookService.addBook("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997);
        bookService.addBook("The Hobbit", "J.R.R. Tolkien", 1937);

        System.out.println("Books in library:");
        for (String book : bookService.getAllBooks()) {
            System.out.println(book);
        }
    }
}
