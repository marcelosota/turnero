package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;

@Named(value="loginCtrl")
@ViewScoped
public class LoginCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873444362560469061L;

	@EJB
	private UsuarioServicio usuarioServicio;
	
	private String usuario;
	private String contrasena;
	private Integer entidad;
	private List<RegistroMercantil> listaRM;
	
	public void validarUsuario() {
		if(usuarioServicio.validarUsuario(getUsuario(), getContrasena(), getEntidad())) {
			try {
				getHttpServletResponse().sendRedirect(getExternalContext().getRequestContextPath()+"/paginas/brand.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Integer getEntidad() {
		return entidad;
	}

	public void setEntidad(Integer entidad) {
		this.entidad = entidad;
	}

	public List<RegistroMercantil> getListaRM() {
		return listaRM;
	}

	public void setListaRM(List<RegistroMercantil> listaRM) {
		this.listaRM = listaRM;
	}
}
