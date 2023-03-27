package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.BolsaPuntos;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Stateless
public class BolsaPuntosDAO extends GeneralABMFunction<BolsaPuntos> {
    @PersistenceContext
    private EntityManager em;

    public BolsaPuntosDAO() {
    }

    public BolsaPuntos findById(int id) {
        return em.find(BolsaPuntos.class, id);
    }
    public void save(BolsaPuntos bolsaPuntos) {
        em.persist(bolsaPuntos);
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
