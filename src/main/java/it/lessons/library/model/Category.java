package it.lessons.library.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Category value cannot be blank")
    private String category;

   @ManyToMany(mappedBy="categories")
   private List<Book> books;


   public Long getId() {
    return id;
   }


   public void setId(Long id) {
    this.id = id;
   }


   public String getCategory() {
    return category;
   }


   public void setCategory(String category) {
    this.category = category;
   }


   public List<Book> getBooks() {
    return books;
   }


   public void setBooks(List<Book> books) {
    this.books = books;
   }

   
}
