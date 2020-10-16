package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_atencion database table.
 * 
 */
@Entity
@Table(name="tipo_atencion")
@NamedQuery(name="TipoAtencion.findAll", query="SELECT t FROM TipoAtencion t")
public class TipoAtencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_ATENCION_TIPOATENCIONID_GENERATOR", sequenceName="TIPO_ATENCION_TIPO_ATENCION_ID_SEQ", allocationSize = 1, schema = "ec_dinardap_turno")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_ATENCION_TIPOATENCIONID_GENERATOR")
	@Column(name="tipo_atencion_id")
	private Short tipoAtencionId;

	private String descripcion;

	private Short estado;

	//bi-directional many-to-one association to Atencion
	@OneToMany(mappedBy="tipoAtencion")
	private List<Atencion> atencions;

	public TipoAtencion() {
	}

	public Short getTipoAtencionId() {
		return this.tipoAtencionId;
	}

	public void setTipoAtencionId(Short tipoAtencionId) {
		this.tipoAtencionId = tipoAtencionId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public List<Atencion> getAtencions() {
		return this.atencions;
	}

	public void setAtencions(List<Atencion> atencions) {
		this.atencions = atencions;
	}

	public Atencion addAtencion(Atencion atencion) {
		getAtencions().add(atencion);
		atencion.setTipoAtencion(this);

		return atencion;
	}

	public Atencion removeAtencion(Atencion atencion) {
		getAtencions().remove(atencion);
		atencion.setTipoAtencion(null);

		return atencion;
	}

}