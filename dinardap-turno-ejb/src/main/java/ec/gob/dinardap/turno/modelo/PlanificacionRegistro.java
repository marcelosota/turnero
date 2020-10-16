package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the planificacion_registro database table.
 *
 */
@Entity
@Table(name = "planificacion_registro")
@NamedQuery(name = "PlanificacionRegistro.findAll", query = "SELECT p FROM PlanificacionRegistro p")
public class PlanificacionRegistro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PLANIFICACION_REGISTRO_PLANIFICACIONID_GENERATOR", sequenceName = "PLANIFICACION_REGISTRO_PLANIFICACION_ID_SEQ", schema = "ec_dinardap_turno", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANIFICACION_REGISTRO_PLANIFICACIONID_GENERATOR")
    @Column(name = "planificacion_id")
    private Integer planificacionId;

    @Column(name = "duracion_tramite")
    private Short duracionTramite;
    
    private Short estado;
    
    @Temporal(TemporalType.DATE)
	@Column(name="fecha_caducidad")
	private Date fechaCaducidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_vigencia")
	private Date fechaVigencia;

	@Column(name="hora_fin")
	private String horaFin;

	@Column(name="hora_inicio")
	private String horaInicio;

    private Short ventanilla;

    //bi-directional many-to-one association to RegistroMercantil
    @ManyToOne
    @JoinColumn(name = "registro_mercantil_id")
    private RegistroMercantil registroMercantil;
    
    //bi-directional many-to-one association to TipoVentanilla
  	@ManyToOne
  	@JoinColumn(name="tipo_ventanilla_id")
  	private TipoVentanilla tipoVentanilla;

    public PlanificacionRegistro() {
    }

    public Integer getPlanificacionId() {
        return this.planificacionId;
    }

    public void setPlanificacionId(Integer planificacionId) {
        this.planificacionId = planificacionId;
    }

    public Short getDuracionTramite() {
        return this.duracionTramite;
    }

    public void setDuracionTramite(Short duracionTramite) {
        this.duracionTramite = duracionTramite;
    }
    
    public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public Date getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaVigencia() {
		return this.fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

    public Short getVentanilla() {
        return this.ventanilla;
    }

    public void setVentanilla(Short ventanilla) {
        this.ventanilla = ventanilla;
    }

    public RegistroMercantil getRegistroMercantil() {
        return this.registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }
    
    public TipoVentanilla getTipoVentanilla() {
		return this.tipoVentanilla;
	}

	public void setTipoVentanilla(TipoVentanilla tipoVentanilla) {
		this.tipoVentanilla = tipoVentanilla;
	}

}
