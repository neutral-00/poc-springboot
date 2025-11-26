// ...existing code...
package com.lousing.poc.bootstrap;

import com.lousing.poc.domain.Author;
import com.lousing.poc.domain.Book;
import com.lousing.poc.domain.Publisher;
import com.lousing.poc.repository.AuthorRepository;
import com.lousing.poc.repository.BookRepository;
import com.lousing.poc.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public DataLoader(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting DataLoader...");

        // Create authors
        Author eric = new Author("Eric", "Evans");
        Author rod = new Author("Rod", "Johnson");

        eric = authorRepository.save(eric);
        rod = authorRepository.save(rod);

        // Create publisher
        Publisher techBooks = new Publisher("Tech Books Publishing", "123 Tech St", "Techville", "TX", "75001");
        techBooks = publisherRepository.save(techBooks);

        // Create books
        Book ddd = new Book("Domain-Driven Design", "978-0321125217");
        Book j2ee = new Book("J2EE Development without EJB", "978-1617294945");

        // Associate publisher with books
        ddd.setPublisher(techBooks);
        j2ee.setPublisher(techBooks);

        ddd = bookRepository.save(ddd);
        j2ee = bookRepository.save(j2ee);

        // Associate Eric -> Domain-Driven Design
        ddd.getAuthors().add(eric);
        eric.getBooks().add(ddd);

        // Associate Rod -> J2EE Development without EJB
        j2ee.getAuthors().add(rod);
        rod.getBooks().add(j2ee);

        // Persist relationship updates
        bookRepository.save(ddd);
        bookRepository.save(j2ee);
        authorRepository.save(eric);
        authorRepository.save(rod);

        log.info("Loaded bootstrap data: authors={}, books={}, publishers={}", authorRepository.count(), bookRepository.count(), publisherRepository.count());
    }
}
// ...existing code...
