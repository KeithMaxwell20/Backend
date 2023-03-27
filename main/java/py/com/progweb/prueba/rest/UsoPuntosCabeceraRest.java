package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.BolsaPuntosDAO;
import py.com.progweb.prueba.ejb.UsoPuntosCabeceraDAO;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.UsoPuntosCabecera;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("uso_puntos_cabecera")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsoPuntosCabeceraRest {
    @Inject
    UsoPuntosCabeceraDAO usoPuntosCabeceraDAO;

    @GET
    public Response index() {
        try {
            List<UsoPuntosCabecera> usoPuntosCabeceras = usoPuntosCabeceraDAO.findAll();
            return Response.ok(usoPuntosCabeceras).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") int id) {
        try{
            UsoPuntosCabecera usoPuntosCabecera = usoPuntosCabeceraDAO.findById(id);
            if (usoPuntosCabecera == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(usoPuntosCabecera).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    public Response store(UsoPuntosCabecera usoPuntosCabecera) {
        try{
            usoPuntosCabeceraDAO.save(usoPuntosCabecera);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, UsoPuntosCabecera usoPuntosCabecera) {
        try {

            UsoPuntosCabecera usoPuntosCabeceraExistente = usoPuntosCabeceraDAO.findById(id);

            if (usoPuntosCabeceraExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            usoPuntosCabeceraDAO.update(usoPuntosCabeceraExistente, usoPuntosCabecera);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try{
            UsoPuntosCabecera usoPuntosCabecera = usoPuntosCabeceraDAO.findById(id);
            if (usoPuntosCabecera == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            usoPuntosCabeceraDAO.delete(usoPuntosCabecera);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()+e.getCause()).build();
        }
    }
}
