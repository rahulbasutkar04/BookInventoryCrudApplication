package com.example.CRUDAPP.controller;


import com.example.CRUDAPP.entity.Book;
import com.example.CRUDAPP.service.BookService;
import com.example.CRUDAPP.service.exception.BookNotFoundException;
import com.example.CRUDAPP.service.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")

    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.save(book);
            return ResponseEntity.ok(savedBook);
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooks() {
        return ResponseEntity.ok(bookService.find());
    }

    @GetMapping("/book")
    public Optional<Book> getBookById(@RequestParam(required = false) String name) {
        return bookService.findBy(name);
    }


    @GetMapping("book/{name}")
    public Optional<Book> getBookByName(@PathVariable String name) {
        return bookService.findBy(name);
    }

    @DeleteMapping("/delete/{name}")
    public String deleteBook(@PathVariable String name) {
        return bookService.deleteBook(name);
    }

    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {

        try {
            bookService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateBook(@RequestBody Book updatedBook, @PathVariable String name) {
        try {
            String result = bookService.updateBook(name, updatedBook);
            return ResponseEntity.ok(result);
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the book.");
        }
    }

}
