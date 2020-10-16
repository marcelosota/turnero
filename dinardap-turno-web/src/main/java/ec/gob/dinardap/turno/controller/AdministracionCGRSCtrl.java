package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.TipoVentanilla;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TipoVentanillaServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Named(value = "administracionCGRSCtrl")
@ViewScoped
public class AdministracionCGRSCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8641855730615163434L;
	
	@EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;
    
    @EJB
    private TipoVentanillaServicio tipoVentanillaServicio;

    //Variables de negocio
    private PlanificacionRegistro planificacionRegistro;
    private RegistroMercantil registroMercantil;
    private List<TipoVentanilla> tipoVentanillaList;
    private List<PlanificacionRegistro> planificacionList;
    private List<PlanificacionRegistro> planificacionListSelect;
    private List<PlanificacionRegistro> filtro;
    private List<RegistroMercantil> registroMercantilList;
    private List<SelectItem> estado;
    private Boolean renderPlanificacion;
    private Boolean renderNuevo;

    @PostConstruct
    protected void init() {
        limpiarCampos();
        registroMercantilList = new ArrayList<RegistroMercantil>();
        registroMercantilList = registroMercantilServicio.getRegistrosMercantiles();
        tipoVentanillaList = new ArrayList<TipoVentanilla>();
        tipoVentanillaList = tipoVentanillaServicio.obtenerTipoVentanillaPorEstado(EstadoEnum.ACTIVO.getEstado());
        renderNuevo = Boolean.TRUE;
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
    
    private Boolean validarVentanillas(){
        if(planificacionRegistro.getVentanilla().equals((short)0)){        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe existir al menos 1 Ventanilla disponible ", ""));
            return false;
        }        
        return true;
    }
    
    private Boolean validarTiempoAtencion(){
        if(planificacionRegistro.getDuracionTramite().equals((short)0)){        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El Tiempo de Atención debe ser al menos 1 minuto ", ""));
            return false;
        }        
        return true;
    }

    private Boolean validarHoras() {        
        Date fechaHoraInicio = null;
        Date fechaHoraFin = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(planificacionRegistro.getHoraInicio().split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(planificacionRegistro.getHoraInicio().split(":")[1]));
        fechaHoraInicio = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(planificacionRegistro.getHoraFin().split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(planificacionRegistro.getHoraFin().split(":")[1]));
        fechaHoraFin = calendar.getTime();
        if (!fechaHoraInicio.before(fechaHoraFin)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: La Hora de Salida no puede ser menor o igual a la Hora de Entrada ", ""));
            return false;
        }
        return true;
    }

    public void buscarPlanificacion() {
    	planificacionList = planificacionRegistroServicio.getPlanificacionRegistroList(registroMercantil.getRegistroMercantilId());
    	limpiarCampos();
    	if(planificacionList != null && planificacionList.size() > 0) {
    		renderPlanificacion = Boolean.FALSE;
    	}else {
    		renderPlanificacion = Boolean.TRUE;
    	}
    	renderNuevo = Boolean.FALSE;
    }

    public void guardarPlanificacion() {        
        if ( validarVentanillas() && validarTiempoAtencion() && validarHoras()) {
            if (planificacionRegistro.getPlanificacionId() != null) {
            	planificacionRegistro.setFechaModificacion(new Timestamp (new Date().getTime()));
                planificacionRegistroServicio.update(planificacionRegistro);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Se actualizó la planificación exitosamente. ", ""));
            } else {
                planificacionRegistro.setPlanificacionId(null);
                planificacionRegistro.setRegistroMercantil(getRegistroMercantil());
                planificacionRegistro.setFechaCreacion(new Timestamp (new Date().getTime()));
                planificacionRegistroServicio.create(planificacionRegistro);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Se ha registrado la planificación exitosamente.", ""));
            }
            buscarPlanificacion();
        }
    }
    
    public void nuevaPlanificacion() {
    	limpiarCampos();
    	renderPlanificacion = Boolean.TRUE;
    }
    
   
    public void limpiarCampos() {
    	planificacionRegistro = new PlanificacionRegistro();
    	planificacionRegistro.setTipoVentanilla(new TipoVentanilla());
    	planificacionRegistro.setRegistroMercantil(new RegistroMercantil());
    	renderPlanificacion = Boolean.FALSE;
    }
    
    public void validarFechas() {
    	if(planificacionRegistro.getFechaVigencia().compareTo(planificacionRegistro.getFechaCaducidad()) < 0)
    		planificacionRegistro.setFechaCaducidad(null);
    }
    
    public void onSelectPlanificacion(){
    	renderPlanificacion = Boolean.TRUE;
    }
    
    public String obtenerEstado(Short estado) {
		return EstadoEnum.obtenerEstadoPorCodigo(estado).name();
	}

    //Getters & Setters
    public PlanificacionRegistro getPlanificacionRegistro() {
        return planificacionRegistro;
    }

    public void setPlanificacionRegistro(PlanificacionRegistro planificacionRegistro) {
        this.planificacionRegistro = planificacionRegistro;
    }

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

	public List<TipoVentanilla> getTipoVentanillaList() {
		return tipoVentanillaList;
	}

	public void setTipoVentanillaList(List<TipoVentanilla> tipoVentanillaList) {
		this.tipoVentanillaList = tipoVentanillaList;
	}

	public List<PlanificacionRegistro> getPlanificacionList() {
		return planificacionList;
	}

	public void setPlanificacionList(List<PlanificacionRegistro> planificacionList) {
		this.planificacionList = planificacionList;
	}

	public List<PlanificacionRegistro> getPlanificacionListSelect() {
		return planificacionListSelect;
	}

	public void setPlanificacionListSelect(List<PlanificacionRegistro> planificacionListSelect) {
		this.planificacionListSelect = planificacionListSelect;
	}

	public List<PlanificacionRegistro> getFiltro() {
		return filtro;
	}

	public void setFiltro(List<PlanificacionRegistro> filtro) {
		this.filtro = filtro;
	}

	public Boolean getRenderPlanificacion() {
		return renderPlanificacion;
	}

	public void setRenderPlanificacion(Boolean renderPlanificacion) {
		this.renderPlanificacion = renderPlanificacion;
	}

	public Boolean getRenderNuevo() {
		return renderNuevo;
	}

	public void setRenderNuevo(Boolean renderTabla) {
		this.renderNuevo = renderTabla;
	}

}
