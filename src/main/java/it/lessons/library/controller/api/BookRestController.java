package it.lessons.library.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lessons.library.model.Book;
import it.lessons.library.service.BookService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/books")
public class BookRestController{

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> index(@RequestParam(name="keyword", required=false) String keyword) {
        return bookService.findBookList(keyword);
    }
    
    @PostMapping
    public Book create(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public Book putMethodName(@PathVariable Integer id, @RequestBody Book book) {
        return bookService.update(book);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        bookService.deleteById(id);
    }
    
    
}
