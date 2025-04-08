package it.lessons.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.library.model.Book;
import it.lessons.library.repository.BookRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "keyword", required = false) String title) {
        List<Book> result;
        if (title != null && !title.isBlank()) {
            result = bookRepository.findByTitleContainingIgnoreCase(title);
        } else {
            result = bookRepository.findAll();
        }
        model.addAttribute("list", result);
        return "books/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Book> optLibro = bookRepository.findById(id);
        if (optLibro.isPresent()) {
            model.addAttribute("book", bookRepository.findById(id).get());
            return "/books/show";
        }

        model.addAttribute("errorCause",
                "Non esiste un libro con id " + id);
        model.addAttribute("errorMessage",
                "Errore di ricerca del libro");

        return "/error_pages/generic_error";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "/books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/books/create";
        }

        //Logica di salvataggio
        bookRepository.save(formBook);

        redirectAttributes.addFlashAttribute("successMessage", "Book created!");
        return "redirect:/books";
    }
}
