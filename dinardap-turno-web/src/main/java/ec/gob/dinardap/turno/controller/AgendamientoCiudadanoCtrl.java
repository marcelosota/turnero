package ec.gob.dinardap.turno.controller;

import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;
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

@Named(value = "agendamientoCiudadanoCtrl")
@ViewScoped
public class AgendamientoCiudadanoCtrl extends BaseCtrl implements Serializable {

    //Declaración de variables
    //Variables de control visual
    private String tituloPagina;

    private String fechaMin;

//    private String tituloNomina;
//    private String tituloFacturaPagada;
//    private String strBtnGuardar;
//
//    private Boolean disableNuevoEgreso;
//
//    private Boolean disabledDeleteNomina;
//    private Boolean disabledDeleteNominaTodos;
//
//    private Boolean disabledDeleteFacturaPagada;
//    private Boolean disabledDeleteFacturaPagadaTodos;
//    private Boolean renderEdition;
//
//    private Boolean onCreateNomina;
//    private Boolean onEditNomina;
//
//    private Boolean onCreateFacturaPagada;
//    private Boolean onEditFacturaPagada;
    //Variables de negocio
    private Turno turno;
    //    private Date fechaSeleccionada;
    //    private Integer año;
    //    private Integer mes;
    //    private RemanenteMensual remanenteMensualSelected;
    //
    //    private Nomina nominaSelected;
    //    private FacturaPagada facturaPagadaSelected;
    //Listas    
    private List<RegistroMercantil> registroMercantilList;
//    private List<Nomina> nominaSelectedList;
//    private List<FacturaPagada> facturaPagadaList;
//    private List<FacturaPagada> facturaPagadaSelectedList;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;
//
//    @EJB
//    private TransaccionServicio transaccionServicio;
//
//    @EJB
//    private FacturaPagadaServicio facturaPagadaServicio;
//
//    @EJB
//    private CatalogoTransaccionServicio catalogoTransaccionServicio;
//
//    @EJB
//    private RemanenteMensualServicio remanenteMensualServicio;
//
//    @EJB
//    private DiasNoLaborablesServicio diasNoLaborablesServicio;

    @PostConstruct
    protected void init() {
        tituloPagina = "Agendamiento de Turnos";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fechaMin = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

        turno = new Turno();
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

    public void buscarDisponilidad() {
        System.out.println("Cita: " + turno.getRegistroMercantil().getNombre());
        System.out.println("Cita: " + turno.getCedula());
        System.out.println("Cita: " + turno.getDia());
        System.out.println("Cita: " + turno.getCorreoElectronico());
        System.out.println("Cita: " + turno.getCelular());
        String nombreCiudadano = "Chris";
        if (nombreCiudadano != null) {
            System.out.println("NombreCiudadano");
            PlanificacionRegistro planificacionRegistro = new PlanificacionRegistro();
            planificacionRegistro
                    = planificacionRegistroServicio.getPlanificacionRegistro(turno.getRegistroMercantil().getRegistroMercantilId());
            if (planificacionRegistro != null) {
                System.out.println("Planificacion: " + planificacionRegistro.getPlanificacionId());
                System.out.println("Planificacion: " + planificacionRegistro.getRegistroMercantil().getNombre());
                System.out.println("Planificacion: " + planificacionRegistro.getVentanilla());
                System.out.println("Planificacion: " + planificacionRegistro.getDuracionTramite());
                System.out.println("Planificacion: " + planificacionRegistro.getHoraInicio());
                System.out.println("Planificacion: " + planificacionRegistro.getHoraFin());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Registro Mercantil seleccionado no cuenta con planificación"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cédula inválida"));
        }
    }

    private String getNombreCiudadano() {
        ServicioDINARDAP ob = new ServicioDINARDAP();
        ConsultarResponse objWs = new ConsultarResponse();
        String nombreCiudadano = null;
        Boolean flag = Boolean.FALSE;//False para no activo True para activo el WS;
        if (flag) {
            ob.obtenerDatosInteroperabilidad(turno.getCedula(), "2639");
            if (objWs != null) {
                nombreCiudadano = objWs.getPaquete().getEntidades().getEntidad().get(0).getFilas().getFila().get(0).getColumnas().getColumna().get(3).getValor();
            }
        }
        return nombreCiudadano;
    }

    //Getters & Setters
    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<RegistroMercantil> getRegistroMercantilList() {
        return registroMercantilList;
    }

    public void setRegistroMercantilList(List<RegistroMercantil> registroMercantilList) {
        this.registroMercantilList = registroMercantilList;
    }

    public String getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(String fechaMin) {
        this.fechaMin = fechaMin;
    }

}
