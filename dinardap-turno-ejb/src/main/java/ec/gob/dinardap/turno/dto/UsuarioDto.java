package ec.gob.dinardap.turno.dto;

public class UsuarioDto {

	private Integer usuarioId;
	private String cedula;
	private String nombre;
	private String contrasena;
	private Integer registroMercantilId;
	private Integer perfilId;
	private Short estado;
	
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public Integer getRegistroMercantilId() {
		return registroMercantilId;
	}
	public void setRegistroMercantilId(Integer registroMercantilId) {
		this.registroMercantilId = registroMercantilId;
	}
	public Integer getPerfilId() {
		return perfilId;
	}
	public void setPerfilId(Integer perfilId) {
		this.perfilId = perfilId;
	}
	public Short getEstado() {
		return estado;
	}
	public void setEstado(Short estado) {
		this.estado = estado;
	}
}
