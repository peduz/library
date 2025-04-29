package it.lessons.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.library.model.Book;
import it.lessons.library.model.Borrowing;
import it.lessons.library.service.BookService;
import it.lessons.library.service.CategoryService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private CategoryService catService;


    @GetMapping
    public String index(Authentication authentication, Model model, @RequestParam(name = "keyword", required = false) String title) {
        model.addAttribute("list", service.findBookList(title));
        model.addAttribute("username", authentication.getName());
        return "books/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Book> optLibro = service.findBookById(id);
        if(optLibro == null) {
            model.addAttribute("errorMessage", "Errore: id non valido");
            return "error";
        }
        if (optLibro.isPresent()) {
            model.addAttribute("book", optLibro.get());
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
        model.addAttribute("categoryList", catService.findAllCategories());
        return "/books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", catService.findAllCategories());
            return "/books/create";
        }

        //Logica di salvataggio
        service.create(formBook);

        redirectAttributes.addFlashAttribute("successMessage", "Book created!");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("book", service.findBookById(id).get());
        model.addAttribute("categoryList", catService.findAllCategories());

        return "/books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @Valid @ModelAttribute("book") Book formBook,
            BindingResult bindingResult,
            Model model) {
        //ISBN: 9780553808049
        Optional<Book> optBook = service.findBookById(formBook.getId());

        if (optBook.isPresent()) {
            Book dbBook = optBook.get();

            if (!dbBook.getIsbn().equals(formBook.getIsbn())) {
                bindingResult.addError(new ObjectError("isbn",
                        "Can't change isbn value. Correct isb is: " + dbBook.getIsbn()));
            }
        }
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        service.create(formBook);

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.deleteById(id);

        return "redirect:/books";
    }

    @GetMapping("/{id}/borrow")
    public String borrow(@PathVariable Integer id, Model model) {
        Borrowing borrow = new Borrowing();
        borrow.setBook(service.findBookById(id).get());

        model.addAttribute("borrow", borrow);
        model.addAttribute("editMode", false);
        return "/borrowings/edit";
    }
    
}
