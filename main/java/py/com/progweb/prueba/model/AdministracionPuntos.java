package py.com.progweb.prueba.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "administracion_puntos")
public class AdministracionPuntos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_puntos")
    @SequenceGenerator(name = "id_puntos", sequenceName = "administracion_puntos_id_seq", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion_concepto")
    private String descripcionConcepto;

    @Column(name = "puntos_requeridos", nullable = false)
    private Integer puntosRequeridos;

    public AdministracionPuntos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionConcepto() {
        return descripcionConcepto;
    }

    public void setDescripcionConcepto(String descripcionConcepto) {
        this.descripcionConcepto = descripcionConcepto;
    }

    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }
}