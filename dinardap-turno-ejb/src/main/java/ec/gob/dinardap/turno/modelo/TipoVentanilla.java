package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tipo_ventanilla database table.
 * 
 */
@Entity
@Table(name="tipo_ventanilla")
@NamedQuery(name="TipoVentanilla.findAll", query="SELECT t FROM TipoVentanilla t")
public class TipoVentanilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_VENTANILLA_TIPOVENTANILLAID_GENERATOR", sequenceName="TIPO_VENTANILLA_TIPO_VENTANILLA_ID_SEQ", allocationSize = 1, schema = "ec_dinardap_turno")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_VENTANILLA_TIPOVENTANILLAID_GENERATOR")
	@Column(name="tipo_ventanilla_id")
	private Integer tipoVentanillaId;

	private Short estado;

	private String nombre;

	//bi-directional many-to-one association to PlanificacionRegistro
	@OneToMany(mappedBy="tipoVentanilla")
	private List<PlanificacionRegistro> planificacionRegistros;

	public TipoVentanilla() {
	}

	public Integer getTipoVentanillaId() {
		return this.tipoVentanillaId;
	}

	public void setTipoVentanillaId(Integer tipoVentanillaId) {
		this.tipoVentanillaId = tipoVentanillaId;
	}

	public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PlanificacionRegistro> getPlanificacionRegistros() {
		return this.planificacionRegistros;
	}

	public void setPlanificacionRegistros(List<PlanificacionRegistro> planificacionRegistros) {
		this.planificacionRegistros = planificacionRegistros;
	}

	public PlanificacionRegistro addPlanificacionRegistro(PlanificacionRegistro planificacionRegistro) {
		getPlanificacionRegistros().add(planificacionRegistro);
		planificacionRegistro.setTipoVentanilla(this);

		return planificacionRegistro;
	}

	public PlanificacionRegistro removePlanificacionRegistro(PlanificacionRegistro planificacionRegistro) {
		getPlanificacionRegistros().remove(planificacionRegistro);
		planificacionRegistro.setTipoVentanilla(null);

		return planificacionRegistro;
	}

}