package it.lessons.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.library.model.Borrowing;
import it.lessons.library.repository.BorrowingRepository;
import jakarta.validation.Valid;




@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingRepository repository;

    @PostMapping("/create")
    public String store(
        @Valid @ModelAttribute("borrowing") Borrowing formBorrow,
        BindingResult bindingResult,
        Model model) {
    
            if(formBorrow.getBook().getNumCopy() <= 0) {
                bindingResult.addError(new ObjectError("note", 
                "Cannot borrow thie book because there aren't enough copy"));
            }

            if(bindingResult.hasErrors()) {
                model.addAttribute("editMode", false);
                model.addAttribute("borrow", formBorrow);
                return "/borrowings/edit";
            }

            int numCopy = formBorrow.getBook().getNumCopy();

            formBorrow.getBook().setNumCopy(--numCopy);

            repository.save(formBorrow);

        return "redirect:/books/show/" + formBorrow.getBook().getId();
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Borrowing borrowing = repository.findById(id).get();
        model.addAttribute("borrow", borrowing);
        model.addAttribute("editMode", true);
        return "/borrowings/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@Valid @ModelAttribute("borrow") Borrowing borrow,
    BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("editMode", true);
            model.addAttribute("borrow", borrow);
            return "/borrowings/edit";
        }

        int numCopy = borrow.getBook().getNumCopy();

        if(borrow.getReturnDate() != null) {
            borrow.getBook().setNumCopy(++numCopy);
        }

        repository.save(borrow);
        
        return "redirect:/books/show/" + borrow.getBook().getId();
    }
    
}
