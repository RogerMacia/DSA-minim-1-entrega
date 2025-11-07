package edu.upc.dsa.data;

import java.util.ArrayList;
import java.util.List;
import edu.upc.dsa.models.*;

public interface BibliotecaManager {
    public void addUser(User user);
    public void addUser(String name, String surname, String DNI, String birthDate, String birthPlace, String address);
    public void addUser(String id, String name, String surname, String DNI, String birthDate, String birthPlace, String address);

    public void addBook(Book book);
    public void addBook(String ISBN, String title, String publisher, String yearPublished, int numEdition, String author, String theme);
    public void addBook(String id, String ISBN, String title, String publisher, String yearPublished, int numEdition, String author, String theme);

    public void catalogarBook();

    public void addPrestec(Prestec prestec);
    public void addPrestec(String idUser, String idBook, String dataPrestec, String dataDevolucio);
    public void addPrestec(String id, String idUser, String idBook, String dataPrestec, String dataDevolucio);

    public List<Prestec> getPrestecDeLector(User user);

    public void clear();
}