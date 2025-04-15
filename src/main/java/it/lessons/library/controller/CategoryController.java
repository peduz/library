package it.lessons.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.library.model.Category;
import it.lessons.library.service.CategoryService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("list", service.findAllCategories());
        model.addAttribute("categoryObj", new Category());
        return "/categories/index";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("categoryObj") Category category,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            service.create(category);
        }

        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        service.deleteById(id);
        return "redirect:/categories";
    }

}
