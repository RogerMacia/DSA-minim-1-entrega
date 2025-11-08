package edu.upc.dsa;

import edu.upc.dsa.exceptions.BookNotFoundException;
import edu.upc.dsa.exceptions.InvalidDateFormatException;
import edu.upc.dsa.exceptions.UserNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import edu.upc.dsa.models.*;
import edu.upc.dsa.data.*;
import org.junit.rules.ExpectedException;

public class BibliotecaManagerImplTest {

    private BibliotecaManagerImpl bm;

    @Before
    public void setUp() {
        bm = BibliotecaManagerImpl.getInstance();
    }

    @After
    public void tearDown() {
        bm.clear();
    }

    @Test
    public void testSingletonInstance() {
        BibliotecaManagerImpl bm2 = BibliotecaManagerImpl.getInstance();
        Assert.assertEquals(bm, bm2);
    }

    @Test
    public void testAddUser1() {
        User user = new User("Pau", "Martí", "12345678A", "01/01/1990", "Barcelona", "Carrer Major 1");
        bm.addUser(user);

        Assert.assertEquals(1, bm.getUsers().size());
        Assert.assertNotNull(bm.getUsers().get(user.getId()));
    }

    @Test
    public void testAddUser2() {
        bm.addUser("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Assert.assertEquals(1, bm.getUsers().size());
    }

    @Test
    public void testAddUser3() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Assert.assertEquals(1, bm.getUsers().size());
    }

    @Test(expected = InvalidDateFormatException.class)
    public void testAddUserWithWrongDateFormat() {
        bm.addUser("Joan", "Serra", "87654321B", "1980", "Girona", "Carrer Nou 3");
    }

    @Test
    public void testAddBook1() {
        Book book = new Book("978-84-376-0494-7", "El Quixot", "Planeta", "2005", "1", "Cervantes", "Novel·la");
        bm.addBook(book);

        Assert.assertEquals(book, bm.getPilaLlibresPrimera().pop());
    }

    @Test
    public void testAddBook2() {
        bm.addBook("978-84-376-0494-7", "El Quixot", "Planeta", "2005", "1", "Cervantes", "Novel·la");


        Assert.assertEquals(1, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testAddBook3() {
        bm.addBook("1", "978-84-376-0494-7", "El Quixot", "Planeta", "2005", "1", "Cervantes", "Novel·la");

        Assert.assertEquals(1, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testAddBook4() {
        for (int i = 0; i < 50; i++) {
            bm.addBook(String.valueOf(i), "ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        }

        Assert.assertEquals(5, bm.getPilesLlibres().size());
    }

    @Test
    public void testCatalogarBookBuit() {
        Queue<Book> q = new QueueImpl<>(10);

        bm.addBook("1", "978-84-376-0494-7", "El Quixot", "Planeta", "2005", "1", "Cervantes", "Novel·la");

        bm.catalogarBook();

        Assert.assertEquals(1, bm.getBooks().size());
        Assert.assertEquals("1", bm.getBooks().get(0).getId());
        Assert.assertEquals(0, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testCatalogarBook2Queues() {
        bm.addBook("1", "ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        bm.addBook("2", "ISBN2", "Títol", "Editorial", "2020", "1", "Autor", "Tema");

        bm.catalogarBook();
        bm.catalogarBook();

        Assert.assertEquals(2, bm.getBooks().size());
        Assert.assertEquals("1", bm.getBooks().get(0).getId());
        Assert.assertEquals("2", bm.getBooks().get(1).getId());
        Assert.assertEquals(0, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testCatalogarBookMoltesCues() {
        for (int i = 0; i < 50; i++) {
            bm.addBook(String.valueOf(i), "ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        }

        for (int i = 0; i < 25; i++) {
            bm.catalogarBook();
        }

        Assert.assertEquals(2, bm.getPilesLlibres().size());
    }

    @Test
    public void testAddPrestec1() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("2");
        bm.getBooks().add(book);

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);

        Assert.assertEquals(1, bm.getPrestecs().size());
    }

    @Test
    public void testAddPrestec2() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("2");
        bm.getBooks().add(book);

        bm.addPrestec("1", "2", "01/11/2025", "15/11/2025");

        Assert.assertEquals(1, bm.getPrestecs().size());
    }

    @Test
    public void testAddPrestec3() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("2");
        bm.getBooks().add(book);

        bm.addPrestec("1", "1", "2", "01/11/2025", "15/11/2025");

        Assert.assertEquals(1, bm.getPrestecs().size());
    }

    @Test(expected = UserNotFoundException.class)
    public void testAddPrestecUserIncorrecte() {
        bm.addUser("2", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("2");
        bm.getBooks().add(book);

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);
    }

    @Test(expected = BookNotFoundException.class)
    public void testAddPrestecBookIncorrecte() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("1");
        bm.getBooks().add(book);

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPrestecDataIncorrecte() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Book book = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book.setId("1");
        bm.getBooks().add(book);

        new Prestec("1", "2", "01/11/2025", "15/1/2025");
    }

    @Test
    public void testGetPrestecDeLector() {
        User u1 = new User("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        u1.setId("1");
        bm.addUser(u1);
        Book book1 = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book1.setId("1");
        Book book2 = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book2.setId("2");

        bm.getBooks().add(book1);
        bm.getBooks().add(book2);

        bm.addPrestec("1", "1", "01/11/2025", "10/11/2025");
        bm.addPrestec("1", "2", "11/11/2025", "10/12/2025");

        List<Prestec> prestecs = bm.getPrestecDeLector(u1);
        Assert.assertEquals("1", prestecs.get(0).getIdBook());
        Assert.assertEquals("2", prestecs.get(1).getIdBook());
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetPrestecDeLectorNoDefinit() {
        User u1 = new User("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        u1.setId("1");
        bm.addUser(u1);
        Book book1 = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book1.setId("1");
        Book book2 = new Book("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
        book2.setId("2");

        bm.getBooks().add(book1);
        bm.getBooks().add(book2);

        bm.addPrestec("5", "1", "01/11/2025", "10/11/2025");
        bm.addPrestec("5", "2", "11/11/2025", "10/12/2025");

        bm.getPrestecDeLector(u1);
    }

    @Test
    public void testClear() {
        bm.getBooks().add(new Book("111", "Test", "Planeta", "2020", "1", "Autor", "Tema"));
        bm.addUser(new User("Anna", "Riba", "9999", "01/01/2001", "Tarragona", "Carrer X"));

        bm.clear();

        Assert.assertNull(bm.getBooks());
        Assert.assertNull(bm.getUsers());
        Assert.assertNull(bm.getPrestecs());
        Assert.assertNull(bm.getPilaLlibresPrimera());
        Assert.assertNull(bm.getPilesLlibres());
    }
}
