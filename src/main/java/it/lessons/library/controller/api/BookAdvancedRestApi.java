package it.lessons.library.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lessons.library.exceptions.BookNotFoundException;
import it.lessons.library.model.Book;
import it.lessons.library.service.BookService;




@RestController
@RequestMapping("/api/books/v2")
public class BookAdvancedRestApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Integer id) {
        
        Optional<Book> optBook = bookService.findBookById(id);

        if(optBook.isEmpty()) {
            throw new BookNotFoundException(id);
        }

        return new ResponseEntity<>(optBook.get(), HttpStatus.OK);

    }
    

    @GetMapping
    public ResponseEntity<List<Book>> index(@RequestParam(name="keyword", required=false) String param) {


        List<Book> libri = bookService.findBookList(param);
        
        return new ResponseEntity<>(libri, HttpStatus.OK); 
    }
    
    @PostMapping()
    public ResponseEntity<Book> create(@RequestBody Book entity) {
        Book libro = bookService.create(entity);
        return new ResponseEntity<>(libro, HttpStatus.CREATED);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book entity) {
        try {
            Book bookUpdated = bookService.update(entity);
            return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
