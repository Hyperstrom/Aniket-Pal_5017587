package com.librarymanagement.library_management;

import com.librarymanagement.library_management.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the bookService bean configured with constructor injection
        BookService bookServiceConstructor = context.getBean("bookService", BookService.class);

        // Test constructor injection
        bookServiceConstructor.addBook("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997);
        bookServiceConstructor.addBook("The Hobbit", "J.R.R. Tolkien", 1937);

        System.out.println("Books added using constructor injection:");
        for (String book : bookServiceConstructor.getAllBooks()) {
            System.out.println(book);
        }

        // Retrieve the bookService bean configured with setter injection
        BookService bookServiceSetter = context.getBean("bookServiceSetter", BookService.class);

        // Test setter injection
        bookServiceSetter.addBook("The Catcher in the Rye", "J.D. Salinger", 1951);
        bookServiceSetter.addBook("To Kill a Mockingbird", "Harper Lee", 1960);

        System.out.println("\nBooks added using setter injection:");
        for (String book : bookServiceSetter.getAllBooks()) {
            System.out.println(book);
        }
    }
}
