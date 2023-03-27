package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.UsoPuntosDetalleDAO;
import py.com.progweb.prueba.model.UsoPuntosDetalle;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("uso_puntos_detalle")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsoPuntoDetalleRest {
    @Inject
    UsoPuntosDetalleDAO usoPuntosDetalle;

    @GET
    public Response index() {
        try {
            List<UsoPuntosDetalle> usoPuntoDetalle = usoPuntosDetalle.findAll();
            return Response.ok(usoPuntoDetalle).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") int id) {
        try{
            UsoPuntosDetalle usoPuntoDetalle = usoPuntosDetalle.findById(id);
            if (usoPuntoDetalle == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(usoPuntoDetalle).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    public Response store(UsoPuntosDetalle usoPuntoDetalle) {
        try{
            usoPuntosDetalle.save(usoPuntoDetalle);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, UsoPuntosDetalle usoPuntoDetalle) {
        try {

            UsoPuntosDetalle bolsaPuntosExistente = usoPuntosDetalle.findById(id);

            if (bolsaPuntosExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            usoPuntosDetalle.update(bolsaPuntosExistente, usoPuntoDetalle);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try{
            UsoPuntosDetalle usoPuntoDetalle = usoPuntosDetalle.findById(id);
            if (usoPuntoDetalle == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            usoPuntosDetalle.delete(usoPuntoDetalle);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()+e.getCause()).build();
        }
    }
}
