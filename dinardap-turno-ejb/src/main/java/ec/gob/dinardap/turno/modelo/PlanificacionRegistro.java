package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the planificacion_registro database table.
 * 
 */
@Entity
@Table(name="planificacion_registro")
@NamedQuery(name="PlanificacionRegistro.findAll", query="SELECT p FROM PlanificacionRegistro p")
public class PlanificacionRegistro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLANIFICACION_REGISTRO_PLANIFICACIONID_GENERATOR", sequenceName="PLANIFICACION_REGISTRO_PLANIFICACION_ID_SEQ", schema="ec_dinardap_turno", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLANIFICACION_REGISTRO_PLANIFICACIONID_GENERATOR")
	@Column(name="planificacion_id")
	private Integer planificacionId;

	@Column(name="duracion_tramite")
	private Short duracionTramite;

	@Column(name="hora_fin")
	private Short horaFin;

	@Column(name="hora_inicio")
	private Short horaInicio;

	private Short ventanilla;

	//bi-directional many-to-one association to RegistroMercantil
	@ManyToOne
	@JoinColumn(name="registro_mercantil_id")
	private RegistroMercantil registroMercantil;

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

	public Short getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Short horaFin) {
		this.horaFin = horaFin;
	}

	public Short getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Short horaInicio) {
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

}