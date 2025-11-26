package com.lousing.poc.repository;

import org.springframework.data.repository.CrudRepository;
import com.lousing.poc.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}

