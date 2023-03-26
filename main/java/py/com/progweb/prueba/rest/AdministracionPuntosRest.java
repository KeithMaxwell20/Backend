package py.com.progweb.prueba.rest;
import py.com.progweb.prueba.model.AdministracionPuntos;
import py.com.progweb.prueba.ejb.AdministracionPuntosDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/administracion_puntos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministracionPuntosRest {

    @Inject
    AdministracionPuntosDAO administracionPuntosDAO;


    @GET
    public Response index() {
        List<AdministracionPuntos> puntos = administracionPuntosDAO.findAll();
        return Response.ok(puntos).build();
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") Long id) {
        AdministracionPuntos administracionPuntos = administracionPuntosDAO.findById(id);
        return Response.ok(administracionPuntos).build();
    }

    @POST
    public Response store(AdministracionPuntos administracionPuntos) {
        administracionPuntosDAO.save(administracionPuntos);
        return Response.ok(administracionPuntos).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AdministracionPuntos administracionPuntos) {
        try {

            AdministracionPuntos administracionExistente = administracionPuntosDAO.findById(id);

            if (administracionExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            administracionPuntosDAO.update(administracionExistente, administracionPuntos);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        AdministracionPuntos administracionPuntos = administracionPuntosDAO.findById(id);
        administracionPuntosDAO.delete(administracionPuntos);
        return Response.noContent().build();
    }
}