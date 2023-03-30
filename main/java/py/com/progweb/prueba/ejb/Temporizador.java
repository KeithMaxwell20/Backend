package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.BolsaPuntos;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class Temporizador {
    @PersistenceContext
    private EntityManager em;


    @Schedule(minute = "*", hour = "*", second = "0/15")
    public void updateSaldo() {
        try {
            System.out.println(new Date() + ": Verificando vencimiento de las bolsas...");
            List<BolsaPuntos> listaBolsas = bolsasVencidas();
            for (BolsaPuntos bolsa : listaBolsas) {
                bolsa.setSaldoPuntos(0);
            }
            guardarLista(listaBolsas);
        } catch (Exception e) {
            System.out.println("Exception in updateSaldo: " + e.getMessage());
        }
    }

    public List<BolsaPuntos> bolsasVencidas() {
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime(new Date()); // Fecha Actual
        return em.createQuery("from BolsaPuntos b " +
                        "where b.fechaCaducidad < :actual and b.saldoPuntos > 0", BolsaPuntos.class)
                .setParameter("actual", fechaActual.getTime())
                .getResultList();
    }

    public void guardarLista(List<BolsaPuntos> listaBolsas) {
        for(BolsaPuntos bolsa : listaBolsas) {
            em.persist(bolsa);
        }
    }
}