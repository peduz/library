package it.lessons.library.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Title is mandatory")
    private String title;

    @NotBlank(message="Isbn code is mandatory")
    @Size(min=13, max=13, message="Isbn code must be 13 chars")
    @Column(name = "isbn_code", length=13, unique=true, nullable=false)
    private String isbn;

    @Column(name = "num_pages")
    private Integer numPages;

    @Column( length=1000, nullable=false)
    private String synopsis;
    
    @NotBlank(message="Author is mandatory")
    private String author;

    @NotBlank(message="Publisher is mandatory")
    private String publisher;

    private String category;

    @NotNull
    private Integer year;

    @Min(value=0)
    private Integer numCopy;

    @OneToMany(mappedBy="book")
    private List<Borrowing> borrowings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNumCopy() {
        return numCopy;
    }

    public void setNumCopy(Integer numCopy) {
        this.numCopy = numCopy;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    
}
