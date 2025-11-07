/*package edu.upc.dsa.services;

import edu.upc.dsa.data.*;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(value = "/productMng")
@Path("/productMng")
public class ProductsService {
    private BibliotecaManagerImpl pm;

    public ProductsService() {
        this.pm = BibliotecaManagerImpl.getInstance();
        if (pm.getProducts().size() == 0) {
            Product p1 = new Product("1", "Hamburguesa", 7.5);
            Product p2 = new Product("2", "Patates", 3);
            Product p3 = new Product("3", "Pasta", 5);
            Product p4 = new Product("4", "Entrepà", 4.5);
            Product p5 = new Product("5", "Aigua", 0.75);

            User u1 = new User("1", "Josep");
            User u2 = new User("2", "Toni");
            User u3 = new User("3", "Albert");
            User u4 = new User("4", "Antonia");
            User u5 = new User("5", "Carla");

            List<Product> products = new ArrayList<>();
            HashMap<String, User> users = new HashMap<>();

            List<String> productUser11 =  new ArrayList<>();
            List<String> productUser12 =  new ArrayList<>();
            List<String> productUser2 =  new ArrayList<>();
            List<String> productUser3 =  new ArrayList<>();
            List<String> productUser4 =  new ArrayList<>();

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);

            users.put(u1.getId(), u1);
            users.put(u2.getId(), u2);
            users.put(u3.getId(), u3);
            users.put(u4.getId(), u4);
            users.put(u5.getId(), u5);

            productUser11.add("5");
            productUser11.add("5");
            productUser11.add("2");
            productUser11.add("2");
            productUser11.add("5");
            productUser12.add("1");
            productUser12.add("1");
            productUser12.add("5");
            productUser2.add("5");
            productUser2.add("2");
            productUser2.add("4");


            productUser3.add("5");
            productUser3.add("3");
            productUser3.add("1");
            productUser3.add("2");
            productUser3.add("2");
            productUser4.add("4");

            for (Product p : products) {
                pm.addProduct(p);
            }

            for (User u : users.values()) {
                pm.addUser(u);
            }

            pm.makeOrder(productUser11, "1");
            pm.makeOrder(productUser12, "1");
            pm.makeOrder(productUser2, "2");
            pm.makeOrder(productUser3, "3");
            pm.makeOrder(productUser4, "4");
            pm.serveOrder();
            pm.serveOrder();
            pm.serveOrder();
        }
    }

    @GET
    @ApiOperation(value = "get all products sorted by ascending price")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class, responseContainer = "List")
    })
    @Path("/productsByPrice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {
        List<Product> products = this.pm.getProductsByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "make an order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @Path("/makeOrder/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeOrder(List<String> idProducts, @PathParam("id") String idUser) {
        if (idProducts.isEmpty()) return Response.status(400).entity("Products' IDs invalid").build();
        for (String id : idProducts) {
            if (this.pm.getProduct(id) == null) {
                return Response.status(404).entity("Product not found").build();
            }
        }
        if (pm.getUser(idUser) == null) return Response.status(404).entity("User not found").build();
        pm.makeOrder(idProducts, idUser);
        return Response.status(201).entity("Created").build();
    }

    @POST
    @ApiOperation(value = "serve an order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
    })
    @Path("/serveOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response serveOrder() {
        if (pm.serveOrder() == null) return Response.status(404).entity("Orders to serve not found").build();
        return Response.status(201).entity("Successful").build();
    }

    @GET
    @ApiOperation(value = "get all done orders form a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Order.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @Path("/doneOrders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoneOrdersFromUser(@PathParam("id") String id) {
        if (pm.getUser(id) == null) return Response.status(404).entity("User with id = " + id + " not found").build();
        List<Order> orders = this.pm.getDoneOrdersFromUser(id);

        if (orders == null) return Response.status(404).entity("Done orders from user with id = " + id + " not found").build();
        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all products sorted by number of sales in descending order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer = "List")
    })
    @Path("/productsBySales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {
        List<Product> products = this.pm.getProductsBySales();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();
    }
}
*/