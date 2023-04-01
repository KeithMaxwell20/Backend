package py.com.progweb.prueba.ejb;
import org.joda.time.Days;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.VencimientoPuntos;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;


import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VencimientoPuntosDAO extends GeneralABMFunction<VencimientoPuntos>{

    @PersistenceContext
    private EntityManager em;

    public VencimientoPuntosDAO(){

    }

    public void save(VencimientoPuntos vencimientoPuntos) {
        em.persist(vencimientoPuntos);
    }

    public VencimientoPuntos findById(Long id) {
        return em.find(VencimientoPuntos.class, id);
    }

    public List<VencimientoPuntos> findAll() {
        TypedQuery<VencimientoPuntos> query = em.createQuery("SELECT v FROM VencimientoPuntos v", VencimientoPuntos.class);
        return query.getResultList();
    }

    /*
    * TypedQuery<VencimientoPuntos> query = em.createQuery("SELECT v FROM VencimientoPuntos v", VencimientoPuntos.class);
        return query.getResultList();*/

    public boolean updatingExpirationDate(String newDate, Long id) {
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(newDate);

            // Hacemos el query para extraer la bolsa de puntos correcta
            BolsaPuntos bolsaPuntos = em.createQuery("from  BolsaPuntos b " +
                    "where b.id = :id", BolsaPuntos.class)
                    .setParameter("id", id.intValue())
                    .getSingleResult();

            // Extraemos el vencimiento_puntos correspondiente
            VencimientoPuntos vencimientoPuntos = bolsaPuntos.getPlanificacion();

            // Actualizamos la fecha fin.
            vencimientoPuntos.setFechaFin(date);

            // Actualizamos la diferencia de días.
            long diferenciaMillisegundos = Math.abs(date.getTime() - vencimientoPuntos.getFechaInicio().getTime());
            int cantNuevaDias = Math.toIntExact( TimeUnit.DAYS.convert(diferenciaMillisegundos, TimeUnit.MILLISECONDS));
            vencimientoPuntos.setDuracionDias(cantNuevaDias);

            // Guardamos los cambios.
            em.persist(vencimientoPuntos);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(VencimientoPuntos vencimientoPuntos) {
        long id = vencimientoPuntos.getId();
        VencimientoPuntos v = em.find(VencimientoPuntos.class, id);
        if (v != null) {
            em.remove(v);
        }
    }
}


