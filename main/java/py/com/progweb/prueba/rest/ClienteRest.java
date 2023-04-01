package py.com.progweb.prueba.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.ejb.ClienteDAO;


@Path("clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteRest {
    @Inject
    ClienteDAO clienteDAO;

    @GET
    public Response index() {
        try {
            List<Cliente> clientes = clienteDAO.findAll();
            return Response.ok(clientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") int id) {
        try{
            Cliente cliente = clienteDAO.findById(id);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(cliente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    public Response store(Cliente cliente) {
        try{
            clienteDAO.save(cliente);
            return Response.status(Response.Status.CREATED).entity("{ \"message\": \"Created successfully!\" }").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Cliente cliente) {
        try {

            Cliente clienteExistente = clienteDAO.findById(id);

            if (clienteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            clienteDAO.update(clienteExistente, cliente);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try{
            Cliente cliente = clienteDAO.findById(id);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            clienteDAO.delete(cliente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()+e.getCause()).build();
        }
    }
}