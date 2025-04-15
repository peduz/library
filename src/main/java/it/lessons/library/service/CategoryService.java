package it.lessons.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lessons.library.model.Book;
import it.lessons.library.model.Category;
import it.lessons.library.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category create(Category cat) {
        return categoryRepository.save(cat);
    }

    public void deleteById(Long id) {
        Category cat = categoryRepository.findById(id).get();

        for (Book b : cat.getBooks()) {
            b.getCategories().remove(cat);
        }

        categoryRepository.deleteById(id);
    }
}
