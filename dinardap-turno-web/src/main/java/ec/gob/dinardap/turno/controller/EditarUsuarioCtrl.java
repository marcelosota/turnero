package ec.gob.dinardap.turno.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

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


public class EditarUsuarioCtrl extends BaseCtrl{

	/**
	 * 
	 */
	private static final long serialVersionUID = -770751950500324855L;

	@EJB
	private UsuarioServicio usuarioServicio;
	
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
	
	public void cargarDatos(Integer usuarioId) {
		usuario = usuarioServicio.findByPk(usuarioId);
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
		/*RegistroMercantil rm = registroMercantilServicio.findByPk(getUsuarioDto().getRegistroMercantilId());
		Perfil perfil = new Perfil();
		if(rm.getTipo() == TipoEntidadEnum.DINARDAP.getTipo())
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.DINARDAP.getPerfil());
		else
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.RM.getPerfil());*/
		if(getUsuarioDto().getContrasena() != null && !getUsuarioDto().getContrasena().equals(""))
			getUsuarioDto().setContrasena(EncriptarCadenas.encriptarCadenaSha1(getUsuarioDto().getContrasena()));
		else
			getUsuarioDto().setContrasena(getUsuario().getContrasena());
		//getUsuarioDto().setPerfilId(perfil.getPerfilId());
		usuarioServicio.modificarUsuario(getUsuarioDto());
		addInfoMessage(getBundleMensaje("registro.guardado", null), null);
		limpiarCampos();
	}
	
	public void limpiarCampos() {
		usuarioDto = new UsuarioDto();
		regresar();
	}
	
	public void regresar() {
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
		//if(listaPerfil == null)
		//	listaPerfil = perfilServicio.obtenerPerfilesPorSistema(Integer.parseInt(getIdentificacionSistema()));
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
