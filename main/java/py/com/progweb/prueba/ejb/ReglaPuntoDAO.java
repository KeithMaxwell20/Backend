package py.com.progweb.prueba.ejb;

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
