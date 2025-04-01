package it.lessons.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
