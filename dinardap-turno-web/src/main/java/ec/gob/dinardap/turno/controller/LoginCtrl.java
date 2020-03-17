package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import ec.gob.dinardap.autorizacion.util.EncriptarCadenas;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Usuario;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
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
	
	@EJB
	private RegistroMercantilServicio registroMercantilServicio;
	
	private String usuario;
	private String contrasena;
	private Integer entidad;
	private List<RegistroMercantil> listaRM;
	
	public void validarUsuario() {
		Usuario usuario = new Usuario();
		RegistroMercantil registroMercantil = new RegistroMercantil();
		usuario = usuarioServicio.validarUsuario(getUsuario(), EncriptarCadenas.encriptarCadenaSha1(getContrasena()), getEntidad());
		if(usuario != null) {
			try {
				registroMercantil = registroMercantilServicio.findByPk(getEntidad());
				HttpSession session = getHttpServletRequest().getSession(true);
				session.setAttribute("tipoEntidad", registroMercantil.getTipo());
				session.setAttribute("usuario", getUsuario());
				//if(registroEntidad.getTipo = )
				getFacesContext().getExternalContext().redirect("/paginas/brand.jsf");
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
		if(listaRM == null)
			listaRM = registroMercantilServicio.obtenerRegistrosMercantiles();
		return listaRM;
	}

	public void setListaRM(List<RegistroMercantil> listaRM) {
		this.listaRM = listaRM;
	}
}
