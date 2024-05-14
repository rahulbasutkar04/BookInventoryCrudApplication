package com.example.CRUDAPP.service;

import com.example.CRUDAPP.entity.Book;
import com.example.CRUDAPP.repository.BookRepository;
import com.example.CRUDAPP.service.BookService;
import com.example.CRUDAPP.service.exception.BookNotFoundException;
import com.example.CRUDAPP.service.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void shouldBeAbleToSaveBookInRepository() throws InvalidInputException {
        Book book = new Book("Clean Code1", "Uncle Bob", "OReally", 100);

        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.save(book));
        Mockito.verify(bookRepository).save(book);

    }


    @Test
    void shouldBeAbleToReturnAllBooksFromRepository() {

        List<Book> books = new ArrayList<>();
        books.add(new Book("Clean Code", "Uncle Bob", "OReally", 100));
        books.add(new Book("Design Patterns", "Gang of Four", "Addison-Wesley", 150));

        when(bookRepository.findAll()).thenReturn(books);


        assertEquals(books, bookService.find());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForEmptyInput() {

        Book book = new Book("", "", "", 0);
        assertThrows(InvalidInputException.class, () -> bookService.save(book));
    }


    @Test
    void shouldBeAbleToReturnBookByName() {
        // arrange
        Book expectedBook = new Book("Clean Code1", "Uncle Bob", "OReally", 100);
        when(bookRepository.findById("Clean Code1")).thenReturn(Optional.of(expectedBook));

        // act
        Optional<Book> actualBookOptional = bookService.findBy("Clean Code1");

        // assert
        assertEquals(expectedBook, actualBookOptional.orElse(null));
    }

    @Test
    void shouldBeAbleToUpdateBook() throws InvalidInputException, BookNotFoundException {
        // arrange
        String name = "Clean Code";
        Book existingBook = new Book("Clean Code", "Author", "Publication", 100);
        Book updatedBook = new Book("Clean Code", "New Author", "New Publication", 200);

        when(bookRepository.findById(name)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        // act
        String result = bookService.updateBook(name, updatedBook);

        // assert
        assertEquals("Book updated: Clean Code", result);
        assertEquals("New Author", existingBook.getAuthor());
        assertEquals("New Publication", existingBook.getPublication());
        assertEquals(200, existingBook.getPrice());
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenUpdatingNonExistingBook() {
        // arrange
        String name = "Non Existing Book";
        Book updatedBook = new Book("Clean Code", "New Author", "New Publication", 200);

        when(bookRepository.findById(name)).thenReturn(Optional.empty());

        // act & assert
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(name, updatedBook));
        assertEquals("Book not found with id: " + name, exception.getMessage());
    }

    @Test
    void shouldDeleteExistingBook() {
        // arrange
        String name = "Clean Code";
        Book book = new Book(name, "Author", "Publication", 100);
        when(bookRepository.findById(name)).thenReturn(Optional.of(book));

        // act
        String result = bookService.deleteBook(name);

        // assert
        assertEquals("Book with ID Clean Code deleted", result);
    }

    @Test
    void shouldBeAbleToReturnNotFoundWhenDeletingNonExistingBook() {
        // arrange
        String name = "Non Existing Book";
        when(bookRepository.findById(name)).thenReturn(Optional.empty());

        // act
        String result = bookService.deleteBook(name);

        // assert
        assertEquals("Book with ID Non Existing Book not found", result);
    }

    @Test
    void shouldBeABleToDeleteAllBooks() {
        // arrange
        when(bookRepository.count()).thenReturn(5L, 0L);

        // act
        String result = bookService.deleteAll();

        // assert
        assertEquals("5 books deleted", result);

    }


}
