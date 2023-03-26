package py.com.progweb.prueba.ejb;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.VencimientoPuntos;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;




import java.util.List;
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

    public void delete(VencimientoPuntos vencimientoPuntos) {
        long id = vencimientoPuntos.getId();
        VencimientoPuntos v = em.find(VencimientoPuntos.class, id);
        if (v != null) {
            em.remove(v);
        }
    }
}


