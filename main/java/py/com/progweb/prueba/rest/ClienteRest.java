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
    public Response listar() {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            return Response.ok(clientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") int id) {
        try{
            Cliente cliente = clienteDAO.obtener(id);
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
    public Response agregar(Cliente cliente) {
        try{
            clienteDAO.agregar(cliente);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") int id, Cliente cliente) {
        try{
            Cliente clienteExistente = clienteDAO.obtener(id);
            if (clienteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            cliente.setId(id);
            clienteDAO.modificar(cliente);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try{
            Cliente cliente = clienteDAO.obtener(id);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            clienteDAO.eliminar(cliente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()+e.getCause()).build();
        }
    }
}