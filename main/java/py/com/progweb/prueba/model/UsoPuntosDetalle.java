package py.com.progweb.prueba.model;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "uso_puntos_detalles")
public class UsoPuntosDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uso_puntos_detalles_id")
    @SequenceGenerator(name = "uso_puntos_detalles_id", sequenceName = "uso_puntos_detalles_id_seq", allocationSize = 0)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uso_puntos_cabecera_id")
    private UsoPuntosCabecera usoPuntosCabecera;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bolsa_puntos_id")
    private BolsaPuntos bolsaPuntos;
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;

    //Constructor
    public UsoPuntosDetalle() {}

    //Constructor.
    public UsoPuntosDetalle(UsoPuntosCabecera usoPuntosCabecera, BolsaPuntos bolsaPuntos, int puntajeUtilizado) {
        this.usoPuntosCabecera = usoPuntosCabecera;
        this.bolsaPuntos = bolsaPuntos;
        this.puntajeUtilizado = puntajeUtilizado;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsoPuntosCabecera getUsoPuntosCabecera() {
        return usoPuntosCabecera;
    }

    public void setUsoPuntosCabecera(UsoPuntosCabecera usoPuntosCabecera) {
        this.usoPuntosCabecera = usoPuntosCabecera;
    }

    public BolsaPuntos getBolsaPuntos() {
        return bolsaPuntos;
    }

    public void setBolsaPuntos(BolsaPuntos bolsaPuntos) {
        this.bolsaPuntos = bolsaPuntos;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }



}
