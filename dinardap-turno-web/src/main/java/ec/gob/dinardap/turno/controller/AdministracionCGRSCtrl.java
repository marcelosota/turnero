package ec.gob.dinardap.turno.controller;

import ec.gob.dinardap.turno.dto.HorarioDTO;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
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

@Named(value = "administracionCGRSCtrl")
@ViewScoped
public class AdministracionCGRSCtrl extends BaseCtrl implements Serializable {

    //Declaración de variables
    //Variables de control visual
    private String tituloPagina;

    private Date horaMinInicio;
    private Date horaMaxInicio;
    private Date horaMinFin;
    private Date horaMaxFin;

    //Variables de negocio
    private PlanificacionRegistro planificacionRegistro, planificacionExistente;
    private RegistroMercantil registroMercantil;
    private int ventanillas;
    private int tiempoAtencion;
    private int horaInicio;
    private int horaFin;

    //Listas    
    private List<RegistroMercantil> registroMercantilList;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;

    @PostConstruct
    protected void init() {
        tituloPagina = "Administración CGRS";

        Calendar hi = Calendar.getInstance();
        hi.setTime(new Date());
        hi.set(Calendar.HOUR_OF_DAY, 7);
        hi.set(Calendar.MINUTE, 0);
        hi.set(Calendar.SECOND, 0);
        hi.set(Calendar.MILLISECOND, 0);
        horaMinInicio = hi.getTime();
        horaMinInicio = new Date();
        hi.set(Calendar.HOUR_OF_DAY, 9);
        hi.set(Calendar.MINUTE, 30);
        horaMaxInicio = hi.getTime();

        Calendar hf = Calendar.getInstance();
        hf.setTime(new Date());
        hf.set(Calendar.HOUR_OF_DAY, 16);
        hf.set(Calendar.MINUTE, 0);
        hf.set(Calendar.SECOND, 0);
        hf.set(Calendar.MILLISECOND, 0);
        horaMinFin = hf.getTime();
        hf.set(Calendar.HOUR_OF_DAY, 18);
        hf.set(Calendar.MINUTE, 30);
        horaMaxFin = hf.getTime();

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

    public void buscarPlanificacion() {
        RegistroMercantil registroMercantilAux = new RegistroMercantil();
        registroMercantilAux = planificacionRegistro.getRegistroMercantil();
        planificacionExistente = new PlanificacionRegistro();
        planificacionExistente = planificacionRegistroServicio.getPlanificacionRegistro(planificacionRegistro.getRegistroMercantil().getRegistroMercantilId());
        if (planificacionExistente.getPlanificacionId() != null) {
            planificacionRegistro = planificacionExistente;
        } else {
            planificacionRegistro = new PlanificacionRegistro();
            planificacionRegistro.setRegistroMercantil(registroMercantilAux);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Planificación no Registrada ", ""));
        }
    }

    public void guardarPlanificacion() {        
        if (planificacionRegistro.getPlanificacionId() != null) {
            planificacionRegistroServicio.update(planificacionRegistro);
        } else {
            planificacionRegistro.setPlanificacionId(null);
            planificacionRegistroServicio.create(planificacionRegistro);
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

    public Date getHoraMinInicio() {
        return horaMinInicio;
    }

    public void setHoraMinInicio(Date horaMinInicio) {
        this.horaMinInicio = horaMinInicio;
    }

    public Date getHoraMaxInicio() {
        return horaMaxInicio;
    }

    public void setHoraMaxInicio(Date horaMaxInicio) {
        this.horaMaxInicio = horaMaxInicio;
    }

    public Date getHoraMinFin() {
        return horaMinFin;
    }

    public void setHoraMinFin(Date horaMinFin) {
        this.horaMinFin = horaMinFin;
    }

    public Date getHoraMaxFin() {
        return horaMaxFin;
    }

    public void setHoraMaxFin(Date horaMaxFin) {
        this.horaMaxFin = horaMaxFin;
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

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public RegistroMercantil getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(RegistroMercantil registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

}
