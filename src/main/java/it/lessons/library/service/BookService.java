package it.lessons.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lessons.library.model.Book;
import it.lessons.library.model.Borrowing;
import it.lessons.library.repository.BookRepository;
import it.lessons.library.repository.BorrowingRepository;
import it.lessons.library.repository.CategoryRepository;

@Service
public class BookService {

    private BookRepository bookRepository;

    private BorrowingRepository borrowingRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BorrowingRepository borrowingRepository,
                            CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Book> findBookList(String title) {
        List<Book> result;
         if (title != null && !title.isBlank()) {
            result = bookRepository.findByTitleContainingIgnoreCase(title);
        } else {
            result = bookRepository.findAll();
        }
        return result;
    }


    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }


    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        Optional<Book> optBook = bookRepository.findById(book.getId());

        if(optBook.isEmpty()) {
            throw new IllegalArgumentException("Impossibile aggiornare il libro senza l'id");
        }

        return bookRepository.save(book);
    }

    public void deleteById(Integer id) {
        
        Book book = bookRepository.findById(id).get();
        for (Borrowing b : book.getBorrowings()) {
            borrowingRepository.deleteById(b.getId());
        }
        bookRepository.deleteById(id);
    }
}
