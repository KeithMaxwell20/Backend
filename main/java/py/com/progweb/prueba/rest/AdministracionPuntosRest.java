package py.com.progweb.prueba.rest;
import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.AdministracionPuntos;
import py.com.progweb.prueba.ejb.AdministracionPuntosDAO;
import py.com.progweb.prueba.model.Cliente;

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
    public Response listar() {
        List<AdministracionPuntos> puntos = administracionPuntosDAO.findAll();
        return Response.ok(puntos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        AdministracionPuntos administracionPuntos = administracionPuntosDAO.findById(id);
        return Response.ok(administracionPuntos).build();
    }

    @POST
    public Response agregar(AdministracionPuntos administracionPuntos) {
        administracionPuntosDAO.save(administracionPuntos);
        return Response.ok(administracionPuntos).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, AdministracionPuntos administracionPuntos) {
        administracionPuntos.setId(id);
        administracionPuntosDAO.update(administracionPuntos);
        return Response.ok(administracionPuntos).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        AdministracionPuntos administracionPuntos = administracionPuntosDAO.findById(id);
        administracionPuntosDAO.delete(administracionPuntos);
        return Response.noContent().build();
    }
}