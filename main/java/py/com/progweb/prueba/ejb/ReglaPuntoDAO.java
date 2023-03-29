package py.com.progweb.prueba.ejb;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.ReglaPuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ReglaPuntoDAO extends GeneralABMFunction<ReglaPuntos> {

    public ReglaPuntoDAO(){
    }
    @PersistenceContext
    private EntityManager em;

    public ReglaPuntos findById(int id) {
        return em.find(ReglaPuntos.class, id);
    }
    public void save(ReglaPuntos reglaPunto) {
        em.persist(reglaPunto);
    }

    /***
     * Calcula el equivalente en puntos de una cantidad dada, de acuerdo a las reglas de puntos ya definidas.
     * @param amount Cantidad de la cual hallar su equivalencia.
     * @return Entero representando la cantidad de puntos.
     */
    public int checkAmountPoints(int monto) {
        ReglaPuntos reglaPuntos = em.createQuery("from ReglaPuntos r " +
                "where r.limiteInferior <= :monto and r.limiteSuperior >= :monto", ReglaPuntos.class)
                .setParameter("monto", monto)
                .getSingleResult();
        return Math.floorDiv(monto, reglaPuntos.getMontoEquivalencia());
    }

    public void delete(ReglaPuntos reglaPunto) {
        int id = reglaPunto.getId();
        ReglaPuntos c = em.find(ReglaPuntos.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
    public List<ReglaPuntos> findAll() {
        TypedQuery<ReglaPuntos> query = em.createQuery("SELECT c FROM ReglaPuntos c", ReglaPuntos.class);
        return query.getResultList();
    }
}
