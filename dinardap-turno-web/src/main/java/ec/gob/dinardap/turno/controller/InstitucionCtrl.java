package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.constante.PeriodoEnum;
import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named("institucionCtrl")
@ViewScoped
public class InstitucionCtrl extends BaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2089357220072772866L;

	@EJB
	private RegistroMercantilServicio registroMercantilServicio;
	
	private List<RegistroMercantil> listaInstitucion;
	private List<RegistroMercantil> filtro;
	private List<SelectItem> estado;
	private List<SelectItem> tipoInstitucion;
	private List<SelectItem> periodo;
	private RegistroMercantil institucion;
	private Boolean nuevaInstitucion;
	
	protected void init() {
		nuevaInstitucion = Boolean.FALSE;
	}
	
	public void registrarNuevaInstitucion() {
		nuevaInstitucion = Boolean.TRUE;
		limpiarCampos();
	}
	
	public void guardar() {
		registroMercantilServicio.guardarInstitucion(institucion);
		addInfoMessage(getBundleMensaje("registro.guardado", null), null);
		limpiarCampos();
	}
	
	public void limpiarCampos() {
		institucion = new RegistroMercantil();
		nuevaInstitucion = Boolean.FALSE;
	}
	
	public void institucionSeleccted() {
		nuevaInstitucion = Boolean.TRUE;
	}
	
	public String obtenerEstado(Short estado) {
		return EstadoEnum.obtenerEstadoPorCodigo(estado).name();
	}
	
	public String obtenerTipoInstitucion(Short estado) {
		return TipoEntidadEnum.obtenerTipoPorCodigo(estado).name();
	}

	public List<RegistroMercantil> getListaInstitucion() {
		listaInstitucion = registroMercantilServicio.getRegistrosMercantiles();
		return listaInstitucion;
	}

	public void setListaInstitucion(List<RegistroMercantil> listaInstitucion) {
		this.listaInstitucion = listaInstitucion;
	}

	public List<RegistroMercantil> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<RegistroMercantil> filtro) {
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

	public List<SelectItem> getTipoInstitucion() {
		if(this.tipoInstitucion == null) {
			List<TipoEntidadEnum> lista = new ArrayList<TipoEntidadEnum>(EnumSet.allOf(TipoEntidadEnum.class));
			this.tipoInstitucion = new ArrayList<SelectItem>();
			for(TipoEntidadEnum valor : lista) {
				SelectItem item = new SelectItem(valor.getTipo(), valor.name());
				this.tipoInstitucion.add(item);
			}
		}
		return tipoInstitucion;
	}

	public void setTipoInstitucion(List<SelectItem> tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	public List<SelectItem> getPeriodo() {
		if(this.periodo == null) {
			List<PeriodoEnum> lista = new ArrayList<PeriodoEnum>(EnumSet.allOf(PeriodoEnum.class));
			this.periodo = new ArrayList<SelectItem>();
			for(PeriodoEnum valor : lista) {
				SelectItem item = new SelectItem(valor.getPeriodo(), valor.name());
				this.periodo.add(item);
			}
		}
		return periodo;
	}

	public void setPeriodo(List<SelectItem> periodo) {
		this.periodo = periodo;
	}

	public RegistroMercantil getInstitucion() {
		return institucion;
	}

	public void setInstitucion(RegistroMercantil institucion) {
		this.institucion = institucion;
	}

	public Boolean getNuevaInstitucion() {
		return nuevaInstitucion;
	}

	public void setNuevaInstitucion(Boolean nuevaInstitucion) {
		this.nuevaInstitucion = nuevaInstitucion;
	}

}
