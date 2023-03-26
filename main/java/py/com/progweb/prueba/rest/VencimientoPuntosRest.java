package py.com.progweb.prueba.rest;
import py.com.progweb.prueba.ejb.VencimientoPuntosDAO;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.AdministracionPuntos;
import py.com.progweb.prueba.model.VencimientoPuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/vencimiento_puntos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VencimientoPuntosRest{

    @Inject
    VencimientoPuntosDAO vencimientoPuntosDAO;


    @GET
    public Response index() {
        List<VencimientoPuntos> puntos = vencimientoPuntosDAO.findAll();
        return Response.ok(puntos).build();
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") Long id) {
        VencimientoPuntos vencimientoPuntos = vencimientoPuntosDAO.findById(id);
        return Response.ok(vencimientoPuntos).build();
    }

    @POST
    public Response store(VencimientoPuntos vencimientoPuntos) {
        vencimientoPuntosDAO.save(vencimientoPuntos);
        return Response.ok(vencimientoPuntos).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, VencimientoPuntos vencimientoPunto) {
        try {

            VencimientoPuntos vencimientoPuntoExistente = vencimientoPuntosDAO.findById(id);

            if (vencimientoPuntoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            vencimientoPuntosDAO.update(vencimientoPuntoExistente, vencimientoPunto);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        VencimientoPuntos vencimientoPunto = vencimientoPuntosDAO.findById(id);
        vencimientoPuntosDAO.delete(vencimientoPunto);
        return Response.noContent().build();
    }
}