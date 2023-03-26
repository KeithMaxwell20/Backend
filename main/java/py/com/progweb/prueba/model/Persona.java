package py.com.progweb.prueba.model;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="persona")
public class Persona {

    @Id
    @Column(name = "id_persona")
    @Basic(optional = false)
    @GeneratedValue(generator = "personaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "personaSec", sequenceName = "persona_sec", allocationSize = 0)
    private Integer idPersona;
    @Column(name = "nombre", length = 50)
    @Basic(optional = false)
    private String nombre;
    @Column(name = "apellido", length = 50)
    @Basic(optional = false)
    private String apellido;

    @OneToMany(mappedBy = "persona")
    private List<Agenda> listaAgenda;

    public List<Agenda> getListaAgenda() {
        return listaAgenda;
    }

    public void setListaAgenda(List<Agenda> listaAgenda) {
        this.listaAgenda = listaAgenda;
    }

    public Persona() {
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
