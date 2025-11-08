package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.InvalidDateFormatException;
import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.ApiModelProperty;

public class Book {
    @ApiModelProperty(hidden = true)
    private String id;
    private String isbn;
    private String title;
    private String publisher;
    private String yearPublished;
    private String numEdition;
    private String author;
    private String theme;

    @ApiModelProperty(hidden = true)
    private int numExemplars;

    public Book() {}
    public Book(String isbn, String title, String publisher, String yearPublished, String numEdition, String author, String theme) {
        this.id = RandomUtils.getId();
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.setYearPublished(yearPublished);
        this.numEdition = numEdition;
        this.author = author;
        this.theme = theme;
        this.numExemplars = 1;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYearPublished() {
        return yearPublished;
    }
    public void setYearPublished(String yearPublished) {
        try {
            Integer.parseInt(yearPublished);
        } catch (NumberFormatException e) {
            throw new InvalidDateFormatException();
        }
        this.yearPublished = yearPublished;
    }

    public String getNumEdition() {
        return numEdition;
    }
    public void setNumEdition(String numEdition) {
        this.numEdition = numEdition;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getNumExemplars() {
        return numExemplars;
    }
    public void setNumExemplars(int numExemplars) {
        this.numExemplars = numExemplars;
    }

    public void addExemplar() {
        this.numExemplars++;
    }
}