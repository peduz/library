package it.lessons.library.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Integer id) {
        super("Cannot get book with id " + id);
    }
}
