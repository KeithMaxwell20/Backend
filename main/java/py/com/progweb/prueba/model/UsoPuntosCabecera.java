package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "uso_puntos_cabecera")
public class UsoPuntosCabecera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uso_puntos_cabecera_id")
    @SequenceGenerator(name = "uso_puntos_cabecera_id", sequenceName = "puntos_cabecera_id_seq", allocationSize = 0)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administracion_puntos_id")
    private AdministracionPuntos conceptoUso;

    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;

    // Getters y Setters

    public UsoPuntosCabecera(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public AdministracionPuntos getConceptoUso() {
        return conceptoUso;
    }

    public void setConceptoUso(AdministracionPuntos conceptoUso) {
        this.conceptoUso = conceptoUso;
    }
}
