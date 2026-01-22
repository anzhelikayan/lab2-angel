package ua.edu.kpi.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ua.edu.kpi.library.model.Book;
import ua.edu.kpi.library.model.Reader;
import ua.edu.kpi.library.repository.BookRepository;
import ua.edu.kpi.library.repository.ReaderRepository;

/**
 * Initializes database with sample data for supporting entities (Readers).
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DatabaseInitializer(ReaderRepository readerRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        // Create readers (supporting entities)
        if (readerRepository.count() == 0) {
            Reader reader1 = new Reader("John Smith", "john.smith@example.com");
            Reader reader2 = new Reader("Emily Johnson", "emily.johnson@example.com");
            Reader reader3 = new Reader("Michael Brown", "michael.brown@example.com");
            Reader reader4 = new Reader("Sarah Williams", "sarah.williams@example.com");
            Reader reader5 = new Reader("David Miller", "david.miller@example.com");
            Reader reader6 = new Reader("Jessica Davis", "jessica.davis@example.com");
            Reader reader7 = new Reader("Robert Wilson", "robert.wilson@example.com");
            Reader reader8 = new Reader("Lisa Anderson", "lisa.anderson@example.com");
            Reader reader9 = new Reader("James Taylor", "james.taylor@example.com");
            Reader reader10 = new Reader("Maria Garcia", "maria.garcia@example.com");
            
            readerRepository.save(reader1);
            readerRepository.save(reader2);
            readerRepository.save(reader3);
            readerRepository.save(reader4);
            readerRepository.save(reader5);
            readerRepository.save(reader6);
            readerRepository.save(reader7);
            readerRepository.save(reader8);
            readerRepository.save(reader9);
            readerRepository.save(reader10);

            // Create some sample books
            if (bookRepository.count() == 0) {
                // Books with readers (borrowed)
                Book book1 = new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "978-0-7475-3269-6", reader1);
                Book book2 = new Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0-544-00035-0", reader2);
                
                // Book without reader (available)
                Book book3 = new Book("1984", "George Orwell", "978-0-452-28423-4", null);
                
                bookRepository.save(book1);
                bookRepository.save(book2);
                bookRepository.save(book3);
            }
        }
    }
}

