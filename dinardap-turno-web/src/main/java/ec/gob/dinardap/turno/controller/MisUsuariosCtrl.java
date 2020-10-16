package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.dinardap.autorizacion.util.EncriptarCadenas;
import ec.gob.dinardap.seguridad.constante.TipoPerfilEnum;
import ec.gob.dinardap.seguridad.modelo.Perfil;
import ec.gob.dinardap.seguridad.servicio.PerfilServicio;
import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.UsuarioT;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named(value="misUsuariosCtrl")
@ConversationScoped
public class MisUsuariosCtrl extends BaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338028830589837214L;

	@EJB
	private UsuarioServicio usuarioServicio;
	
	@Inject
	private Conversation conversation;
	
	private List<UsuarioT> listaUsuario;
	private List<UsuarioT> filtro;
	private Integer usuarioId;
	

	
	@EJB
	private RegistroMercantilServicio registroMercantilServicio;
	
	@EJB
	private PerfilServicio perfilServicio;
	
	private UsuarioT usuario;
	private UsuarioDto usuarioDto;
	private List<RegistroMercantil> listaRM;
	private List<SelectItem> estado;
	private List<Perfil> listaPerfil;
	private String perfil;
	
	
	public void initConversation(){
		if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient())
			conversation.begin();
	}
	
	private void endConversation() {
		if(!conversation.isTransient())
			conversation.end();
	}
	 
	public String verUsuario() {
		limpiarCampos();
		return "editarUsuario?faces-redirect=true";
	}
	
	public List<UsuarioT> getListaUsuario() {
		if(listaUsuario == null)
			listaUsuario = usuarioServicio.findAll();
		return listaUsuario;
	}
	public void setListaUsuario(List<UsuarioT> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	public List<UsuarioT> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<UsuarioT> filtro) {
		this.filtro = filtro;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
	
	
	
	/** EditarUsuarioCtrl **/
	public void cargarDatos() {
		if(usuarioDto.getUsuarioId() == null) {
			usuario = usuarioServicio.findByPk(getUsuarioId());
			usuarioDto = new UsuarioDto();
			getUsuarioDto().setUsuarioId(getUsuario().getUsuarioId());
			getUsuarioDto().setCedula(getUsuario().getCedula());
			//getUsuarioDto().setContrasena(getUsuario().getContrasena());
			getUsuarioDto().setNombre(getUsuario().getNombre());
			getUsuarioDto().setRegistroMercantilId(getUsuario().getRegistroMercantil().getRegistroMercantilId());
			getUsuarioDto().setPerfilId(getUsuario().getPerfil().getPerfilId());
			getUsuarioDto().setEstado(getUsuario().getEstado());
			setPerfil(getUsuario().getPerfil().getNombre());
			buscarPerfil();
		}
	}
	
	public void buscarPerfil() {
		if(listaPerfil != null)
			listaPerfil.clear();
		RegistroMercantil rm = registroMercantilServicio.findByPk(getUsuarioDto().getRegistroMercantilId());
		if(rm.getTipo() == TipoEntidadEnum.DINARDAP.getTipo())
			listaPerfil = perfilServicio.obtenerPerfilesPorSistemaTipoPerfil(Integer.parseInt(getIdentificacionSistema()), TipoPerfilEnum.INTERNO.getTipoPerfil());
		else
			listaPerfil = perfilServicio.obtenerPerfilesPorSistemaTipoPerfil(Integer.parseInt(getIdentificacionSistema()), TipoPerfilEnum.EXTERNO.getTipoPerfil());
	}
	
	public void guardarUsuario() {
		if(getUsuarioDto().getContrasena() != null && !getUsuarioDto().getContrasena().equals(""))
			getUsuarioDto().setContrasena(EncriptarCadenas.encriptarCadenaSha1(getUsuarioDto().getContrasena()));
		else
			getUsuarioDto().setContrasena(getUsuario().getContrasena());
		//getUsuarioDto().setPerfilId(perfil.getPerfilId());
		usuarioServicio.modificarUsuario(getUsuarioDto());
		addInfoMessage(getBundleMensaje("registro.guardado", null), null);
		regresar();
		
	}
	
	public void limpiarCampos() {
		usuarioDto = new UsuarioDto();
	}
	
	public void regresar() {
		limpiarCampos();
		endConversation();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("misUsuarios.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UsuarioT getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioT usuario) {
		this.usuario = usuario;
	}

	public UsuarioDto getUsuarioDto() {
		System.out.println("Registro Mercantil: "+usuarioDto.getRegistroMercantilId());
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public List<RegistroMercantil> getListaRM() {
		if(listaRM == null) 
			listaRM = registroMercantilServicio.findAll();
		return listaRM;
	}

	public void setListaRM(List<RegistroMercantil> listaRM) {
		this.listaRM = listaRM;
	}

	public List<SelectItem> getEstado() {
		if(this.estado == null) {
			List<EstadoEnum> lista = new ArrayList<EstadoEnum>(EnumSet.allOf(EstadoEnum.class));
			this.estado = new ArrayList<SelectItem>();
			for(EstadoEnum valor : lista) {
				SelectItem item = new SelectItem(valor.getEstado(), valor.name());
				this.estado.add(item);
			}
		}
		return estado;
	}

	public void setEstado(List<SelectItem> estado) {
		this.estado = estado;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
}
