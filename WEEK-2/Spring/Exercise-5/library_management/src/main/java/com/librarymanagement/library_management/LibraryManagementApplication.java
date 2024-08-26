package com.librarymanagement.library_management;

import com.librarymanagement.library_management.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the bookService bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Test the configuration
        bookService.addBook("Spring in Action");
        bookService.addBook("Effective Java");
        bookService.addBook("Clean Code");

        for (String book : bookService.getAllBooks()) {
            System.out.println(book);
        }
    }
}
