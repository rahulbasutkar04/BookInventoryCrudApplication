package com.example.CRUDAPP.repository;

import com.example.CRUDAPP.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  BookRepository extends MongoRepository<Book,String> {
}
