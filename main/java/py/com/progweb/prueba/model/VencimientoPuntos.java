package py.com.progweb.prueba.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "vencimiento_puntos")
public class VencimientoPuntos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_puntos_vencimiento")
    @SequenceGenerator(name = "id_puntos_vencimiento", sequenceName = "vencimiento_puntos_id_seq", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    // Constructors, getters and setters

    public VencimientoPuntos() {
    }

    public VencimientoPuntos(Date fechaInicio, Date fechaFin, int duracionDias) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionDias = duracionDias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }
}
