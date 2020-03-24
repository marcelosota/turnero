package ec.gob.dinardap.turno.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.autorizacion.util.EncriptarCadenas;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.Usuario;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;

@Named(value="cambiarContrasenaCtrl")
@ViewScoped
public class CambiarContrasenaCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5963176473739246926L;
	
	@EJB
	private UsuarioServicio usuarioServicio;

	private String cedula;
	private String contrasena;
	private String nuevaContrasena;
	private String repetirContrasena;
	
	public void modificarContrasena() {
		Usuario usuario = usuarioServicio.verificarCredenciales(getCedula(), EncriptarCadenas.encriptarCadenaSha1(getContrasena()));
		if(usuario != null) {
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setUsuarioId(usuario.getUsuarioId());
			usuarioDto.setCedula(usuario.getCedula());
			usuarioDto.setContrasena(EncriptarCadenas.encriptarCadenaSha1(getNuevaContrasena()));
			usuarioDto.setNombre(usuario.getNombre());
			usuarioDto.setRegistroMercantilId(usuario.getRegistroMercantil().getRegistroMercantilId());
			usuarioDto.setPerfilId(usuario.getPerfil().getPerfilId());
			usuarioDto.setEstado(usuario.getEstado());
			usuarioServicio.modificarUsuario(usuarioDto);
			limpiarCampos();
			addInfoMessage(getBundleMensaje("cambiar.contrasena", null), null);
		}else
			addErrorMessage(null, getBundleMensaje("error.credenciales", null), null);
	}
	
	public void limpiarCampos() {
		setCedula("");
		setContrasena("");
		setNuevaContrasena("");
		setRepetirContrasena("");
	}
	
	public void regresar() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			//limpiarCampos();
			String valor = context.getRequestContextPath();
			context.redirect(valor + "/index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}
	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNuevaContrasena() {
		return nuevaContrasena;
	}
	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}
	public String getRepetirContrasena() {
		return repetirContrasena;
	}
	public void setRepetirContrasena(String repetirContrasena) {
		this.repetirContrasena = repetirContrasena;
	}
	
}
