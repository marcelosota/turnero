package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
	
	private String cedula;
	private String contrasena;
	private Integer entidad;
	private List<RegistroMercantil> listaRM;
	private String nuevaContrasena;
	private Boolean dialogo;
	
	@PostConstruct
	public void init() {
		setDialogo(Boolean.FALSE);
	}
	
	public void  validarUsuario() {
		Usuario usuario = new Usuario();
		RegistroMercantil registroMercantil = new RegistroMercantil();
		usuario = usuarioServicio.validarUsuario(getCedula(), EncriptarCadenas.encriptarCadenaSha1(getContrasena()), getEntidad());
		if(usuario != null) {
			registroMercantil = registroMercantilServicio.findByPk(getEntidad());
			HttpSession session = getHttpServletRequest().getSession(true);
			session.setAttribute("tipoEntidad", registroMercantil.getTipo());
			session.setAttribute("usuario", usuario.getCedula());
			session.setAttribute("perfil", usuario.getPerfil().getNombre());
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				limpiarCampos();
				context.redirect(context.getRequestContextPath() + "/paginas/brand.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			addErrorMessage(null, getBundleMensaje("error.credenciales", null), null);
	}
	
	public void abrirDialogo() {
		limpiarCampos();
		setDialogo(Boolean.TRUE);
	}
	
	public void cambiarContrasena() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			//limpiarCampos();
			context.redirect(context.getRequestContextPath() + "/publico/cambiarContrasena.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		limpiarCampos();
		setDialogo(Boolean.FALSE);
	}
	
	private void limpiarCampos() {
		setNuevaContrasena("");
		setContrasena("");
		setCedula("");
		setEntidad(null);
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

	public Integer getEntidad() {
		return entidad;
	}

	public void setEntidad(Integer entidad) {
		this.entidad = entidad;
	}

	public List<RegistroMercantil> getListaRM() {
		if(listaRM == null)
			listaRM = registroMercantilServicio.findAll();
		return listaRM;
	}

	public void setListaRM(List<RegistroMercantil> listaRM) {
		this.listaRM = listaRM;
	}

	public String getNuevaContrasena() {
		return nuevaContrasena;
	}

	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}

	public Boolean getDialogo() {
		return dialogo;
	}

	public void setDialogo(Boolean dialogo) {
		this.dialogo = dialogo;
	}
}
