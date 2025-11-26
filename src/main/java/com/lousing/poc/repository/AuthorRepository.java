package com.lousing.poc.repository;

import org.springframework.data.repository.CrudRepository;
import com.lousing.poc.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
