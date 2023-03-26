package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;
import py.com.progweb.prueba.model.Cliente;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class ClienteDAO {
    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void agregar(Cliente cliente) {
        em.persist(cliente);
    }

    public void modificar(Cliente cliente) {
        em.merge(cliente);
    }

    public void eliminar(Cliente cliente) {
        em.remove(cliente);
    }

    public Cliente obtener(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> listar() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }
}