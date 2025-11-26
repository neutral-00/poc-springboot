package com.lousing.poc.service;

import com.lousing.poc.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
