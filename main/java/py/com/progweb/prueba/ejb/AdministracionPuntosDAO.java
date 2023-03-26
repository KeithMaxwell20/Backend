package py.com.progweb.prueba.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.AdministracionPuntos;

import java.util.List;


@Stateless
public class AdministracionPuntosDAO extends GeneralABMFunction <AdministracionPuntos> {

    @PersistenceContext
    private EntityManager em;

    public AdministracionPuntosDAO(){

    }

    public AdministracionPuntos findById(Long id) {
        return em.find(AdministracionPuntos.class, id);
    }

    public void save(AdministracionPuntos administracionPuntos) {
        em.persist(administracionPuntos);
    }

    public void delete(AdministracionPuntos administracionPuntos) {
        long id = administracionPuntos.getId();
        AdministracionPuntos punto = em.find(AdministracionPuntos.class, id);
        if (punto != null) {
            em.remove(punto);
        }
    }

    public List<AdministracionPuntos> findAll() {
        TypedQuery<AdministracionPuntos> query = em.createQuery("SELECT a FROM AdministracionPuntos a", AdministracionPuntos.class);
        return query.getResultList();
    }
}
