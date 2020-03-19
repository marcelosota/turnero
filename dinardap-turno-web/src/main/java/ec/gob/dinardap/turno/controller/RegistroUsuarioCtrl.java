package ec.gob.dinardap.turno.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.autorizacion.util.EncriptarCadenas;
import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;
import ec.gob.dinardap.seguridad.modelo.Perfil;
import ec.gob.dinardap.seguridad.servicio.PerfilServicio;
import ec.gob.dinardap.turno.constante.PerfilTurnoEnum;
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
	
	@PostConstruct
	protected void init() {
		usuarioDto = new UsuarioDto();
	}
	
	public void buscarUsuario() {
		ServicioDINARDAP ob = new ServicioDINARDAP();
		ConsultarResponse objWs;
		objWs = ob.obtenerDatosInteroperabilidad(getUsuarioDto().getCedula(), "2639");
		if (objWs != null) {
			getUsuarioDto().setNombre( objWs.getPaquete().getEntidades().getEntidad().get(0).getFilas().getFila().get(0)
					.getColumnas().getColumna().get(3).getValor());
		}
	}
	
	public void guardarUsuario() {
		RegistroMercantil rm = registroMercantilServicio.findByPk(getUsuarioDto().getRegistroMercantilId());
		Perfil perfil = new Perfil();
		if(rm.getTipo() == TipoEntidadEnum.DINARDAP.getTipo())
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.DINARDAP.getPerfil());
		else
			perfil = perfilServicio.obtenerPorNombre(PerfilTurnoEnum.RM.getPerfil());
		getUsuarioDto().setContrasena(EncriptarCadenas.encriptarCadenaSha1(getUsuarioDto().getContrasena()));
		getUsuarioDto().setPerfilId(perfil.getPerfilId());
		usuarioServicio.crearUsuario(getUsuarioDto());
		addInfoMessage(getBundleMensaje("registro.guardado", null), null);
	}
	
	public void limpiarCampos() {
		usuarioDto = new UsuarioDto();
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
	

}
