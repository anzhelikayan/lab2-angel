package ua.edu.kpi.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.kpi.library.model.Book;
import ua.edu.kpi.library.model.Reader;
import ua.edu.kpi.library.repository.BookRepository;
import ua.edu.kpi.library.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        // Automatically set availability based on reader presence
        if (book.getReader() != null) {
            book.setIsAvailable(false); // Book is borrowed
        } else {
            book.setIsAvailable(true); // Book is available
        }
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

    public Optional<Reader> findReaderById(Long id) {
        return readerRepository.findById(id);
    }
}


