package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.UsoPuntosDetalle;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ConsultasDAO {

    public ConsultasDAO() {

    }

    @PersistenceContext
    private EntityManager em;

    /**
     * Encontrar todos los usos de puntos de acuerdo al concepto de utilizacion.
     * @param descripcionConcepto Descripcion del concepto del cual listar
     * @return Lista de datos de cliente, descripcion del concepto de uso, fecha, puntos utilizados.
     * */
    public List<UsoPuntosDetalle> findPointsUseByConcept (String descripcionConcepto) {
        return em.createQuery("from UsoPuntosDetalle u " +
                "where u.usoPuntosCabecera.conceptoUso.descripcionConcepto = :descripcionConcepto", UsoPuntosDetalle.class).setParameter("descripcionConcepto", descripcionConcepto).getResultList();
    }

    /***
     * Encontrar todos los usos de puntos de acuerdo a la fecha de utilizacion.
     * @param fecha Fecha de uso de los puntos (dd-mm-yyyy)
     * @return Lista de datos de cliente, descripcion del concepto de uso, fecha, puntos utilizados.
     */
    public List<UsoPuntosDetalle> findPointsUseByDate (String fecha) {
        return em.createQuery("from UsoPuntosDetalle u " +
                "where to_char(u.usoPuntosCabecera.fecha, 'dd-mm-yyyy') = :fecha", UsoPuntosDetalle.class).setParameter("fecha", fecha).getResultList();
    }

    /**
     * Encontrar todos los usos de puntos de acuerdo al cliente.
     * @param id ID del cliente.
     * @return Lista de datos de cliente, descripcion del concepto de uso, fecha, puntos utilizados.
     */
    public List<UsoPuntosDetalle> findPointsUseByClient (int id) {
        return em.createQuery("from UsoPuntosDetalle u " +
                "where u.usoPuntosCabecera.cliente.id = :id", UsoPuntosDetalle.class).setParameter("id", id).getResultList();
    }

    /**
     * Encontrar todas las bolsas de puntos de acuerdo al cliente.
     * @param id ID del cliente
     * @return Lista de datos de cliente, datos de bolsa de puntos.
     */
    public List<BolsaPuntos> findPointsBagByClient(int id) {
        return em.createQuery("from BolsaPuntos b " +
                        "where b.cliente.id = :id", BolsaPuntos.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Encontrar todas las bolsas de puntos de acuerdo al puntaje asignado en el rango [min, max]
     * @param min Minimo de puntaje asignado de las bolsas a buscar.
     * @param max Maximo de puntaje asignado de las bolsas a buscar.
     * @return Lista de datos de cliente, datos de bolsa de puntos.
     */
    public List<BolsaPuntos> findPointsBagByRange(int min, int max) {
        return em.createQuery("from BolsaPuntos b " +
                "where b.puntajeAsignado >= :min and b.puntajeAsignado <= :max", BolsaPuntos.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }

    /**
     * Encontrar todas las bolsas de puntos cuya fecha de vencimiento es menor a dentro de cierta cantidad de dias.
     *
     * @param dias Cantidad de dias, desde hoy en adelante, a revisar las fechas de vencimiento de las bolsas.
     * @return Lista de datos de cliente, cantidad de puntos con riesgo de vencer.
     */
    public List<Query> findClientByBagExpirationDate(int dias) {
        List<Query>result = em.createNativeQuery("select c.nombre, c.apellido, c.ci, sum(b.saldo_puntos)\n" +
                        "from cliente c, bolsa_puntos b\n" +
                        "where c.id = b.cliente_id and b.fecha_caducidad <= CURRENT_DATE + :dias * INTERVAL '1 day'" +
                        "group by c.nombre, c.apellido, c.ci")
                .setParameter("dias", dias)
                .getResultList();

        return result;
    }

    /**
     * Encontrar todos los clientes con nombre que comience con "nombre"
     * @param nombre Nombre de cliente
     * @return Lista de clientes
     */
    public List<Cliente> findClientByName(String nombre) {
        return em.createQuery("from Cliente where lower(:nombre) like substring(lower(nombre), 1, char_length(:nombre)) ", Cliente.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    /**
     * Encontrar todos los clientes con apellido que empiece con "apellido"
     * @param apellido Apellido de cliente
     * @return Lista de clientes
     */
    public List<Cliente> findClientBySurname(String apellido) {
        return em.createQuery("from Cliente where lower(:apellido) like substring(lower(apellido), 1, char_length(:apellido))", Cliente.class)
                .setParameter("apellido", apellido)
                .getResultList();
    }

    /**
     * Encontrar todos los clientes por fecha de cumpleaños
     * @param ddmm Fecha de cumpleaños (en formato diaMes dd-mm)
     * @return Lista de clientes
     */
    public List<Cliente> findClientByBirthday(String ddmm) {
        return em.createQuery("from Cliente " +
                "where to_char(fechaNacimiento, 'dd-mm') = :ddmm", Cliente.class)
                .setParameter("ddmm", ddmm)
                .getResultList();
    }
}
