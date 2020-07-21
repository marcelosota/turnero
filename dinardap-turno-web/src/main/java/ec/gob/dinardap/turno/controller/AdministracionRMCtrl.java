package ec.gob.dinardap.turno.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import ec.gob.dinardap.turno.constante.EstadoTurnoEnum;
import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.dto.AgendadaAtendidasDto;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.modelo.Usuario;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TurnoServicio;
import ec.gob.dinardap.turno.servicio.UsuarioServicio;
import org.primefaces.PrimeFaces;

@Named(value = "administracionRMCtrl")
@ViewScoped
public class AdministracionRMCtrl extends BaseCtrl {

    private static final long serialVersionUID = 2441633867566660777L;

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;
    @EJB
    private TurnoDao turnoDao;
    @EJB
    private TurnoServicio turnoServicio;
    @EJB
    private UsuarioServicio usuarioServicio;

    private List<RegistroMercantil> registroMercantil;
    private Integer registroMercantilId;
    private Date fecha;
    private Date fechaInicio;
    private Date fechaFin;
    private List<AgendadaAtendidasDto> reporteTurnos;
    private AgendadaAtendidasDto horaSeleccionada;
    private String validador;
    private Turno turno;

    private List<Turno> turnoList;

    @PostConstruct
    protected void init() {
        Usuario usuario = new Usuario();
        usuario = usuarioServicio.buscarPorCedula(getLoggedUser());
        registroMercantilId = usuario.getRegistroMercantil().getRegistroMercantilId();
        registroMercantil = new ArrayList<>();
        registroMercantil = registroMercantilServicio.obtenerRegistros(TipoEntidadEnum.RM.getTipo());
        reporteTurnos = new ArrayList<>();
        turnoList = new ArrayList<>();
        horaSeleccionada = new AgendadaAtendidasDto();
        //Turno turno = new Turno();
    }

    public void atenderTurno() {
        Short atendido = EstadoTurnoEnum.ATENDIDO.getEstado();
        Short agendado = EstadoTurnoEnum.AGENDADO.getEstado();
        turno = turnoServicio.buscarTurno(validador);

        if (buscarCiudadanosAtendidos(validador) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getBundleMensaje("sin.informacion", null), ""));
        } else if (buscarCiudadanosAtendidos(validador) == true) {
            if (turno.getEstado().equals(atendido)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Turno Atendido", "El Turno de: " + turno.getHora() + ", perteneciente al ciudadano " + turno.getNombre()
                        + ", con cédula: " + turno.getCedula() + ". Fue atendido."
                ));
            } else if (turno.getEstado().equals(agendado)) {
                if (new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(new SimpleDateFormat("yyyy-MM-dd").format(turno.getDia()))) {
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('informacionTurnoDlg').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La fecha de atención es diferente a la fecha del turno agendado", ""));
                }
            }
        }
    }

    public void onRowSelectHora() {
        turnoList = turnoServicio.getTurnos(registroMercantilId, fecha, horaSeleccionada.getHora());
    }

    public void ciudadanoAtendido() {
        try {
            Short atendido = EstadoTurnoEnum.ATENDIDO.getEstado();
            Short agendado = EstadoTurnoEnum.AGENDADO.getEstado();

            if (buscarCiudadanosAtendidos(validador) == false) {
                String mensaje = getBundleMensaje("sin.informacion", null);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));

            } else if (buscarCiudadanosAtendidos(validador) == true) {
                System.out.println("obj" + turno.getEstado());
                if (turno.getEstado() == atendido) {
                    String mensaje = getBundleMensaje("error.atendido", null);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
                }
                if (turno.getEstado() == agendado) {
                    if (fechaEstadoAtendido(turno.getDia()) == true) {
                        turno.setEstado(atendido);
                        turno.setAtendidoPor(getLoggedUser());
                        if (turnoServicio.actualizarAtendido(turno) == true) {
                            String mensaje = getBundleMensaje("ciudadano.atendido", null);
                            addInfoMessage(mensaje, null);
                            turnoList = turnoServicio.getTurnos(registroMercantilId, fecha, horaSeleccionada.getHora());
                            consultar();
                            PrimeFaces current = PrimeFaces.current();
                            current.executeScript("PF('informacionTurnoDlg').hide();");
                        } else {
                            String mensaje = getBundleMensaje("error.actualizar.ciudadano", null);
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
                        }
                    } else {

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "La fecha de atención es diferente a la fecha del turno agendado", ""));
                    }
                }
            }

        } catch (Exception e) {
            String mensaje = getBundleMensaje("sin.informacion", null);
            addErrorMessage(null, mensaje, null);
            e.printStackTrace();

        }

    }

    public List<RegistroMercantil> getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(List<RegistroMercantil> registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

    public Integer getRegistroMercantilId() {
        return registroMercantilId;
    }

    public void setRegistroMercantilId(Integer registroMercantilId) {
        this.registroMercantilId = registroMercantilId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<AgendadaAtendidasDto> getReporteTurnos() {
        return reporteTurnos;
    }

    public void setReporteTurnos(List<AgendadaAtendidasDto> reporteTurnos) {
        this.reporteTurnos = reporteTurnos;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    //////// funciones
    public Date getFechaInicio() {

        Calendar c = Calendar.getInstance();
        /// calcula la fecha actual fechaInicio = c.getTime();
        int anio = c.get(Calendar.YEAR);

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String year = String.valueOf(anio);
            String strFecha = year + "-01-01";
            fechaInicio = formato.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        Calendar c = Calendar.getInstance();
        /// calcula la fecha actual fechaInicio = c.getTime();
        int anio = c.get(Calendar.YEAR);

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String periodo = String.valueOf(anio);
            String strFecha = periodo + "-12-31";
            fechaFin = formato.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void limpiar() {
        reporteTurnos = new ArrayList<AgendadaAtendidasDto>();

    }

    public void mensaje() {
        addInfoMessage("hola", "dadfd");

    }

    public void consultar() {
        try {
            reporteTurnos = turnoDao.reporteAgendamiento(registroMercantilId,
                    new SimpleDateFormat("yyyy-MM-dd").format(fecha), EstadoTurnoEnum.AGENDADO.getEstado(),
                    EstadoTurnoEnum.ATENDIDO.getEstado());
            System.out.println("tamanio lista" + reporteTurnos.size());

        } catch (Exception e) {
            // String mensaje = getBundleMensaje("sin.informacion", null);
            // addErrorMessage(null, mensaje, null);
            e.printStackTrace();

        }

    }

    public boolean buscarCiudadanosAtendidos(String validador) {
        try {
            turno = turnoServicio.buscarTurno(validador);
            if (turno == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    // Solo puede marcar como atendido si la fecha es la del día de atención
    public boolean fechaEstadoAtendido(Date fechaAtencion) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        /// calcula la fecha actual fechaInicio = c.getTime();
        Date fechaActual = c.getTime();
        String strFecha = formato.format(fechaActual);
        try {
            fechaActual = formato.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (fechaActual.equals(fechaAtencion)) {
            return true;
        } else {
            return false;
        }

    }

    public void getInfoTurno() {

    }

    //Getters & Setters
    public AgendadaAtendidasDto getHoraSeleccionada() {
        return horaSeleccionada;
    }

    public void setHoraSeleccionada(AgendadaAtendidasDto horaSeleccionada) {
        this.horaSeleccionada = horaSeleccionada;
    }

    public List<Turno> getTurnoList() {
        return turnoList;
    }

    public void setTurnoList(List<Turno> turnoList) {
        this.turnoList = turnoList;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

}
