package py.com.progweb.prueba.model;

import javax.persistence.*;

@Entity
@Table(name = "regla_punto")
public class ReglaPuntos {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_regla_punto")
    @SequenceGenerator(name = "id_regla_punto", sequenceName = "regla_punto_id_seq", allocationSize = 0)
    private int id;

    @Column(name = "limite_inferior")
    private int limiteInferior;

    @Column(name = "limite_superior")
    private int limiteSuperior;

    @Column(name = "monto_equivalencia")
    private int montoEquivalencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(int limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(int limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public int getMontoEquivalencia() {
        return montoEquivalencia;
    }

    public void setMontoEquivalencia(int montoEquivalencia) {
        this.montoEquivalencia = montoEquivalencia;
    }
}
