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

import ec.gob.dinardap.turno.modelo.TipoVentanilla;
import ec.gob.dinardap.turno.servicio.TipoVentanillaServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named("tipoVentanillaCtrl")
@ViewScoped
public class TipoVentanillaCtrl extends BaseCtrl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2883575069645268909L;

	@EJB
	private TipoVentanillaServicio tipoVentanillaServicio;
	
	private List<TipoVentanilla> listaTipoVentanilla;
	private List<TipoVentanilla> filtro;
	private List<SelectItem> estado;
	private TipoVentanilla tipoVentanilla;
	private Boolean nuevoTipoVentanilla;
	
	@PostConstruct
	protected void init() {
		limpiarCampos();
	}
	
	public void tipoVentanillaSeleccionada() {
		nuevoTipoVentanilla = Boolean.TRUE;
	}
	
	public void nuevaVentanilla() {
		limpiarCampos();
		nuevoTipoVentanilla = Boolean.TRUE;
	}
	
	public void guardar() {
		tipoVentanillaServicio.guardarTipoVentanilla(getTipoVentanilla());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", getBundleMensaje("registro.guardado", null)));
		limpiarCampos();
	}
	
	public String obtenerEstado(Short estado) {
		return EstadoEnum.obtenerEstadoPorCodigo(estado).name();
	}
	
	public void limpiarCampos() {
		tipoVentanilla = new TipoVentanilla();
		nuevoTipoVentanilla = Boolean.FALSE;
	}
	

	public List<TipoVentanilla> getListaTipoVentanilla() {
		listaTipoVentanilla = tipoVentanillaServicio.findAll();
		return listaTipoVentanilla;
	}
	public void setListaTipoVentanilla(List<TipoVentanilla> listaTipoVentanilla) {
		this.listaTipoVentanilla = listaTipoVentanilla;
	}
	public List<TipoVentanilla> getFiltro() {
		return filtro;
	}
	public void setFiltro(List<TipoVentanilla> filtro) {
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
	public TipoVentanilla getTipoVentanilla() {
		return tipoVentanilla;
	}
	public void setTipoVentanilla(TipoVentanilla tipoVentanilla) {
		this.tipoVentanilla = tipoVentanilla;
	}
	public Boolean getNuevoTipoVentanilla() {
		return nuevoTipoVentanilla;
	}
	public void setNuevoTipoVentanilla(Boolean nuevoTipoVentanilla) {
		this.nuevoTipoVentanilla = nuevoTipoVentanilla;
	}

}
