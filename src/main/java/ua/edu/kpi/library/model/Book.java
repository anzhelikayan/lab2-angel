package ua.edu.kpi.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a book in the library.
 * This is the main entity for CRUD operations.
 */
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book title cannot be empty")
    @Size(min = 1, max = 200, message = "Book title must be between 1 and 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String author;

    @Column(length = 50)
    private String isbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id", nullable = true)
    private Reader reader;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    public Book() {
    }

    public Book(String title, String author, String isbn, Reader reader) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.reader = reader;
        // If book has a reader, it's borrowed (not available)
        this.isAvailable = (reader == null);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
        // Automatically update availability based on reader
        if (reader != null) {
            this.isAvailable = false; // Book is borrowed
        } else {
            this.isAvailable = true; // Book is available
        }
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}

