package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ConsultasDAO;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.UsoPuntosDetalle;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("consultas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsultasRest {

    @Inject
    ConsultasDAO consultasDao;

    @GET
    @Path("/nombre")
    public Response indexName(@QueryParam("q") String nombre) {
        try {
            List<Cliente> listaCliente = consultasDao.findClientByName(nombre);
            if (listaCliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaCliente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/apellido")
    public Response indexSurname(@QueryParam("q") String apellido) {
        try {
            List<Cliente> listaCliente = consultasDao.findClientBySurname(apellido);
            if (listaCliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaCliente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/cumpleanhos")
    public Response indexBirthday(@QueryParam("q") String cumpleanhos) {
        try {
            List<Cliente> listaCliente = consultasDao.findClientByBirthday(cumpleanhos);
            if (listaCliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaCliente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/vencimiento")
    public Response expiration(@QueryParam("q") int diasVencimiento) {
        try {
            List<Query> listaCliente = consultasDao.findClientByBagExpirationDate(diasVencimiento);
            if (listaCliente == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaCliente).build();
        } catch (Exception e) {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing clients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/bolsas/{id}")
    public Response bolsas(@PathParam("id") int id) {
        try {
            List<BolsaPuntos> listaBolsas = consultasDao.findPointsBagByClient(id);
            if(listaBolsas == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaBolsas).build();
        } catch (Exception e ) {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing point bags: " + e.getMessage()).build();
        }

    }

    @GET
    @Path("/bolsas/rango")
    public Response bolsasPorRango(@QueryParam("min") int min,
                                   @QueryParam("max") int max) {
        try {
            List<BolsaPuntos> listaBolsas = consultasDao.findPointsBagByRange(min, max);
            if (listaBolsas == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaBolsas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing point bags: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("uso-puntos/cliente/{id}")
    public Response pointUsesByClient(@PathParam("id") int id) {
        try {
            List<UsoPuntosDetalle> listaUsos = consultasDao.findPointsUseByClient(id);
            if (listaUsos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaUsos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing point uses: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("uso-puntos/fecha")
    public Response pointUsesByDate(@QueryParam("q") String fecha) {
        try {
            List<UsoPuntosDetalle> listaUsos = consultasDao.findPointsUseByDate(fecha);
            if (listaUsos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaUsos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing point uses: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("uso-puntos/descripcion")
    public Response pointUsesByConcept(@QueryParam("q") String descripcionConcepto) {
        try {
            List<UsoPuntosDetalle> listaUsos = consultasDao.findPointsUseByConcept(descripcionConcepto);
            if (listaUsos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaUsos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error ocurred while listing point uses: " + e.getMessage()).build();
        }
    }


}