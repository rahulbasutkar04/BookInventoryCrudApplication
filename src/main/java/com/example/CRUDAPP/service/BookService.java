package com.example.CRUDAPP.service;

import com.example.CRUDAPP.entity.Book;
import com.example.CRUDAPP.repository.BookRepository;
import com.example.CRUDAPP.service.exception.BookNotFoundException;
import com.example.CRUDAPP.service.exception.InvalidInputException;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book save(Book book) throws InvalidInputException {
        if (validateInput(book)) {
            return bookRepository.save(book);
        } else {
            throw new InvalidInputException("Invalid Input Parameter..");
        }
    }

    public List<Book> find() {
        return bookRepository.findAll();
    }


    private boolean validateInput(Book book) throws InvalidInputException {
        boolean isValid = true;
        if (book == null || StringUtils.isEmpty(book.getName()) || StringUtils.isEmpty(book.getAuthor())
                || StringUtils.isEmpty(book.getPublication()) || book.getPrice() <= 0) {
            isValid = false;
            throw new InvalidInputException("Empty or invalid input provided");
        }
        return isValid;
    }

    public Optional<Book> findBy(String name) {
        return bookRepository.findById(name);
    }

    public String deleteBook(String name) {
        Optional<Book> bookOptional = bookRepository.findById(name);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(name);
            return "Book with ID " + name + " deleted";
        } else {
            return "Book with ID " + name + " not found";
        }
    }

    public String deleteAll() {
        long countBeforeDelete = bookRepository.count();
        bookRepository.deleteAll();
        long countAfterDelete = bookRepository.count();
        long totalDeleted = countBeforeDelete - countAfterDelete;
        return totalDeleted + " books deleted";
    }

    public String updateBook(String name, Book updatedBook) throws InvalidInputException, BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(name);
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException("Book not found with id: " + name);
        }

        Book existingBook = optionalBook.get();
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublication(updatedBook.getPublication());
        existingBook.setPrice(updatedBook.getPrice());

        validateInput(existingBook);

        Book savedBook = bookRepository.save(existingBook);
        return "Book updated: " + savedBook.getName();
    }
}
