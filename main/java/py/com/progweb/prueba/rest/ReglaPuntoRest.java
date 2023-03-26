package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ReglaPuntoDAO;
import py.com.progweb.prueba.model.ReglaPuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/regla_puntos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReglaPuntoRest {

    @Inject
    ReglaPuntoDAO reglaPuntosDAO;


    @GET
    public Response index() {
        List<ReglaPuntos> puntos = reglaPuntosDAO.findAll();
        return Response.ok(puntos).build();
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") int id) {
        ReglaPuntos reglaPuntos = reglaPuntosDAO.findById(id);
        return Response.ok(reglaPuntos).build();
    }

    @POST
    public Response store(ReglaPuntos reglaPuntos) {
        reglaPuntosDAO.save(reglaPuntos);
        return Response.ok(reglaPuntos).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, ReglaPuntos reglaPuntos) {
        try {

            ReglaPuntos administracionExistente = reglaPuntosDAO.findById(id);

            if (administracionExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            reglaPuntosDAO.update(administracionExistente, reglaPuntos);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        ReglaPuntos reglaPuntos = reglaPuntosDAO.findById(id);
        reglaPuntosDAO.delete(reglaPuntos);
        return Response.noContent().build();
    }
}
