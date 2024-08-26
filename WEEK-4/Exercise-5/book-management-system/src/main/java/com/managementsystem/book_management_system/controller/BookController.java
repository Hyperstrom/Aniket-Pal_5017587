package com.managementsystem.book_management_system.controller;

import com.managementsystem.book_management_system.entity.Book;
import com.managementsystem.book_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // GET: Retrieve all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "List of Books");
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    // GET: Retrieve a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Book Details");
            return new ResponseEntity<>(b, headers, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Create a new book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // Custom Response Status
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Book Created");
        return new ResponseEntity<>(savedBook, headers, HttpStatus.CREATED);
    }

    // PUT: Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setPrice(bookDetails.getPrice());
            book.setIsbn(bookDetails.getIsbn());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Book Updated");
            return new ResponseEntity<>(bookRepository.save(book), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Delete a book
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Custom Response Status
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.delete(bookOptional.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Book Deleted");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET: Filter books by title and/or author
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        List<Book> books;

        if (title != null && author != null) {
            books = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        } else if (title != null) {
            books = bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContainingIgnoreCase(author);
        } else {
            books = bookRepository.findAll();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Search Results");

        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}
