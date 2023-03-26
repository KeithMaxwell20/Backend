package py.com.progweb.prueba.ejb;
import py.com.progweb.prueba.model.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
public class ClienteDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public ClienteDAO() {
    }

    public void agregar(Cliente cliente) {
        em.persist(cliente);
    }

    public void modificar(Cliente cliente) {
        em.merge(cliente);
    }

    public void eliminar(Cliente cliente) {
        int id = cliente.getId();
        Cliente c = em.find(Cliente.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    public Cliente obtener(int id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> listar() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }
}