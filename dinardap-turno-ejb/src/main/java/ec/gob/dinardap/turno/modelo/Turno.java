package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the turno database table.
 *
 */
@Entity
@NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t")
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TURNO_TURNOID_GENERATOR", sequenceName = "TURNO_TURNO_ID_SEQ", schema = "ec_dinardap_turno", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TURNO_TURNOID_GENERATOR")
    @Column(name = "turno_id")
    private Integer turnoId;

    @Column(name = "atendido_por")
    private String atendidoPor;

    private String cedula;

    private String celular;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Temporal(TemporalType.DATE)
    private Date dia;

    private Short estado;

    private String hora;

    private String nombre;

    private String validador;

    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    //bi-directional many-to-one association to RegistroMercantil
    @ManyToOne
    @JoinColumn(name = "registro_mercantil_id")
    private RegistroMercantil registroMercantil;

    public Turno() {
    }

    public Integer getTurnoId() {
        return this.turnoId;
    }

    public void setTurnoId(Integer turnoId) {
        this.turnoId = turnoId;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public void setAtendidoPor(String atendidoPor) {
        this.atendidoPor = atendidoPor;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Date getDia() {
        return this.dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Short getEstado() {
        return this.estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValidador() {
        return this.validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public RegistroMercantil getRegistroMercantil() {
        return this.registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

}
