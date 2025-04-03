package it.lessons.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    //Select b from Book b where UPPER(b.title) = UPPER(?1)
    public List<Book> findByTitleContainingIgnoreCase(String title);

}
