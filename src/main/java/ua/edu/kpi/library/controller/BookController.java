package ua.edu.kpi.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import ua.edu.kpi.library.model.Book;
import ua.edu.kpi.library.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("readers", bookService.findAllReaders());
        model.addAttribute("isEdit", false);
        return "books/form";
    }

    @PostMapping("/new")
    public String createBook(@Valid @ModelAttribute Book book, 
                            BindingResult result, 
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("readers", bookService.findAllReaders());
            model.addAttribute("isEdit", false);
            return "books/form";
        }
        
        try {
            // Load the full Reader object from the selected ID, or set to null if "No Reader" selected
            if (book.getReader() != null && book.getReader().getId() != null) {
                bookService.findReaderById(book.getReader().getId())
                        .ifPresent(book::setReader);
            } else {
                // Explicitly set reader to null if no reader selected (free access)
                book.setReader(null);
            }
            bookService.save(book);
            String statusMessage = book.getReader() != null ? "Book created successfully (borrowed)!" : "Book created successfully (available for reading)!";
            redirectAttributes.addFlashAttribute("successMessage", statusMessage);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error processing request: " + e.getMessage());
            model.addAttribute("readers", bookService.findAllReaders());
            model.addAttribute("isEdit", false);
            return "books/form";
        }
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    model.addAttribute("readers", bookService.findAllReaders());
                    model.addAttribute("isEdit", false);
                    return "books/form";
                })
                .orElse("redirect:/books");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    model.addAttribute("readers", bookService.findAllReaders());
                    model.addAttribute("isEdit", true);
                    return "books/form";
                })
                .orElse("redirect:/books");
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id,
                            @Valid @ModelAttribute Book book,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("readers", bookService.findAllReaders());
            model.addAttribute("isEdit", true);
            return "books/form";
        }
        
        try {
            book.setId(id);
            // Load the full Reader object from the selected ID, or set to null if "No Reader" selected
            if (book.getReader() != null && book.getReader().getId() != null) {
                bookService.findReaderById(book.getReader().getId())
                        .ifPresent(book::setReader);
            } else {
                // Explicitly set reader to null if no reader selected (free access)
                book.setReader(null);
            }
            bookService.save(book);
            String statusMessage = book.getReader() != null ? "Book updated successfully (borrowed)!" : "Book updated successfully (available for reading)!";
            redirectAttributes.addFlashAttribute("successMessage", statusMessage);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error processing request: " + e.getMessage());
            model.addAttribute("readers", bookService.findAllReaders());
            model.addAttribute("isEdit", true);
            return "books/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error processing request: " + e.getMessage());
        }
        return "redirect:/books";
    }

    @PostMapping("/{id}/toggle-status")
    public String toggleBookStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            // Remove reader to make book available for reading
            if (book.getReader() != null) {
                book.setReader(null); // Remove reader to make book available
                redirectAttributes.addFlashAttribute("successMessage", 
                        "Book is now available for reading (reader removed)!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", 
                        "Book is already available for reading!");
            }
            bookService.save(book);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                    "Error processing request: " + e.getMessage());
        }
        return "redirect:/books";
    }
}

