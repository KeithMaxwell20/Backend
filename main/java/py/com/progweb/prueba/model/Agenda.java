package py.com.progweb.prueba.model;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="agenda")
public class Agenda {
    @Id
    @Column(name = "id_agenda")
    @Basic(optional = false)
    @GeneratedValue(generator = "agendaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "agendaSec", sequenceName = "agenda_sec", allocationSize = 0)
    private Integer idAgenda;

    @Column(name = "actividad", length = 200)
    @Basic(optional = false)
    private String actividad;

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE) // Depende de lo que diga en la bd, porque también podría ser TIMESTAMP o el otro
    private Date fecha;

    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false)
    private Persona persona;

    public Agenda(){

    }

    public Integer getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
