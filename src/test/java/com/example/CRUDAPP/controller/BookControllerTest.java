package com.example.CRUDAPP.controller;

import com.example.CRUDAPP.entity.Book;
import com.example.CRUDAPP.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Test
    void shouldBeAbleToCreateABook() throws Exception {
        // arrange
        Book book = new Book("Clean Code4", "Uncle Bob", "OReally", 100);

        // act
        when(bookService.save(book)).thenReturn(book);

        // assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(book))).andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToGetTheAllBooks() throws Exception {

        // arrange
        Book book = new Book("Clean Code", "Uncle Bob", "OReally", 100);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        // act
        when(bookService.find()).thenReturn(bookList);

        // assert
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.asJsonString(book))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Clean Code"))
                .andDo(print());
    }

    @Test
    void shouldBeABleToReadBookByName() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle Bob", "OReally", 100);

        // act
        when(bookService.findBy("Clean Code")).thenReturn(Optional.of(book));

        // assert
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/book?name=Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.asJsonString(book))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Uncle Bob"))
                .andDo(print());

    }

    @Test
    void shouldBeAbleToDeleteBook() throws Exception {
        // arrange
        String bookName = "Clean Code";

        // act
        when(bookService.deleteBook(bookName)).thenReturn("Book with ID " + bookName + " deleted");

        //assert
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/delete/{name}", bookName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Book with ID " + bookName + " deleted"))
                .andDo(print());
    }

    @Test
    void shouldBeAbleToDeleteAllBooks() throws Exception {
        // arrange
        String expectedMessage = "";

        // act
        when(bookService.deleteAll()).thenReturn(expectedMessage);

        //assert
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(expectedMessage))
                .andDo(print());
    }


    @Test
    void shouldBeAbleToUpdateBook() throws Exception {
        // arrange
        Book updatedBook = new Book("Clean Code", "New Author", "New Publication", 200);
        Book existingBook = new Book("Clean Code", "Existing Author", "Existing Publication", 100);

        // act
        when(bookService.findBy("Clean Code")).thenReturn(Optional.of(existingBook));
        when(bookService.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // assert
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/update/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.asJsonString(updatedBook)))
                .andExpect(status().isOk());
    }

}