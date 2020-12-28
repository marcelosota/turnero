package ec.gob.dinardap.turno.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.autorizacion.util.EncriptarCadenas;
import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;
import ec.gob.dinardap.seguridad.constante.TipoPerfilEnum;
import ec.gob.dinardap.seguridad.modelo.Perfil;
import ec.gob.dinardap.seguridad.servicio.PerfilServicio;
import ec.gob.dinardap.turno.constante.InteroperabilidadEnum;
import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.dto.UsuarioDto;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;

@Named(value="registroUsuarioCtrl")
@ViewScoped
public class RegistroUsuarioCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6455322718943194443L;
	
	@EJB
	private PerfilServicio perfilServicio;
	
	@EJB
	private UsuarioServicio usuarioServicio;
	
	@EJB
	private RegistroMercantilServicio registroMercantilServicio;
	
	private UsuarioDto usuarioDto;
	private List<RegistroMercantil> listaRM;
	private List<Perfil> listaPerfil;
	private Boolean deshabilitaGuardar;
	
	@PostConstruct
	protected void init() {
		usuarioDto = new UsuarioDto();
		deshabilitaGuardar = Boolean.TRUE;
	}
	
	public void buscarUsuario() {
		if(usuarioServicio.buscarPorCedula(getUsuarioDto().getCedula()) == null) {
			ServicioDINARDAP ob = new ServicioDINARDAP();
			ConsultarResponse objWs;
			objWs = ob.obtenerDatosInteroperabilidad(getUsuarioDto().getCedula(), InteroperabilidadEnum.RC.getPaquete());

			if (objWs != null) {
				getUsuarioDto().setNombre( objWs.getPaquete().getEntidades().getEntidad().get(0).getFilas().getFila().get(0)
						.getColumnas().getColumna().get(3).getValor());
				deshabilitaGuardar = Boolean.FALSE;
			}else {
				addErrorMessage(null, "Error", getBundleMensaje("error.documento.identificacion", null));
				deshabilitaGuardar = Boolean.TRUE;
			}
		}else {
			addErrorMessage(null, getBundleMensaje("usuario.existe", null), null);
			deshabilitaGuardar = Boolean.TRUE;
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
		/*RegistroMercantil rm = registroMercantilServicio.findByPk(getUsuarioDto().getRegistroMercantilId());
		 Perfil perfil = new Perfil();
		 if(rm.getTipo() == TipoEntidadEnum.DINARDAP.getTipo())
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.DINARDAP.getPerfil());
		else
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.RM.getPerfil());
		getUsuarioDto().setPerfilId(perfil.getPerfilId());
		*/
		getUsuarioDto().setContrasena(EncriptarCadenas.encriptarCadenaSha1(getUsuarioDto().getContrasena()));
		usuarioServicio.crearUsuario(getUsuarioDto());
		addInfoMessage(getBundleMensaje("registro.guardado", null), null);
		limpiarCampos();
	}
	
	public void limpiarCampos() {
		usuarioDto = new UsuarioDto();
		deshabilitaGuardar = Boolean.TRUE;
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

	public List<Perfil> getListaPerfil() {
		//if(listaPerfil == null)
		//	listaPerfil = perfilServicio.obtenerPerfilesPorSistema(Integer.parseInt(getIdentificacionSistema()));
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public Boolean getDeshabilitaGuardar() {
		return deshabilitaGuardar;
	}

	public void setDeshabilitaGuardar(Boolean deshabilitaGuardar) {
		this.deshabilitaGuardar = deshabilitaGuardar;
	}
	

}
