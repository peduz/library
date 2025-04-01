package it.lessons.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.library.model.Book;
import it.lessons.library.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;



   @GetMapping
   public String index(Model model) {
      List<Book> result = bookRepository.findAll();
      model.addAttribute("list", result);
      return "books/index";
   }	

    
}