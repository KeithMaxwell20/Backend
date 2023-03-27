package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.UsoPuntosCabecera;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Stateless
public class UsoPuntosCabeceraDAO extends GeneralABMFunction<UsoPuntosCabecera> {
    @PersistenceContext
    private EntityManager em;

    @Inject
    ClienteDAO clienteDAO;
    public UsoPuntosCabeceraDAO() {
    }

    public UsoPuntosCabecera findById(int id) {
        return em.find(UsoPuntosCabecera.class, id);
    }
    public void save(UsoPuntosCabecera usoPuntosCabecera) {;
        em.persist(usoPuntosCabecera);
    }

    public void delete(UsoPuntosCabecera usoPuntosCabecera) {
        int id = usoPuntosCabecera.getId();
        UsoPuntosCabecera c = em.find(UsoPuntosCabecera.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
    public List<UsoPuntosCabecera> findAll() {
        TypedQuery<UsoPuntosCabecera> query = em.createQuery("SELECT c FROM UsoPuntosCabecera c", UsoPuntosCabecera.class);
        return query.getResultList();
    }
}
