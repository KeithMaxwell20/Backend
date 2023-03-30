package py.com.progweb.prueba.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import py.com.progweb.prueba.ejb.BolsaPuntosDAO;
import py.com.progweb.prueba.ejb.ReglaPuntoDAO;
import py.com.progweb.prueba.ejb.UsoPuntosCabeceraDAO;

import javax.inject.Inject;

@Path("servicio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiciosREST {

    @Inject
    BolsaPuntosDAO bolsaPuntosDAO;

    @Inject
    UsoPuntosCabeceraDAO usoPuntos;

    @Inject
    ReglaPuntoDAO reglaPuntoDAO;

    @POST
    @Path("/carga-puntos")
    public Response cargarPuntos(String jsonString) {
        try{
            bolsaPuntosDAO.save(jsonString);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while creating point bags: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/uso-puntos")
    public Response usarPuntos(String jsonString) {
        try {
            return usoPuntos.usingPoints(jsonString) ?
                    Response.status(Response.Status.CREATED).entity("Transaction accepted!").build() :
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Not enough points left for this transaction!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while using points: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/consulta-puntos")
    public Response consultarPuntos(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            Long monto = (Long) json.get("monto");
            int cantPuntos = reglaPuntoDAO.checkAmountPoints(monto.intValue());
            json.put("monto", monto.intValue());
            json.put("puntos", cantPuntos);
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while consulting points: " + e.getMessage()).build();
        }
    }
}
