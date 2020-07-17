package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import javax.persistence.*;

import ec.gob.dinardap.seguridad.modelo.Perfil;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario", schema="ec_dinardap_turno")
@NamedQuery(name="UsuarioT.findAll", query="SELECT u FROM UsuarioT u")
public class UsuarioT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIOT_USUARIOID_GENERATOR", sequenceName="USUARIO_USUARIO_ID_SEQ", schema="ec_dinardap_turno", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIOT_USUARIOID_GENERATOR")
	@Column(name="usuario_id")
	private Integer usuarioId;

	private String cedula;

	private String contrasena;

	private Short estado;
	
	private String nombre;
	
	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="perfil_id")
	private Perfil perfil;

	//bi-directional many-to-one association to RegistroMercantil
	@ManyToOne
	@JoinColumn(name="registro_mercantil_id")
	private RegistroMercantil registroMercantil;

	public UsuarioT() {
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
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public RegistroMercantil getRegistroMercantil() {
		return this.registroMercantil;
	}

	public void setRegistroMercantil(RegistroMercantil registroMercantil) {
		this.registroMercantil = registroMercantil;
	}

}