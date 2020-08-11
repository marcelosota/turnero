package ec.gob.dinardap.turno.controller;

import ec.gob.dinardap.correo.mdb.cliente.ClienteQueueMailServicio;
import ec.gob.dinardap.correo.util.MailMessage;
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
import ec.gob.dinardap.seguridad.servicio.ParametroServicio;
import ec.gob.dinardap.turno.constante.EstadoTurnoEnum;
import ec.gob.dinardap.turno.constante.InteroperabilidadEnum;
import ec.gob.dinardap.turno.constante.ParametroEnum;
import ec.gob.dinardap.turno.dto.HorarioDTO;
import ec.gob.dinardap.turno.modelo.Baneo;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.BaneoServicio;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TurnoServicio;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "agendamientoCiudadanoCtrl")
@ViewScoped
public class AgendamientoCiudadanoCtrl extends BaseCtrl implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4955068063614741302L;
    //Declaraci�n de variables
    //Variables de control visual
    private String tituloPagina;
    private String fechaMin;

    private Boolean renderHorarios;
    private Boolean renderInformacionTurno;
    private Boolean turnoAgendado;
    private Boolean renderCancelacionTurno;
    private Boolean renderRemitirTurno;
    //Variables de negocio
    private Turno turno;
    private Turno turnoGenerado;
    private HorarioDTO horarioDTOSelected;
    private String codigoIngresado;
    private String correoIngresado;

    //Listas    
    private List<RegistroMercantil> registroMercantilList;
    private List<HorarioDTO> horarioDTOList;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @EJB
    private PlanificacionRegistroServicio planificacionRegistroServicio;

    @EJB
    private TurnoServicio turnoServicio;

    @EJB
    private ParametroServicio parametroServicio;

    @EJB
    private ClienteQueueMailServicio clienteQueueMailServicio;

    @EJB
    private BaneoServicio baneoServicio;

    @PostConstruct
    protected void init() {
        tituloPagina = "Agendamiento de Turnos";
        renderHorarios = Boolean.FALSE;
        renderInformacionTurno = Boolean.FALSE;
        turnoAgendado = Boolean.FALSE;
        renderCancelacionTurno = Boolean.FALSE;
        renderRemitirTurno = Boolean.FALSE;

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

    public void buscarDisponibilidad() {
        renderCancelacionTurno = Boolean.FALSE;
        renderRemitirTurno = Boolean.FALSE;
        turnoGenerado = new Turno();
        PlanificacionRegistro planificacionRegistro = null;
        planificacionRegistro = planificacionRegistroServicio.getPlanificacionRegistro(turno.getRegistroMercantil().getRegistroMercantilId());
        String nombreCiudadano = "Chris";//Añadir el metodo getNombreCiudadano para consumir el ws de Jady
//        String nombreCiudadano = getNombreCiudadano();
        horarioDTOList = new ArrayList<HorarioDTO>();
        if (planificacionRegistro.getPlanificacionId() != null) {
            if (nombreCiudadano != null) {
                if (!nombreCiudadano.equals("-1")) {
                    turno.setNombre(nombreCiudadano);
                    Calendar diaTurno = Calendar.getInstance();
                    diaTurno.setTime(turno.getDia());
                    String dayOfWeek = diaTurno.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
                    if (!dayOfWeek.equals("SUNDAY") && !dayOfWeek.equals("SATURDAY")) {
                        if (!ban(turno.getCedula(), turno.getCorreoElectronico(), turno.getCelular())) {
                            horarioDTOList = generacionListadoHorario(planificacionRegistro);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Turnos no disponibles para fines de semana."));
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cédula inválida."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Registro Mercantil seleccionado no cuenta con una planificación"));
        }
    }

    private Boolean ban(String identificación, String correoElectronico, String celular) {
        //Falso si no esta ban
        Boolean validacion = Boolean.FALSE;

        List<Baneo> banList = new ArrayList<Baneo>();
        banList = baneoServicio.getBanList(1);
        for (Baneo b : banList) {
            if (b.getValor().equals(identificación)) {
                if (rangoFechas(b.getFechaInicio(), b.getFechaFin())) {
                    validacion = Boolean.TRUE;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Prohibición", getBundleMensaje("prohibicion.cedula", null)));
                    break;
                }
            }
        }

        banList = new ArrayList<Baneo>();
        banList = baneoServicio.getBanList(2);
        for (Baneo b : banList) {
            if (b.getValor().equals(correoElectronico)) {
                if (rangoFechas(b.getFechaInicio(), b.getFechaFin())) {
                    validacion = Boolean.TRUE;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Prohibición", getBundleMensaje("prohibicion.correoElectronico", null)));
                    break;
                }
            }
        }

        banList = new ArrayList<Baneo>();
        banList = baneoServicio.getBanList(3);
        for (Baneo b : banList) {
            if (b.getValor().equals(celular)) {
                if (rangoFechas(b.getFechaInicio(), b.getFechaFin())) {
                    validacion = Boolean.TRUE;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Prohibición", getBundleMensaje("prohibicion.celular", null)));
                    break;
                }
            }
        }

        return validacion;
    }

    private Boolean rangoFechas(Date fechaInicio, Date fechaFin) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaActual = sdf.parse(sdf.format(new Date()));
            fechaFin = fechaFin == null ? new SimpleDateFormat("yyyy-MM-dd").parse("3000-01-01") : fechaFin;
            if ((!fechaInicio.after(fechaActual) && !fechaFin.before(fechaActual))) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (ParseException ex) {
            Logger.getLogger(AgendamientoCiudadanoCtrl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void seleccionarHorario() {
        turnoGenerado = new Turno();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Usted a seleccionado el horario de " + horarioDTOSelected.getHora()));
        turno.setHora(horarioDTOSelected.getHora());
    }

    public void agendarHorario() {
        turno.setTurnoId(null);
        turno.setEstado(EstadoTurnoEnum.AGENDADO.getEstado());
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
        if ((turnoServicio.getTurnosDisponibles(pr.getVentanilla().intValue(), turno.getDia(), turno.getHora(), turno.getRegistroMercantil().getRegistroMercantilId()) > 0)
                && (horaActual.getTime().before(horaTurno.getTime()))) {
            turnoAgendado = Boolean.TRUE;
            turnoServicio.crearTurno(turno);
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
        renderRemitirTurno = Boolean.FALSE;

    }

    public void remitirTurno() {
        renderRemitirTurno = Boolean.TRUE;
        renderCancelacionTurno = Boolean.FALSE;
    }

    public void confirmarCancelacionTurno() {
        if (turno.getValidador().equals(codigoIngresado)) {
            turno.setEstado(EstadoTurnoEnum.CANCELADO.getEstado());
            turnoServicio.actualizarAtendido(turno);
            renderInformacionTurno = Boolean.FALSE;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Su turno ha sido anulado exitosamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Código de Validación Incorrecto. Ingrese el código de validación para anular el turno agendado."));
        }
        renderCancelacionTurno = Boolean.FALSE;
    }

    public void confirmarRemitirTurno() {
        if (turno.getCorreoElectronico().equals(correoIngresado)) {
            MailMessage mailMessage = new MailMessage();
            StringBuilder html = new StringBuilder("<center><h1><B>Sistema para el Agendamiento de Turnos en Registros Mercantiles</B></h1></center><br/><br/>");
            html.append("Estimado(a) " + turno.getNombre() + ", <br /><br />");
            html.append("Le informamos que se ha generado un turno con la siguiente descripción:<br />");
            html.append("<B>REGISTRO MERCANTIL: </B>" + turno.getRegistroMercantil().getNombre() + "<br/>");
            html.append("<B>CÉDULA: </B>" + turno.getCedula() + "<br/>");
            html.append("<B>NOMBRE: </B>" + turno.getNombre() + "<br/>");
            html.append("<B>FECHA: </B>" + new SimpleDateFormat("yyyy-MM-dd").format(turno.getDia()) + "<br/>");
            html.append("<B>HORA: </B>" + turno.getHora() + "<br/>");
            html.append("<B>CÓDIGO VALIDACIÓN: </B>" + turno.getValidador() + "<br/><br/>");

            html.append("<B>NOTA: <B/>Favor guardar el  Código de Validación, ya que este será solicitado en Ventanilla para su atención. "
                    + "Si desea cancelar el turno se deberá ingresar el Código de Validación en la misma Plataforma.<br/>");
            html.append("Gracias por usar nuestros servicios.<br /><br />");
            html.append("<FONT FACE=\"Arial Narrow, sans-serif\"><B> ");
            html.append("Dirección Nacional de Registros de Datos Públicos");
            html.append("</B></FONT>");
            List<String> to = new ArrayList<String>();
            StringBuilder asunto = new StringBuilder(200);
            to.add(turno.getCorreoElectronico());
            asunto.append("Notificación Sistema de Turnos - Información de Turno");
            mailMessage = credencialesCorreo();
            mailMessage.setTo(to);
            mailMessage.setSubject(asunto.toString());
            mailMessage.setText(html.toString());

            clienteQueueMailServicio.encolarMail(mailMessage);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Su código ha sido enviado a " + turno.getCorreoElectronico()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El Correo ingresado no coincide con el Correo registrado en su agendamiento"));
        }
        renderRemitirTurno = Boolean.FALSE;
    }

    private MailMessage credencialesCorreo() {
        MailMessage credenciales = new MailMessage();
        credenciales.setFrom(parametroServicio.findByPk(ParametroEnum.MAIL_TURNERO.name()).getValor());
        credenciales.setUsername(parametroServicio.findByPk(ParametroEnum.MAIL_USERNAME_TURNERO.name()).getValor());
        credenciales.setPassword(parametroServicio.findByPk(ParametroEnum.MAIL_CONTRASENA_TURNERO.name()).getValor());
        return credenciales;
    }

    private String getNombreCiudadano() {
        ServicioDINARDAP ob = new ServicioDINARDAP();
        ConsultarResponse objWs;//= new ConsultarResponse();
        String nombreCiudadano = null;
        try {
            objWs = ob.obtenerDatosInteroperabilidad(turno.getCedula(), InteroperabilidadEnum.RC.getPaquete());
            if (objWs != null) {
                nombreCiudadano = objWs.getPaquete().getEntidades().getEntidad().get(0).getFilas().getFila().get(0).getColumnas().getColumna().get(3).getValor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            nombreCiudadano = "-1";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se ha detectado un inconveniente, por favor intente más tarde."));
        }
        return nombreCiudadano;
    }

    private List<HorarioDTO> generacionListadoHorario(PlanificacionRegistro pr) {
        List<HorarioDTO> horarioList = new ArrayList<>();

        Calendar horaActual = Calendar.getInstance();

        Calendar horaInicio = Calendar.getInstance();
        horaInicio.setTime(turno.getDia());
        String horaInicial = pr.getHoraInicio();

        horaInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaInicial.split(":")[0]));
        horaInicio.set(Calendar.MINUTE, Integer.parseInt(horaInicial.split(":")[1]));
        horaInicio.set(Calendar.SECOND, 0);
        horaInicio.set(Calendar.MILLISECOND, 0);

        Calendar horaFin = Calendar.getInstance();
        horaFin.setTime(turno.getDia());
        String horaFinal = pr.getHoraFin();
        horaFin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaFinal.split(":")[0]));
        horaFin.set(Calendar.MINUTE, Integer.parseInt(horaFinal.split(":")[1]));
        horaFin.set(Calendar.SECOND, 0);
        horaFin.set(Calendar.MILLISECOND, 0);

        if (turnoServicio.validacionDiariaPersona(turno)) {

            renderHorarios = Boolean.TRUE;
            renderInformacionTurno = Boolean.FALSE;
            while (horaInicio.getTime().before(horaFin.getTime())) {
                String hora = new SimpleDateFormat("HH:mm").format(horaInicio.getTime());
                HorarioDTO horario = new HorarioDTO(hora, pr.getVentanilla().intValue(), turnoServicio.getTurnosDisponibles(pr.getVentanilla().intValue(), turno.getDia(), hora, pr.getRegistroMercantil().getRegistroMercantilId()));
                if (horaActual.getTime().before(horaInicio.getTime())) {
                    horarioList.add(horario);
                }
                try {
                    //Validacion eventual para 7 de septiembre
                    Date fechaCambio = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-06");
                    if (turno.getDia().after(fechaCambio)
                            && turno.getRegistroMercantil().getRegistroMercantilId() == 11) {
                        horaInicio.add(Calendar.MINUTE, 5);
                    } else {
                        horaInicio.add(Calendar.MINUTE, pr.getDuracionTramite());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(AgendamientoCiudadanoCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }

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

    public Boolean getRenderRemitirTurno() {
        return renderRemitirTurno;
    }

    public void setRenderRemitirTurno(Boolean renderRemitirTurno) {
        this.renderRemitirTurno = renderRemitirTurno;
    }

    public String getCorreoIngresado() {
        return correoIngresado;
    }

    public void setCorreoIngresado(String correoIngresado) {
        this.correoIngresado = correoIngresado;
    }

}
