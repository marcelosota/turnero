package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;
import ec.gob.dinardap.turno.dto.HorarioDTO;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TurnoServicio;

@Named(value = "agendamientoCiudadanoCtrl")
@ViewScoped
public class AgendamientoCiudadanoCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4955068063614741302L;
	//Declaración de variables
    //Variables de control visual
    private String tituloPagina;
    private String fechaMin;

    private Boolean renderHorarios;
    private Boolean renderInformacionTurno;
    private Boolean turnoAgendado;
    private Boolean renderCancelacionTurno;
    //Variables de negocio
    private Turno turno;
    private Turno turnoGenerado;
    private HorarioDTO horarioDTOSelected;
    private String codigoIngresado;

    //Listas    
    private List<RegistroMercantil> registroMercantilList;
    private List<HorarioDTO> horarioDTOList;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;

    @EJB
    private TurnoServicio turnoServicio;

    @PostConstruct
    protected void init() {
        tituloPagina = "Agendamiento de Turnos";
        renderHorarios = Boolean.FALSE;
        renderInformacionTurno = Boolean.FALSE;
        turnoAgendado = Boolean.FALSE;
        renderCancelacionTurno = Boolean.FALSE;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fechaMin = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

        turno = new Turno();
        turnoGenerado = new Turno();
        horarioDTOSelected = new HorarioDTO();
        registroMercantilList = new ArrayList<RegistroMercantil>();
        registroMercantilList = registroMercantilServicio.getRegistrosMercantiles();
        horarioDTOList = new ArrayList<HorarioDTO>();
        codigoIngresado = "";
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

    @SuppressWarnings("unused")
	public void buscarDisponibilidad() {
        turnoGenerado = new Turno();
        PlanificacionRegistro planificacionRegistro = null;
        planificacionRegistro = planificacionRegistroServicio.getPlanificacionRegistro(turno.getRegistroMercantil().getRegistroMercantilId());
//        String nombreCiudadano = "Chris";//Añadir el metodo getNombreCiudadano para consumir el ws de Jady
        String nombreCiudadano = getNombreCiudadano();
        horarioDTOList = new ArrayList<HorarioDTO>();
        if (planificacionRegistro.getPlanificacionId() != null) {
            if (nombreCiudadano != null) {
                turno.setNombre(nombreCiudadano);
                Calendar diaTurno = Calendar.getInstance();
                diaTurno.setTime(turno.getDia());
                String dayOfWeek = diaTurno.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
                if (!dayOfWeek.equals("SUNDAY") && !dayOfWeek.equals("SATURDAY")) {
                    horarioDTOList = generacionListadoHorario(planificacionRegistro);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: Turnos no disponibles para fines de semana.", "Advertencia: Turnos nos disponibles para fines de semana."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Cédula inválida.", "Error: Cédula inválida."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El Registro Mercantil seleccionado no cuenta con una planificación", "El Registro Mercantil seleccionado no cuenta con planificación"));
        }
    }

    public void seleccionarHorario() {
        turnoGenerado = new Turno();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Usted a seleccionado el horario de " + horarioDTOSelected.getHora(), ""));
        turno.setHora(horarioDTOSelected.getHora());
    }

    public void agendarHorario() {
        turno.setTurnoId(null);
        turno.setEstado((short) 1);
        turno.setValidador(getGeneracionValidacion());
        PlanificacionRegistro pr = null;
        pr = planificacionRegistroServicio.getPlanificacionRegistro(turno.getRegistroMercantil().getRegistroMercantilId());
        PrimeFaces.current().executeScript("PF('agendarTurnoDlg').hide()");

        Calendar horaActual = Calendar.getInstance();

        Calendar horaTurno = Calendar.getInstance();
        horaTurno.setTime(turno.getDia());

        horaTurno.set(Calendar.HOUR_OF_DAY, Integer.parseInt(turno.getHora().split(":")[0]));
        horaTurno.set(Calendar.MINUTE, Integer.parseInt(turno.getHora().split(":")[1]));
        horaTurno.set(Calendar.SECOND, 0);
        horaTurno.set(Calendar.MILLISECOND, 0);
        if ((turnoServicio.getTurnosDisponibles(pr.getVentanilla().intValue(), turno.getDia(), turno.getHora()) > 0)
                && (horaActual.getTime().before(horaTurno.getTime()))) {
            turnoAgendado = Boolean.TRUE;
            turnoServicio.create(turno);
            turnoGenerado = turno;
            turno = new Turno();
            renderHorarios = Boolean.FALSE;
            renderInformacionTurno = Boolean.FALSE;
        } else {
            turnoAgendado = Boolean.FALSE;
            buscarDisponibilidad();
        }

    }

    public void cancelarTurno() {
        renderCancelacionTurno = Boolean.TRUE;
    }

    public void confirmarCancelacionTurno() {
        if (turno.getValidador().equals(codigoIngresado)) {
            turno.setEstado((short) 2);
            turnoServicio.update(turno);
            renderInformacionTurno = Boolean.FALSE;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: Su turno ha sido anulado exitosamente.", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: Código de Validación Incorrecto. Ingrese el código de validación para anular el turno agendado.", ""));
        }
        renderCancelacionTurno = Boolean.FALSE;
    }

    @SuppressWarnings("unused")
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
        List<HorarioDTO> horarioList = new ArrayList<>();

        Calendar horaActual = Calendar.getInstance();

        Calendar horaInicio = Calendar.getInstance();
        horaInicio.setTime(turno.getDia());
        Short horaInicial = pr.getHoraInicio();

        horaInicio.set(Calendar.HOUR_OF_DAY, horaInicial);
        horaInicio.set(Calendar.MINUTE, 0);
        horaInicio.set(Calendar.SECOND, 0);
        horaInicio.set(Calendar.MILLISECOND, 0);

        Calendar horaFin = Calendar.getInstance();
        horaFin.setTime(turno.getDia());
        Short horaFinal = pr.getHoraFin();
        horaFin.set(Calendar.HOUR_OF_DAY, horaFinal);
        horaFin.set(Calendar.MINUTE, 0);
        horaFin.set(Calendar.SECOND, 0);
        horaFin.set(Calendar.MILLISECOND, 0);

        if (turnoServicio.validacionDiariaPersona(turno)) {
            renderHorarios = Boolean.TRUE;
            renderInformacionTurno = Boolean.FALSE;
            while (horaInicio.getTime().before(horaFin.getTime())) {
                String hora = new SimpleDateFormat("HH:mm").format(horaInicio.getTime());
                HorarioDTO horario = new HorarioDTO(hora, pr.getVentanilla().intValue(), turnoServicio.getTurnosDisponibles(pr.getVentanilla().intValue(), turno.getDia(), hora));
                if (horaActual.getTime().before(horaInicio.getTime())) {
                    horarioList.add(horario);
                }
                horaInicio.add(Calendar.MINUTE, pr.getDuracionTramite());
            }
        } else {
            renderHorarios = Boolean.FALSE;
            renderInformacionTurno = Boolean.TRUE;
            turno = turnoServicio.getTurno(turno);
        }
        return horarioList;
    }

    public static String getGeneracionValidacion() {
        String str = "DAYSI1234567890";
        String claveGenerada = "";
        int numero;
        for (Integer i = 0; i < 6; i++) {
            numero = (int) (Math.random() * 15);
            claveGenerada = claveGenerada + str.substring(numero, numero + 1);
        }
        return claveGenerada;
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

    public Boolean getRenderInformacionTurno() {
        return renderInformacionTurno;
    }

    public void setRenderInformacionTurno(Boolean renderInformacionTurno) {
        this.renderInformacionTurno = renderInformacionTurno;
    }

    public Turno getTurnoGenerado() {
        return turnoGenerado;
    }

    public void setTurnoGenerado(Turno turnoGenerado) {
        this.turnoGenerado = turnoGenerado;
    }

    public Boolean getTurnoAgendado() {
        return turnoAgendado;
    }

    public void setTurnoAgendado(Boolean turnoAgendado) {
        this.turnoAgendado = turnoAgendado;
    }

    public Boolean getRenderCancelacionTurno() {
        return renderCancelacionTurno;
    }

    public void setRenderCancelacionTurno(Boolean renderCancelacionTurno) {
        this.renderCancelacionTurno = renderCancelacionTurno;
    }

    public String getCodigoIngresado() {
        return codigoIngresado;
    }

    public void setCodigoIngresado(String codigoIngresado) {
        this.codigoIngresado = codigoIngresado;
    }

}
