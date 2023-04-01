package py.com.progweb.prueba.ejb;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
public class ClienteDAO extends GeneralABMFunction<Cliente> {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public ClienteDAO() {
    }

    public Cliente findById(int id) {
        return em.find(Cliente.class, id);
    }
    public void save(Cliente cliente) {
        em.persist(cliente);
    }

    public void delete(Cliente cliente) {
        int id = cliente.getId();
        Cliente c = em.find(Cliente.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    public List<Cliente> findAll() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }
}