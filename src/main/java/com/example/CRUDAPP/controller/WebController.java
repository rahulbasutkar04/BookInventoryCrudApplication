package com.example.CRUDAPP.controller;

import com.example.CRUDAPP.entity.Book;
import com.example.CRUDAPP.service.BookService;
import com.example.CRUDAPP.service.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookService.find());
        return "index";
    }

    @PostMapping("/addBook")
    public String addBook(@RequestBody Book book, Model model) throws InvalidInputException {
        bookService.save(book);
        model.addAttribute("books", bookService.find());
        return "index";
    }
}
