package py.com.progweb.prueba.ejb;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import py.com.progweb.prueba.implementations.GeneralABMFunction;
import py.com.progweb.prueba.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class UsoPuntosCabeceraDAO extends GeneralABMFunction<UsoPuntosCabecera> {
    @PersistenceContext
    private EntityManager em;
    public UsoPuntosCabeceraDAO() {
    }

    /**
     * Registra el uso por parte de un cliente de los puntos de acuerdo al concepto de uso.
     * @param json JSON String con los siguientes campos: {"idCliente": X, "idConcepto": Y}
     * @return True si se procesa con exito,
     *         False si no hay puntos suficientes para completar la transaccion.
     */
    public boolean usingPoints(String jsonString) throws ParseException, NoResultException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);

        // Extraemos los valores del json
        Long idCliente = (Long) json.get("idCliente");
        Long idConcepto = (Long) json.get("idConcepto");

        // Extraemos de la BD al Cliente y al concepto de uso
        Cliente cliente = em.createQuery("from Cliente r " +
                        "where r.id = :id", Cliente.class)
                .setParameter("id", idCliente.intValue())
                .getSingleResult();


        AdministracionPuntos concepto = em.createQuery("from AdministracionPuntos a " +
                "where a.id = :id", AdministracionPuntos.class)
                .setParameter("id", idConcepto.longValue())
                .getSingleResult();

        // Listamos las bolsas de puntos del cliente ordenamos de fechas anteriores a fechas nuevas.
        List<BolsaPuntos> listaBolsas = em.createQuery("from BolsaPuntos b " +
                "where b.cliente.id = :id " +
                        "order by b.planificacion.fechaInicio asc", BolsaPuntos.class)
                .setParameter("id", idCliente.intValue())
                .getResultList();

        // Procesamos las bolsas de puntos (si una bolsa no contiene suficientes puntos
        // para realizar la compra, se pasa a la siguiente bolsa)
        int cantidadRevisada = 0;
        int cantidadNecesaria = concepto.getPuntosRequeridos();
        // Verificamos si es posible sumar la cantidad de puntos para realizar la operacion.
        for (int i = 0; i < listaBolsas.size(); i++) {
            // Mientras no acumulemos la cantidad necesaria, seguimos recorriendo la lista.
            if (cantidadRevisada < cantidadNecesaria) {
                cantidadRevisada+=listaBolsas.get(i).getSaldoPuntos();
            } else {
                break;
            }
        }

        // Si es posible usar los puntos para hacer el canje.
        if (cantidadRevisada >= cantidadNecesaria) {
            int puntosRestantes = cantidadNecesaria;

            // Obtenemos la fecha actual
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.setTime(new Date()); // Fecha Actual
            // Registramos los usos
            UsoPuntosCabecera usoCabecera = new UsoPuntosCabecera(cliente, cantidadNecesaria, fechaActual.getTime(), concepto);
            em.persist(usoCabecera);

            for(int i = 0; i < listaBolsas.size() && puntosRestantes > 0; i++) {
                BolsaPuntos aux = listaBolsas.get(i);
                // Usamos todos los puntos
                if (aux.getSaldoPuntos() > 0) {
                    if (puntosRestantes > aux.getSaldoPuntos()) {
                        // Creamos un uso_puntos_detalles para registrar el proceso de esta bolsa.
                        UsoPuntosDetalle usoPuntosDetalle = new UsoPuntosDetalle(   usoCabecera,
                                aux,
                                aux.getSaldoPuntos());

                        em.persist(usoPuntosDetalle); // Saving

                        // Registramos el proceso.
                        puntosRestantes = puntosRestantes - aux.getSaldoPuntos();
                        aux.setPuntajeUtilizado(aux.getPuntajeUtilizado()+aux.getSaldoPuntos()); // Ya se uso la misma cantidad de puntos que el asignado a la bolsa.
                        aux.setSaldoPuntos(0);
                        em.persist(aux); // Saving
                    } else { // Ya cubrimos todos los puntos
                        // Creamos un uso_puntos_detalles para registrar el proceso de esta bolsa.
                        UsoPuntosDetalle usoPuntosDetalle = new UsoPuntosDetalle(   usoCabecera,
                                listaBolsas.get(i),
                                puntosRestantes);

                        em.persist(usoPuntosDetalle); // Saving

                        aux.setSaldoPuntos(aux.getSaldoPuntos()-puntosRestantes);
                        aux.setPuntajeUtilizado(aux.getPuntajeUtilizado()+puntosRestantes);

                        em.persist(aux); // Saving
                        puntosRestantes=0;
                    }

                }
            }

            // Si se pudo usar los puntos con exito, enviamos el correo.
            Email email = new Email();
            String encabezado = "Uso de puntos para el canje de productos";
            String texto = "Estimado cliente " + cliente.getNombre() + " " + cliente.getApellido() + ", con CI " +
                    cliente.getNumeroDocumento() +
                    ".\nSe ha registrado una transaccion en tu cuenta, usando puntos disponibles de cliente!" +
                    "\n\nCantidad Puntos Usados: " + cantidadNecesaria +
                    "\nItem Canjeado: " + concepto.getDescripcionConcepto();
            email.sendEmail(cliente.getEmail(), encabezado, texto);

            return true;
        } else { // No es posible usar los puntos para hacer el canje
            System.out.println("No hay puntos suficientes! Requeridos: " + cantidadNecesaria + "| Restantes: " + cantidadRevisada);
            return false;
        }
    }

    public UsoPuntosCabecera findById(int id) {
        return em.find(UsoPuntosCabecera.class, id);
    }
    public void save(UsoPuntosCabecera usoPuntosCabecera) {
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
