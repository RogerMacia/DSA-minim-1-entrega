package edu.upc.dsa.services;

import edu.upc.dsa.data.*;
import edu.upc.dsa.exceptions.EmptyQueueException;
import edu.upc.dsa.models.*;
import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/bibliotecaManager")
@Path("/bibliotecaManager")
public class BibliotecaManagerService {
    private BibliotecaManagerImpl bm;

    public BibliotecaManagerService() {
        this.bm = BibliotecaManagerImpl.getInstance();
        if (bm.getBooks().isEmpty()) {
            bm.addBook("ISBN1", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN2", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN3", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN4", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN4", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN4", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN5", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN6", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN7", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN7", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN7", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN8", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN9", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN10", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN11", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN12", "Títol", "Editorial", "2020", "1", "Autor", "Tema");
            bm.addBook("ISBN12", "Títol", "Editorial", "2020", "1", "Autor", "Tema");

            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();
            bm.catalogarBook();

            User u1 = new User("Pau", "Martí", "12345678A", "01/01/1990", "Barcelona", "Carrer Major 1");
            u1.setId("1");
            User u2 = new User("Joan", "Serra", "87654321B", "02/02/1980", "Girona", "Carrer Nou 3");
            u2.setId("2");
            User u3 = new User("Anna", "Riba", "9999", "01/01/2001", "Tarragona", "Carrer X");
            u3.setId("3");

            bm.addUser(u1);
            bm.addUser(u2);
            bm.addUser(u3);

            Prestec p1 = new Prestec(u1.getId(), bm.getBooks().get(0).getId(), "01/11/2025", "15/11/2025");
            Prestec p2 = new Prestec(u1.getId(), bm.getBooks().get(1).getId(), "01/11/2025", "15/11/2025");
            Prestec p3 = new Prestec(u1.getId(), bm.getBooks().get(4).getId(), "01/11/2025", "15/11/2025");
            Prestec p4 = new Prestec(u2.getId(), bm.getBooks().get(4).getId(), "01/11/2025", "15/11/2025");
            Prestec p5 = new Prestec(u2.getId(), bm.getBooks().get(5).getId(), "01/11/2025", "15/11/2025");

            bm.addPrestec(p1);
            bm.addPrestec(p2);
            bm.addPrestec(p3);
            bm.addPrestec(p4);
            bm.addPrestec(p5);
        }
    }

    @POST
    @ApiOperation(value = "Add a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @Path("/addUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (user.getName() == null || user.getSurname() == null || user.getDni() == null || user.getBirthDate() == null || user.getBirthPlace() == null || user.getAddress() == null)
            return Response.status(400).entity("Invalid parameters").build();

        user.setId(RandomUtils.getId());
        bm.addUser(user);

        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "Add a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @Path("/addBook")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        if (book.getIsbn() == null || book.getTitle() == null || book.getPublisher() == null || book.getYearPublished() == null || book.getAuthor() == null || book.getTheme() == null)
            return Response.status(400).entity("Invalid parameters").build();

        book.setId(RandomUtils.getId());
        bm.addBook(book);

        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "Catalogar un llibre")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @Path("/catalogar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response catalogarBook() {
        try {
            bm.catalogarBook();
            return Response.status(200).build();
        }
        catch (EmptyQueueException e) {
            return Response.status(404).entity("No s'ha trobat llibres per catalogar").build();
        }
    }

    @GET
    @ApiOperation(value = "Consultar tots els préstecs que ha realitzat un usuari introduït per id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Prestec.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @Path("/PrestecDeLector/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrestecDeLector(@PathParam("id") String id) {
        if (bm.getUsers().get(id) == null) return Response.status(404).entity("User with id = " + id + " not found").build();
        List<Prestec> prestecs = this.bm.getPrestecDeLector(bm.getUsers().get(id));

        if (prestecs.isEmpty()) return Response.status(404).entity("Prestecs de l'usuari amb id = " + id + " not found").build();
        GenericEntity<List<Prestec>> entity = new GenericEntity<List<Prestec>>(prestecs) {};
        return Response.status(201).entity(entity).build();
    }
}
