package edu.upc.dsa;

import edu.upc.dsa.exceptions.BookNotFoundException;
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
    public void testAddUser() {
        User user = new User("Pau", "Martí", "12345678A", "01/01/1990", "Barcelona", "Carrer Major 1");
        bm.addUser(user);

        Assert.assertEquals(1, bm.getUsers().size());
        Assert.assertNotNull(bm.getUsers().get(user.getId()));
    }

    @Test
    public void testAddUserWithParams() {
        bm.addUser("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        Assert.assertEquals(1, bm.getUsers().size());
    }

    @Test
    public void testAddUserWithWrongParams() throws IllegalArgumentException {
        bm.addUser("Joan", "Serra", "87654321B", "1980", "Girona", "Carrer Nou 3");
    }

    @Test
    public void testAddBook() {
        Book book = new Book("978-84-376-0494-7", "El Quixot", "Planeta", "1-2-2005", 1, "Cervantes", "Novel·la");
        bm.addBook(book);

        Assert.assertEquals(book, bm.getPilaLlibresPrimera().pop());
    }

    @Test
    public void testCatalogarBookBuit() {
        Queue<Book> q = new QueueImpl<>(10);

        Book book = new Book("ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");
        book.setId("b1");
        q.push(book);

        bm.setPilaLlibresPrimera(q);

        bm.catalogarBook();

        Assert.assertEquals(1, bm.getBooks().size());
        Assert.assertEquals("b1", bm.getBooks().get(0).getId());
        Assert.assertEquals(0, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testCatalogarBook2Queues() {
        Queue<Book> q1 = new QueueImpl<>(10);
        Queue<Book> q2 = new QueueImpl<>(10);
        LinkedList<Queue<Book>> lq = new LinkedList<>();

        Book book1 = new Book("ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");
        Book book2 = new Book("ISBN2", "Títol", "Editorial", "3/8/2020", 1, "Autor", "Tema");

        book1.setId("b1");
        book2.setId("b2");
        q1.push(book1);
        q2.push(book2);
        lq.add(q2);

        bm.setPilaLlibresPrimera(q1);
        bm.setPilesLlibres(lq);

        bm.catalogarBook();
        bm.catalogarBook();

        Assert.assertEquals(2, bm.getBooks().size());
        Assert.assertEquals("b1", bm.getBooks().get(0).getId());
        Assert.assertEquals("b2", bm.getBooks().get(1).getId());
        Assert.assertEquals(0, bm.getPilaLlibresPrimera().size());
    }

    @Test
    public void testAddPrestec() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        bm.addBook("2", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);

        Assert.assertEquals(1, bm.getPrestecs().size());
    }

    @Test
    public void testAddPrestecUserIncorrecte() throws UserNotFoundException {
        bm.addUser("2", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        bm.addBook("2", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);
    }

    @Test
    public void testAddPrestecPrestecIncorrecte() throws BookNotFoundException {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        bm.addBook("1", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");

        Prestec p = new Prestec("1", "2", "01/11/2025", "15/11/2025");
        bm.addPrestec(p);
    }

    @Test
    public void testAddPrestecWithParams() {
        bm.addUser("1", "Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        bm.addBook("1", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");

        bm.addPrestec("1", "1", "01/11/2025", "10/11/2025");
    }

    @Test
    public void testClear() {
        bm.getBooks().add(new Book("111", "Test", "Planeta", "2020", 1, "Autor", "Tema"));
        bm.addUser(new User("Anna", "Riba", "9999", "01/01/2001", "Tarragona", "Carrer X"));

        bm.clear();

        Assert.assertNull(bm);
    }

    @Test
    public void testGetPrestecDeLector() {
        User u1 = new User("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
        u1.setId("1");
        bm.addUser(u1);
        bm.addBook("1", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");
        bm.addBook("2", "ISBN1", "Títol", "Editorial", "1-1-2020", 1, "Autor", "Tema");

        bm.addPrestec("1", "1", "01/11/2025", "10/11/2025");
        bm.addPrestec("1", "2", "11/11/2025", "10/1/2025");

        List<Prestec> prestecs = bm.getPrestecDeLector(u1);
        Assert.assertEquals("1", prestecs.get(0).getId());
        Assert.assertEquals("2", prestecs.get(1).getId());
    }
}
