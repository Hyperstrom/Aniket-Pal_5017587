package com.library.librarymanagement;

import com.library.librarymanagement.model.Book;
import com.library.librarymanagement.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LibrarymanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrarymanagementApplication.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = (BookService) context.getBean("bookService");

        List<Book> books = Arrays.asList(
				new Book("Brave New World", "Aldous Huxley", "12345"),
				new Book("Emma", "Jane Austen", "67890"),
				new Book("To Kill a Mockingbird", "Harper Lee", "54321"),
				new Book("Gone Girl", "Gillian Flynn", "98765"),
				new Book("Big Little Lies", "Liane Moriarty", "13579")
			
        );

        books.forEach(bookService::addBook);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. List all books");
            System.out.println("2. Search for a book by ISBN");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("All books in the library:");
                    bookService.listAllBooks().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    Book book = bookService.findBookByIsbn(isbn);
                    if (book != null) {
                        System.out.println("Book found: " + book);
                    } else {
                        System.out.println("Book not found with ISBN: " + isbn);
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
