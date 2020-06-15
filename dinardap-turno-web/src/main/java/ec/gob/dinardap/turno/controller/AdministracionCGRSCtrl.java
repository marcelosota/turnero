package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;

@Named(value = "administracionCGRSCtrl")
@ViewScoped
public class AdministracionCGRSCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8641855730615163434L;

	//Declaración de variables
    //Variables de control visual
    private String tituloPagina;

    //Variables de negocio
    private PlanificacionRegistro planificacionRegistro, planificacionExistente;
    private RegistroMercantil registroMercantil;
    private int ventanillas;
    private int tiempoAtencion;
    private String horaInicio;
    private String horaFin;

    private Date fechaHoraInicio;
    private Date fechaHoraFin;

    //Listas    
    private List<RegistroMercantil> registroMercantilList;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;

    @PostConstruct
    protected void init() {
        tituloPagina = "Administración CGRS";
        fechaHoraInicio = null;
        planificacionRegistro = new PlanificacionRegistro();
        registroMercantil = new RegistroMercantil();
        registroMercantilList = new ArrayList<RegistroMercantil>();
        registroMercantilList = registroMercantilServicio.getRegistrosMercantiles();
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
        fechaHoraInicio = null;
        fechaHoraFin = null;
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
        //horaInicio = null;
        //horaFin = null;
        fechaHoraInicio = null;
        fechaHoraFin = null;
        RegistroMercantil registroMercantilAux = new RegistroMercantil();
        registroMercantilAux = planificacionRegistro.getRegistroMercantil();
        planificacionExistente = new PlanificacionRegistro();
        planificacionExistente = planificacionRegistroServicio.getPlanificacionRegistro(planificacionRegistro.getRegistroMercantil().getRegistroMercantilId());
        if (planificacionExistente.getPlanificacionId() != null) {
            planificacionRegistro = planificacionExistente;
        } else {
            planificacionRegistro = new PlanificacionRegistro();
            planificacionRegistro.setRegistroMercantil(registroMercantilAux);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Planificación no Registrada ", ""));
        }
    }

    public void guardarPlanificacion() {        
        if ( validarVentanillas() && validarTiempoAtencion() && validarHoras()) {
            if (planificacionRegistro.getPlanificacionId() != null) {
                planificacionRegistroServicio.update(planificacionRegistro);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Se actualizó la planificación exitosamente. ", ""));
            } else {
                planificacionRegistro.setPlanificacionId(null);
                planificacionRegistroServicio.create(planificacionRegistro);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Se ha registrado la planificación exitosamente.", ""));
            }
        }
    }

    //Getters & Setters
    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

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

    public PlanificacionRegistro getPlanificacionExistente() {
        return planificacionExistente;
    }

    public void setPlanificacionExistente(PlanificacionRegistro planificacionExistente) {
        this.planificacionExistente = planificacionExistente;
    }

    /*
    public RegistroMercantil getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }
     */
    public int getVentanillas() {
        return ventanillas;
    }

    public void setVentanillas(int ventanillas) {
        this.ventanillas = ventanillas;
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(int tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public RegistroMercantil getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

}
