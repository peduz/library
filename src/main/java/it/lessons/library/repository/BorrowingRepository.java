package it.lessons.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.library.model.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long>{

}
