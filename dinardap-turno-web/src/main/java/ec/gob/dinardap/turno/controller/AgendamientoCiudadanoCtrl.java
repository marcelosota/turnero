package ec.gob.dinardap.turno.controller;

import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;
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

@Named(value = "agendamientoCiudadanoCtrl")
@ViewScoped
public class AgendamientoCiudadanoCtrl extends BaseCtrl implements Serializable {

    //Declaración de variables
    //Variables de control visual
    private String tituloPagina;

    private String fechaMin;

    private Boolean renderHorarios;

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
    private HorarioDTO horarioDTOSelected;
    //    private Date fechaSeleccionada;
    //    private Integer año;
    //    private Integer mes;
    //    private RemanenteMensual remanenteMensualSelected;
    //
    //    private Nomina nominaSelected;
    //    private FacturaPagada facturaPagadaSelected;
    //Listas    
    private List<RegistroMercantil> registroMercantilList;
    private List<HorarioDTO> horarioDTOList;
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
        renderHorarios = Boolean.FALSE;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fechaMin = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

        turno = new Turno();
        horarioDTOSelected = new HorarioDTO();
        registroMercantilList = new ArrayList<RegistroMercantil>();
        registroMercantilList = registroMercantilServicio.getRegistrosMercantiles();
        horarioDTOList = new ArrayList<HorarioDTO>();
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
        PlanificacionRegistro planificacionRegistro = null;
        planificacionRegistro = planificacionRegistroServicio.getPlanificacionRegistro(turno.getRegistroMercantil().getRegistroMercantilId());
        String nombreCiudadano = "Chris";//Añadir el metodo getNombreCiudadano para consumir el ws de Jady
//        String nombreCiudadano = getNombreCiudadano();
        if (planificacionRegistro.getPlanificacionId() != null) {
            renderHorarios = Boolean.TRUE;
            if (nombreCiudadano != null) {
                //Creación del array de Horarios
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El Registro Mercantil seleccionado no cuenta con una planificación", "El Registro Mercantil seleccionado no cuenta con planificación"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El Registro Mercantil seleccionado no cuenta con una planificación", "El Registro Mercantil seleccionado no cuenta con planificación"));
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

    private List<HorarioDTO> generacionListadoHorario(PlanificacionRegistro pr) {

        return null;
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

    public Boolean getRenderHorarios() {
        return renderHorarios;
    }

    public void setRenderHorarios(Boolean renderHorarios) {
        this.renderHorarios = renderHorarios;
    }

    public List<HorarioDTO> getHorarioDTOList() {
        return horarioDTOList;
    }

    public void setHorarioDTOList(List<HorarioDTO> horarioDTOList) {
        this.horarioDTOList = horarioDTOList;
    }

    public HorarioDTO getHorarioDTOSelected() {
        return horarioDTOSelected;
    }

    public void setHorarioDTOSelected(HorarioDTO horarioDTOSelected) {
        this.horarioDTOSelected = horarioDTOSelected;
    }

}
