package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.modelo.TipoAtencion;
import ec.gob.dinardap.turno.servicio.TipoAtencionServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named("tipoAtencionCtrl")
@ViewScoped
public class TipoAtencionCtrl extends BaseCtrl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2883575069645268909L;

	@EJB
	private TipoAtencionServicio tipoAtencionServicio;
	
	private List<TipoAtencion> listaTipoAtencion;
	private List<TipoAtencion> filtro;
	private List<SelectItem> estado;
	private TipoAtencion tipoAtencion;
	private Boolean nuevoTipoAtencion;
	
	@PostConstruct
	protected void init() {
		limpiarCampos();
	}
	
	public void tipoAtencionSeleccionada() {
		nuevoTipoAtencion = Boolean.TRUE;
	}
	
	public void nuevaAtencion() {
		limpiarCampos();
		nuevoTipoAtencion = Boolean.TRUE;
	}
	
	public void guardar() {
		tipoAtencionServicio.guardarTipoAtencion(getTipoAtencion());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", getBundleMensaje("registro.guardado", null)));
		limpiarCampos();
	}
	
	public String obtenerEstado(Short estado) {
		return EstadoEnum.obtenerEstadoPorCodigo(estado).name();
	}
	
	public void limpiarCampos() {
		tipoAtencion = new TipoAtencion();
		nuevoTipoAtencion = Boolean.FALSE;
	}
	

	public List<TipoAtencion> getListaTipoAtencion() {
		listaTipoAtencion = tipoAtencionServicio.findAll();
		return listaTipoAtencion;
	}
	public void setListaTipoAtencion(List<TipoAtencion> listaTipoAtencion) {
		this.listaTipoAtencion = listaTipoAtencion;
	}
	public List<TipoAtencion> getFiltro() {
		return filtro;
	}
	public void setFiltro(List<TipoAtencion> filtro) {
		this.filtro = filtro;
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
	public TipoAtencion getTipoAtencion() {
		return tipoAtencion;
	}
	public void setTipoAtencion(TipoAtencion tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}
	public Boolean getNuevoTipoAtencion() {
		return nuevoTipoAtencion;
	}
	public void setNuevoTipoAtencion(Boolean nuevoTipoAtencion) {
		this.nuevoTipoAtencion = nuevoTipoAtencion;
	}

}
