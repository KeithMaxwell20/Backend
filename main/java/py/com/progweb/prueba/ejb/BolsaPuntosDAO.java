package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.BolsaPuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.ReglaPuntos;
import py.com.progweb.prueba.model.VencimientoPuntos;


@Stateless
public class BolsaPuntosDAO extends GeneralABMFunction<BolsaPuntos> {
    @PersistenceContext
    EntityManager em;

    public BolsaPuntosDAO() {
    }

    public BolsaPuntos findById(int id) {
        return em.find(BolsaPuntos.class, id);
    }

    /**
     * Guardamos una bolsa de puntos.
     * @param jsonString String en formato JSON con el id del cliente y el monto de compras.
     *                   Tiene el siguiente formato: {"id": X, "monto": Y}
     * @throws ParseException Error al parsear el strinJson
     */
    public void save(String jsonString) throws ParseException {
        int cantPuntos;
        JSONParser parser = new JSONParser();
        System.out.println("El string recibido es: " + jsonString);
        JSONObject json = (JSONObject) parser.parse(jsonString);
        System.out.println("El string recibido es: " + jsonString);
        // Verificamos la regla de puntos a utilizar
        System.out.println("El json monto: " + json.get("monto"));
        System.out.println("El json id: " + json.get("id"));
        Long monto = (Long) json.get("monto");
        Long id = (Long) json.get("id");
        ReglaPuntos registro = em.createQuery("from ReglaPuntos r " +
                        "where r.limiteInferior <= :monto and r.limiteSuperior >= :monto", ReglaPuntos.class)
                .setParameter("monto", monto.intValue())
                .getSingleResult();


        // Calculamos la cantidad de puntos a asignar
        cantPuntos = Math.floorDiv(monto.intValue(), registro.getMontoEquivalencia());


        Cliente cliente = em.createQuery("from Cliente c where c.id = :id", Cliente.class)
                .setParameter("id", id.intValue())
                .getSingleResult();

        Calendar fechaAsignacion = Calendar.getInstance();
        fechaAsignacion.setTime(new Date()); // Fecha Actual

        Calendar fechaVencimiento = Calendar.getInstance();
        fechaVencimiento.setTime(fechaAsignacion.getTime());
        fechaVencimiento.add(Calendar.DAY_OF_MONTH, 15); // Agregando dias
        int cantidadDias = 15;
        VencimientoPuntos planificacion = new VencimientoPuntos(fechaAsignacion.getTime(), fechaVencimiento.getTime(), cantidadDias);

        // Agregamos la cantidad de puntos correcta.
        BolsaPuntos bolsa = new BolsaPuntos(cliente, planificacion, cantPuntos, 0, cantPuntos,  monto.intValue());

        System.out.println("La bolsa de puntos a guardar es: " + bolsa);

        // Guardamos vencimientoPuntos
        em.persist(planificacion);

        // Guardamos la bolsa
        em.persist(bolsa);
    }

    public JSONObject getPointsByClient(int id) {
        JSONObject json = new JSONObject();

        // Obtenemos la cantidad de puntos
        Long cantPuntos = em.createQuery("SELECT sum(b.saldoPuntos) " +
                        "FROM BolsaPuntos b " +
                        "WHERE b.cliente.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();

        // Agregar al json
        json.put("id", id);

        // Si el query es null, significa que no tiene bolsas asignadas.
        // Retornamos 0
        if (cantPuntos == null) {
            json.put("puntos", 0);
        } else {
            json.put("puntos", cantPuntos.intValue());
        }

        return json;

    }

    public void delete(BolsaPuntos bolsaPuntos) {
        int id = bolsaPuntos.getId();
        BolsaPuntos c = em.find(BolsaPuntos.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
    public List<BolsaPuntos> findAll() {
        TypedQuery<BolsaPuntos> query = em.createQuery("SELECT c FROM BolsaPuntos c", BolsaPuntos.class);
        return query.getResultList();
    }
}
