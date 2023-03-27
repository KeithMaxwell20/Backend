package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.UsoPuntosDetalle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UsoPuntosDetalleDAO extends GeneralABMFunction <UsoPuntosDetalle> {
    @PersistenceContext
    private EntityManager em;

    public UsoPuntosDetalleDAO() {
    }

    public UsoPuntosDetalle findById(int id) {
        return em.find(UsoPuntosDetalle.class, id);
    }
    public void save(UsoPuntosDetalle usoPuntosDetalle) {;
        em.persist(usoPuntosDetalle);
    }

    public void delete(UsoPuntosDetalle usoPuntosDetalle) {
        int id = usoPuntosDetalle.getId();
        UsoPuntosDetalle c = em.find(UsoPuntosDetalle.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
    public List<UsoPuntosDetalle> findAll() {
        TypedQuery<UsoPuntosDetalle> query = em.createQuery("SELECT c FROM UsoPuntosDetalle c", UsoPuntosDetalle.class);
        return query.getResultList();
    }
}
