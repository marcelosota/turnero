package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_USUARIOID_GENERATOR", sequenceName="USUARIO_USUARIO_ID_SEQ", schema="ec_dinardap_turno", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_USUARIOID_GENERATOR")
	@Column(name="usuario_id")
	private Integer usuarioId;

	private String cedula;

	private String contrasena;

	private Short estado;

	//bi-directional many-to-one association to RegistroMercantil
	@ManyToOne
	@JoinColumn(name="registro_mercantil_id")
	private RegistroMercantil registroMercantil;

	public Usuario() {
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public RegistroMercantil getRegistroMercantil() {
		return this.registroMercantil;
	}

	public void setRegistroMercantil(RegistroMercantil registroMercantil) {
		this.registroMercantil = registroMercantil;
	}

}