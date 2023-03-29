package py.com.progweb.prueba.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bolsa_puntos")
public class BolsaPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bolsa_puntos_id")
    @SequenceGenerator(name = "bolsa_puntos_id", sequenceName = "bolsa_puntos_id_seq", allocationSize = 0)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha_asignacion")
    private Date fechaAsignacion;

    @Column(name = "fecha_caducidad")
    private Date fechaCaducidad;

    @Column(name = "puntaje_asignado")
    private int puntajeAsignado;

    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;

    @Column(name = "saldo_puntos")
    private int saldoPuntos;

    @Column(name = "monto_operacion")
    private int montoOperacion;

    //Constructor
    public BolsaPuntos() {}

    //Constructor
    public BolsaPuntos(Cliente cliente, Date fechaAsignacion, Date fechaCaducidad, int puntajeAsignado, int puntajeUtilizado, int saldoPuntos, int montoOperacion) {
        this.cliente = cliente;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaCaducidad = fechaCaducidad;
        this.puntajeAsignado = puntajeAsignado;
        this.puntajeUtilizado = puntajeUtilizado;
        this.saldoPuntos = saldoPuntos;
        this.montoOperacion = montoOperacion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(int puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public int getSaldoPuntos() {
        return saldoPuntos;
    }

    public void setSaldoPuntos(int saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }

    public int getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(int montoOperacion) {
        this.montoOperacion = montoOperacion;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
