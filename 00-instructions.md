# Create Persistence Layer

Let's create the persistence layer for our application using Spring Data JPA. This layer will handle the interaction with the database.

## Step 1 - Set up the Entities
1. Create a new package named `com.lousing.poc.domain`.
2. Inside this package, create a new Java class annotated with `@Entity` and named `Author.java` with the following fields
    - `id` (Long) | annotation: `@Id`, `@GeneratedValue(strategy = GenerationType.AUTO)`
    - `fisrtName` (String)
    - `lastName` (String)
    - `books` (Set<Book>) | annotation: `@OneToMany(mappedBy = "author")`
3. create similarly another Java class named `Book` with the following fields
    - `id` (Long) | annotation: `@Id`, `@GeneratedValue(strategy = GenerationType.AUTO)`
    - `title` (String)
    - `isbn` (String)
    - `authors` (Set<Author>) | annotation: `@ManyToMany()`, '@JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))'
4. Make sure to include getters, setters, constructors, and `toString()` methods for both classes.

ℹ️ Info: 
- `toString()` is required by hibernate to check the entities are same or not.
- Remember to import necessary JPA annotations from `jakarta.persistence` package.

## Step 2 - Create Repositories
1. Create a new package named `com.lousing.poc.repository`.
2. Inside this package, create a new interface named `AuthorRepository.java` that extends `CrudRepository<Author, Long>`.
3. Similarly, create another interface named `BookRepository.java` that extends `CrudRepository<Book, Long>`.

ℹ️ Info: 
- Spring Data JPA will automatically provide the implementation for these repositories at runtime.

## Step 2 - Persist Data
To test the persistence layer, we can add some initial data when the application starts.
1. Create a package named `com.lousing.poc.bootstrap`.
2. Inside this package, create a class named `DataLoader.java` that implements `CommandLineRunner`.
3. In the `run` method, use the `AuthorRepository` and `BookRepository` to save some sample authors and books to the database.
   - Create one book with these details:
     - Title: "Domain-Driven Design"
     - ISBN: "978-0321125217"
   - Create another book with these details:
     - Title: "J2EE Development without EJB"
     - ISBN: "978-1617294945"
   - Create one author with these details:
     - First Name: "Eric"
     - Last Name: "Evans"
   - Create another author with these details:
     - First Name: "Rod"
     - Last Name: "Johnson"
4. Associate the authors with the books appropriately. Associate "Eric Evans" with "Domain-Driven Design" and "Rod Johnson" with "J2EE Development without EJB".
5. Make sure to annotate the `DataLoader` class with `@Component` so that Spring Boot can detect it.

## Step 3 - Add another entity called Publisher
1. Create a new POJO Java class named `Publisher.java` in the `com.lousing.poc.domain` package with the following fields:
    - `id` (Long) | annotation: `@Id`, `@GeneratedValue(strategy = GenerationType.AUTO)`
    - `name` (String)
    - `address` (String)
    - `city` (String)
    - `state` (String)
    - `zip` (String)
    - `books` (Set<Book>) | annotation: `@OneToMany(mappedBy = "publisher")`
2. Update the `Book` entity to include a reference to `Publisher`:
    - Add a field `publisher` (Publisher) | annotation: `@ManyToOne`
3. Create a new interface named `PublisherRepository.java` in the `com.lousing.poc.repository` package that extends `CrudRepository<Publisher, Long>`.
4. Update the `DataLoader` class to create and associate a `Publisher` with the books created earlier. For example:
   - Create a publisher with these details:
     - Name: "Tech Books Publishing"
     - Address: "123 Tech St"
     - City: "Techville"
     - State: "TX"
     - Zip: "75001"
   - Associate this publisher with both books created earlier.

## Step 4 - Verify the Data | Enable H2 Console
1. Let's enable the H2 console to verify the data.
2. Open `application.properties` file and add the following line:
   ```properties
   spring.h2.console.enabled=true
   ```
3. Run the application `.\mvnw.cmd spring-boot:run`
4. and navigate to `http://localhost:8080/h2-console`
4. Use the default JDBC URL `jdbc:h2:mem:testdb`, username `sa`, and no password to connect.
5. Execute SQL queries to verify that the authors, books, and publishers have been persisted correctly.

## Step 5 - Create Service Layer
1. Create a new package named `com.lousing.poc.service`.
2. Inside this package, create a new interface named `BookService.java`.
3. Define methods in the `BookService` interface for common operations such as:
   - `Iterable<Book> findAll()`
   - `Optional<Book> getBookById(Long id)`
   - `Book saveBook(Book book)`
   - `void deleteBook(Long id)`
4. Create a class named `BookServiceImpl.java` that implements the `BookService` interface.
5. In `BookServiceImpl`, inject the `BookRepository` using constructor injection.
6. Implement the methods defined in the `BookService` interface using the `BookRepository`.
7. Annotate the `BookServiceImpl` class with `@Service` so that Spring can manage it as a service component.

## Step 6 - Create Controller Layer
1. Create a new package named `com.lousing.poc.controller`.
2. Inside this package, create a new class named `BookController.java`.
3. Annotate the `BookController` class with `@RestController` and `@RequestMapping("/api/books")`.
4. Inject the `BookService` into the `BookController` using constructor injection.
5. Define endpoints in the `BookController` for the following operations:
   - `GET /books` - to retrieve all books
6. Name the method as `getBooks()`.
7. Annotate the method with `@RequestMapping`.
8. Ensure this method returns string which is a view name `books`

## Step 7 - Create Thymeleaf Template
1. Create a new directory named `templates` under `src/main/resources`.
2. Inside the `templates` directory, create a new HTML file named `books.html`.
3. Add the following Thymeleaf template code to display the list of books:
   ```html
   <!DOCTYPE html>
   <html xmlns:th="http://www.thymeleaf.org">
   <head>
       <title>Books List</title>
   </head>
   <body>
       <h1>Books</h1>
       <table>
           <thead>
               <tr>
                   <th>Title</th>
                   <th>ISBN</th>
                   <th>Authors</th>
                   <th>Publisher</th>
               </tr>
           </thead>
           <tbody>
               <tr th:each="book : ${books}">
                   <td th:text="${book.title}">Title</td>
                   <td th:text="${book.isbn}">ISBN</td>
                   <td>
                       <span th:each="author : ${book.authors}" th:text="${author.firstName} + ' ' + ${author.lastName} + ' '">Author</span>
                   </td>
                   <td th:text="${book.publisher.name}">Publisher</td>
               </tr>
           </tbody>
       </table>
   </body>
   </html>
   ```
   4. This template will display a table of books with their titles, ISBNs, authors, and publishers.
   5. Make sure to include Thymeleaf dependency in your `pom.xml` if not already present:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
   ```
   