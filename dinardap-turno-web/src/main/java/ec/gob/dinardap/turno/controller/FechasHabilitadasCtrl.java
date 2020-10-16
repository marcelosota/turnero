package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.constante.AtencionSuspensionEnum;
import ec.gob.dinardap.turno.dao.AtencionDao;
import ec.gob.dinardap.turno.modelo.Atencion;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.TipoAtencion;
import ec.gob.dinardap.turno.servicio.AtencionServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TipoAtencionServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named(value = "fechasHabilitadasCtrl")
@ViewScoped
public class FechasHabilitadasCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8641855730615163434L;
	
	@EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private AtencionServicio atencionServicio;
    
    @EJB
    private TipoAtencionServicio tipoAtencionServicio;
    
    @EJB
    private AtencionDao atencionDao;

    //Variables de negocio
    private Atencion atencion;
    private RegistroMercantil registroMercantil;
    private List<TipoAtencion> tipoAtencionList;
    private List<Atencion> atencionList;
    private List<Atencion> atencionListSelect;
    private List<Atencion> filtro;
    private List<RegistroMercantil> registroMercantilList;
    private List<SelectItem> estado;
    private List<SelectItem> atencionSuspension;
    private Boolean renderFormulario;
    private Boolean renderNuevo;

    @PostConstruct
    protected void init() {
        limpiarCampos();
        construirListaRegistroMercantil();
        tipoAtencionList = new ArrayList<TipoAtencion>();
        tipoAtencionList = tipoAtencionServicio.obtenerTipoAtencionPorEstado(EstadoEnum.ACTIVO.getEstado());
        renderNuevo = Boolean.TRUE;
    }
    
    private void construirListaRegistroMercantil() {
    	RegistroMercantil rm = new RegistroMercantil();
    	registroMercantilList = new ArrayList<RegistroMercantil>();
    	//registroMercantilList = registroMercantilServicio.getRegistrosMercantiles();
    	rm.setRegistroMercantilId(0);
    	rm.setNombre("TODOS");
        registroMercantilList.add(rm);
        registroMercantilList.addAll(registroMercantilServicio.getRegistrosMercantiles());
    }

    public List<RegistroMercantil> completeNombreRegistroMercantil(String query) {
        List<RegistroMercantil> filteredRegistroMercantil = new ArrayList<RegistroMercantil>();
        for (RegistroMercantil rm : registroMercantilList) {
            if (rm.getNombre().toLowerCase().contains(query)
                    || rm.getNombre().toUpperCase().contains(query)) {
                filteredRegistroMercantil.add(rm);
            }
        }
        return filteredRegistroMercantil;
    }
    
    public void buscarVacaciones() {
    	if(registroMercantil.getRegistroMercantilId() > 0) 
	    	atencionList = atencionServicio.obtenerVacacionesPorIstitucion(registroMercantil.getRegistroMercantilId());
    	else
    		atencionList = atencionDao.obtenerAtencionParaTodaInstitucion();
	    limpiarCampos();
	    if(atencionList != null && atencionList.size() > 0) {
	    	renderFormulario = Boolean.FALSE;
	    }else {
	    	renderFormulario= Boolean.TRUE;
	    }
    	renderNuevo = Boolean.FALSE;
    }

    public void guardarAtencion() {        
    	if (atencion.getAtencionId() != null) {
    		atencion.setFechaModificacion(new Timestamp (new Date().getTime()));
    		atencionServicio.update(atencion);
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", getBundleMensaje("registro.guardado", null)));
    	} else {
    		if(registroMercantil.getRegistroMercantilId() > 0)
    			atencion.setRegistroMercantil(getRegistroMercantil());
    		else
    			atencion.setRegistroMercantil(null);
    		atencion.setAtencionId(null);
    		atencion.setFechaCreacion(new Timestamp (new Date().getTime()));
    		atencionServicio.create(atencion);
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", getBundleMensaje("registro.guardado", null)));
    	}
    	buscarVacaciones();
    }
    
    public void nuevaAtencion() {
    	limpiarCampos();
    	renderFormulario = Boolean.TRUE;
    }
    
   
    public void limpiarCampos() {
    	atencion = new Atencion();
    	atencion.setTipoAtencion(new TipoAtencion());
    	atencion.setRegistroMercantil(new RegistroMercantil());
    	renderFormulario = Boolean.FALSE;
    }
    
    public void onSelectAtencion(){
    	renderFormulario = Boolean.TRUE;
    }
    
    public void validarFechas() {
    	System.out.println("Valido Fechas");
    	if(atencion.getFechaDesde()!= null && atencion.getFechaHasta() != null 
    			&& atencion.getFechaDesde().compareTo(atencion.getFechaHasta()) > 0)
    		atencion.setFechaHasta(null);
    }
    
    public void validarHora() {
    	System.out.println("Valido Horas");
    	if(atencion.getFechaDesde()!= null && atencion.getFechaHasta() != null 
    			&& atencion.getHoraDesde() != null && atencion.getHoraHasta() != null
    			&& atencion.getFechaDesde().compareTo(atencion.getFechaHasta()) == 0 
    			&& atencion.getHoraDesde().compareTo(atencion.getHoraHasta()) > 0)
    		atencion.setHoraHasta(null);
    }
    
    public String obtenerEstado(Short estado) {
		return EstadoEnum.obtenerEstadoPorCodigo(estado).name();
	}
    
    public String obtenerAtencionSuspension(Short atencionSuspension) {
		return AtencionSuspensionEnum.obtenerAtencionSuspensionPorCodigo(atencionSuspension).name();
	}

    //Getters & Setters
    public List<RegistroMercantil> getRegistroMercantilList() {
        return registroMercantilList;
    }

    public void setRegistroMercantilList(List<RegistroMercantil> registroMercantilList) {
        this.registroMercantilList = registroMercantilList;
    }

    public RegistroMercantil getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

    public Atencion getAtencion() {
		return atencion;
	}

	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	public List<TipoAtencion> getTipoAtencionList() {
		return tipoAtencionList;
	}

	public void setTipoAtencionList(List<TipoAtencion> tipoAtencionList) {
		this.tipoAtencionList = tipoAtencionList;
	}

	public List<Atencion> getAtencionList() {
		return atencionList;
	}

	public void setAtencionList(List<Atencion> atencionList) {
		this.atencionList = atencionList;
	}

	public List<Atencion> getAtencionListSelect() {
		return atencionListSelect;
	}

	public void setAtencionListSelect(List<Atencion> atencionListSelect) {
		this.atencionListSelect = atencionListSelect;
	}

	public List<Atencion> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<Atencion> filtro) {
		this.filtro = filtro;
	}

	public Boolean getRenderFormulario() {
		return renderFormulario;
	}

	public void setRenderFormulario(Boolean renderFormulario) {
		this.renderFormulario = renderFormulario;
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
	
	public List<SelectItem> getAtencionSuspension() {
		if(this.atencionSuspension == null) {
			List<AtencionSuspensionEnum> lista = new ArrayList<AtencionSuspensionEnum>(EnumSet.allOf(AtencionSuspensionEnum.class));
			this.atencionSuspension = new ArrayList<SelectItem>();
			for(AtencionSuspensionEnum valor : lista) {
				SelectItem item = new SelectItem(valor.getatencionSuspension(), valor.name());
				this.atencionSuspension.add(item);
			}
		}
		return atencionSuspension;
	}

	public void setAtencionSuspension(List<SelectItem> atencionSuspension) {
		this.atencionSuspension = atencionSuspension;
	}

	public Boolean getRenderNuevo() {
		return renderNuevo;
	}

	public void setRenderNuevo(Boolean renderTabla) {
		this.renderNuevo = renderTabla;
	}

}
