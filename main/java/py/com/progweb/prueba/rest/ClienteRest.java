package py.com.progweb.prueba.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.ejb.ClienteDAO;


@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteRest {
    @Inject
    private ClienteDAO clienteDAO;

    @GET
    public Response listar() {
        List<Cliente> clientes = clienteDAO.listar();
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Cliente cliente = clienteDAO.obtener(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cliente).build();
    }

    @POST
    public Response agregar(Cliente cliente) {
        clienteDAO.agregar(cliente);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, Cliente cliente) {
        Cliente clienteExistente = clienteDAO.obtener(id);
        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        cliente.setId(id);
        clienteDAO.modificar(cliente);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        Cliente cliente = clienteDAO.obtener(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        clienteDAO.eliminar(cliente);
        return Response.noContent().build();
    }
}