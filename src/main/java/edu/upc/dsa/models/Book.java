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
    private int numEdition;
    private String author;
    private String theme;

    @ApiModelProperty(hidden = true)
    private int numExemplars;

    public Book() {}
    public Book(String isbn, String title, String publisher, String yearPublished, int numEdition, String author, String theme) {
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
        char separador;

        try {
            separador = yearPublished.charAt(yearPublished.length() - 5);
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidDateFormatException();
        }

        String[] date = new String[0];

        if (separador == '-') {
            date = yearPublished.split("-");
        } else if (separador == '/') {
            date = yearPublished.split("/");
        }

        if (date.length != 3 || date[2].length() != 4 || date[0].length() > 2 || date[1].length() > 2)
            throw new InvalidDateFormatException();

        try {
            int dia = Integer.parseInt(date[0]);
            int mes = Integer.parseInt(date[1]);
            int any = Integer.parseInt(date[2]);

            if (mes > 12) throw new InvalidDateFormatException();

            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:   //Mesos amb 31 dies
                    if (dia > 31) throw new InvalidDateFormatException();
                    break;

                case 4:
                case 6:
                case 9:
                case 11:    //Mesos amb 30 dies
                    if (dia > 30) throw new InvalidDateFormatException();
                    break;

                case 2: //Febrer
                    if (dia > 29 || dia > 28 && any % 4 != 0)
                        throw new InvalidDateFormatException();
                    break;
            }
        } catch (NumberFormatException e) {
            throw new InvalidDateFormatException();
        }

        this.yearPublished = yearPublished;
    }

    public int getNumEdition() {
        return numEdition;
    }
    public void setNumEdition(int numEdition) {
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