package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.BolsaPuntosDAO;
import py.com.progweb.prueba.model.BolsaPuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bolsa_puntos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BolsaPuntosRest {
    @Inject
    BolsaPuntosDAO bolsaPuntosDAO;

    @GET
    public Response index() {
        try {
            List<BolsaPuntos> bolsaPuntos = bolsaPuntosDAO.findAll();
            return Response.ok(bolsaPuntos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") int id) {
        try{
            BolsaPuntos bolsaPuntos = bolsaPuntosDAO.findById(id);
            if (bolsaPuntos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(bolsaPuntos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    public Response store(BolsaPuntos bolsaPuntos) {
        try{
            bolsaPuntosDAO.save(bolsaPuntos);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, BolsaPuntos bolsaPuntos) {
        try {

            BolsaPuntos bolsaPuntosExistente = bolsaPuntosDAO.findById(id);

            if (bolsaPuntosExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            bolsaPuntosDAO.update(bolsaPuntosExistente, bolsaPuntos);
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try{
            BolsaPuntos bolsaPuntos = bolsaPuntosDAO.findById(id);
            if (bolsaPuntos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            bolsaPuntosDAO.delete(bolsaPuntos);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while listing clients: " + e.getMessage()+e.getCause()).build();
        }
    }
}
