package edu.upc.dsa.data;

import java.util.*;

import org.apache.log4j.Logger;
import edu.upc.dsa.models.*;
import edu.upc.dsa.exceptions.*;

public class BibliotecaManagerImpl implements BibliotecaManager {
    private static BibliotecaManagerImpl bm;

    private BibliotecaManagerImpl() {}

    private ArrayList<Book> books;
    private HashMap<String, User> users;
    private List<Prestec> prestecs;
    private Queue<Book> pilaLlibresPrimera;
    private LinkedList<Queue<Book>> pilesLlibres;

    private static final Logger logger = Logger.getLogger(BibliotecaManagerImpl.class);

    public static BibliotecaManagerImpl getInstance() {
        if (bm == null) {
            bm = new BibliotecaManagerImpl();

            bm.books = new ArrayList<>();
            bm.users = new HashMap<>();
            bm.prestecs = new LinkedList<>();
            bm.pilaLlibresPrimera = new QueueImpl<>(10);
            bm.pilesLlibres = new LinkedList<>();
            bm.pilesLlibres.add(new QueueImpl<>(10));
        }
        return bm;
    }

    public ArrayList<Book> getBooks() {
        logger.info("Getting books");
        return books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        logger.info("Books set");
    }

    public HashMap<String, User> getUsers() {
        logger.info("Getting users");
        return users;
    }
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
        logger.info("Users set");
    }

    public List<Prestec> getPrestecs() {
        logger.info("Getting prestecs");
        return prestecs;
    }
    public void setPrestecs(List<Prestec> prestecs) {
        this.prestecs = prestecs;
        logger.info("Prestecs set");
    }

    public Queue<Book> getPilaLlibresPrimera() {
        logger.info("Getting pila llibres primera");
        return pilaLlibresPrimera;
    }
    public void setPilaLlibresPrimera(Queue<Book> pilaLlibresPrimera) {
        this.pilaLlibresPrimera = pilaLlibresPrimera;
        logger.info("Pila llibres primera");
    }

    public LinkedList<Queue<Book>> getPilesLlibres() {
        logger.info("Getting piles llibres");
        return pilesLlibres;
    }
    public void setPilesLlibres(LinkedList<Queue<Book>> pilesLlibres) {
        this.pilesLlibres = pilesLlibres;
        logger.info("Piles llibres");
    }

    private Book getBookByID(String id) {
        logger.info("Searching for the book...");

        for (Book b : books) {
            if (id.equals(b.getId())) {
                logger.info("Book found!");
                return b;
            }
        }
        logger.info("No book found");
        return null;
    }

    @Override
    public void addUser(User user) {
        this.users.put(user.getId(), user);
        logger.info("User added");
    }

    @Override
    public void addUser(String name, String surname, String DNI, String birthDate, String birthPlace, String address) {
        User u = new User(name, surname, DNI, birthDate, birthPlace, address);
        users.put(u.getId(), u);
        logger.info("User added");
    }

    @Override
    public void addUser(String id, String name, String surname, String DNI, String birthDate, String birthPlace, String address) {
        User u = new User(name, surname, DNI, birthDate, birthPlace, address);
        u.setId(id);
        users.put(u.getId(), u);
        logger.info("User added");
    }

    @Override
    public void addBook(Book book) {
        if (pilaLlibresPrimera.size() == 0) {
            pilaLlibresPrimera.push(book);
        }

        Queue<Book> lastQueue = pilesLlibres.get(pilesLlibres.size() - 1);
        if (lastQueue.size() < 10) {
            lastQueue.push(book);
        }
        else {
            pilesLlibres.add(new QueueImpl<Book>(10));
            logger.info("New queue created");
            pilesLlibres.get(pilesLlibres.size() - 1).push(book);
        }
        logger.info("Book added to the queue");
    }

    @Override
    public void addBook(String ISBN, String title, String publisher, String yearPublished, int numEdition, String author, String theme) {
        Book book = new Book(ISBN, title, publisher, yearPublished, numEdition, author, theme);
        addBook(book);
    }

    @Override
    public void addBook(String id, String ISBN, String title, String publisher, String yearPublished, int numEdition, String author, String theme) {
        Book book = new Book(ISBN, title, publisher, yearPublished, numEdition, author, theme);
        book.setId(id);
        addBook(book);
    }

    @Override
    public void catalogarBook() {
        Book book = pilaLlibresPrimera.pop();

        if (pilaLlibresPrimera.size() == 0) {
            if (!pilesLlibres.isEmpty() && pilesLlibres.get(0).size() > 0)
                pilaLlibresPrimera = pilesLlibres.get(0);
            else {
                logger.error("Piles buides", new EmptyQueueException());
            }

            pilesLlibres.remove(0);
        }

        Book b = getBookByID(book.getId());

        if (b == null) {
            this.books.add(book);
        }
        else {
            b.addExemplar();
        }

        logger.info("Llibre catalogat");
    }

    @Override
    public void addPrestec(Prestec prestec) {
        logger.info("Prestec added");
        prestecs.add(prestec);
    }

    @Override
    public void addPrestec(String idUser, String idBook, String dataPrestec, String dataDevolucio) {
        Book book = getBookByID(idBook);
        if (book == null)
            logger.error("Book with id: " + idBook + " not found", new BookNotFoundException());

        if (users.get(idUser) == null)
            logger.error("User with id: " + idUser + " not found", new UserNotFoundException());

        if (book.getNumExemplars() == 0)
            logger.error("No book with id: " + idBook + " available", new SenseExemplarsException());

        Prestec prestec = new Prestec(idUser, idBook, dataPrestec, dataDevolucio);
        prestecs.add(prestec);
        logger.info("Prestec added");
    }

    @Override
    public void addPrestec(String id, String idUser, String idBook, String dataPrestec, String dataDevolucio) {
        Prestec prestec = new Prestec(idUser, idBook, dataPrestec, dataDevolucio);
        prestec.setId(id);
        prestecs.add(prestec);
        logger.info("Prestec added");
    }

    @Override
    public List<Prestec> getPrestecDeLector(User user) {
        logger.info("Getting prestec");
        return user.getPrestects();
    }

    @Override
    public void clear() {
        books.clear();
        prestecs.clear();
        pilaLlibresPrimera = null;
        pilesLlibres.clear();
        bm = null;
    }

}