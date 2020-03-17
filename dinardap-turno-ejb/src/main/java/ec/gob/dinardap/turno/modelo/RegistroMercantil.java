package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the registro_mercantil database table.
 * 
 */
@Entity
@Table(name="registro_mercantil")
@NamedQuery(name="RegistroMercantil.findAll", query="SELECT r FROM RegistroMercantil r")
public class RegistroMercantil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REGISTRO_MERCANTIL_REGISTROMERCANTILID_GENERATOR", sequenceName="REGISTRO_MERCANTIL_REGISTRO_MERCANTIL_ID_SEQ", schema="ec_dinardap_turno", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REGISTRO_MERCANTIL_REGISTROMERCANTILID_GENERATOR")
	@Column(name="registro_mercantil_id")
	private Integer registroMercantilId;

	private String nombre;

	private Short tipo;

	//bi-directional many-to-one association to PlanificacionRegistro
	@OneToMany(mappedBy="registroMercantil")
	private List<PlanificacionRegistro> planificacionRegistros;

	//bi-directional many-to-one association to Turno
	@OneToMany(mappedBy="registroMercantil")
	private List<Turno> turnos;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="registroMercantil")
	private List<Usuario> usuarios;

	public RegistroMercantil() {
	}

	public Integer getRegistroMercantilId() {
		return this.registroMercantilId;
	}

	public void setRegistroMercantilId(Integer registroMercantilId) {
		this.registroMercantilId = registroMercantilId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Short getTipo() {
		return this.tipo;
	}

	public void setTipo(Short tipo) {
		this.tipo = tipo;
	}

	public List<PlanificacionRegistro> getPlanificacionRegistros() {
		return this.planificacionRegistros;
	}

	public void setPlanificacionRegistros(List<PlanificacionRegistro> planificacionRegistros) {
		this.planificacionRegistros = planificacionRegistros;
	}

	public PlanificacionRegistro addPlanificacionRegistro(PlanificacionRegistro planificacionRegistro) {
		getPlanificacionRegistros().add(planificacionRegistro);
		planificacionRegistro.setRegistroMercantil(this);

		return planificacionRegistro;
	}

	public PlanificacionRegistro removePlanificacionRegistro(PlanificacionRegistro planificacionRegistro) {
		getPlanificacionRegistros().remove(planificacionRegistro);
		planificacionRegistro.setRegistroMercantil(null);

		return planificacionRegistro;
	}

	public List<Turno> getTurnos() {
		return this.turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Turno addTurno(Turno turno) {
		getTurnos().add(turno);
		turno.setRegistroMercantil(this);

		return turno;
	}

	public Turno removeTurno(Turno turno) {
		getTurnos().remove(turno);
		turno.setRegistroMercantil(null);

		return turno;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setRegistroMercantil(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setRegistroMercantil(null);

		return usuario;
	}

}