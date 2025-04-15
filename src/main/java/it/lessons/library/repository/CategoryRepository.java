package it.lessons.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.library.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
