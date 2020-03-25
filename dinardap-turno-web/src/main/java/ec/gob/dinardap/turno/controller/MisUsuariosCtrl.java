package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.gob.dinardap.turno.modelo.Usuario;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;

@Named(value="misUsuariosCtrl")
@SessionScoped
public class MisUsuariosCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338028830589837214L;

	@EJB
	private UsuarioServicio usuarioServicio;
	
	private List<Usuario> listaUsuario;
	private List<Usuario> filtro;
	private Integer usuarioId;
	
	public void verUsuario() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editarUsuario.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Usuario> getListaUsuario() {
		if(listaUsuario == null)
			listaUsuario = usuarioServicio.findAll();
		return listaUsuario;
	}
	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	public List<Usuario> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<Usuario> filtro) {
		this.filtro = filtro;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
}
