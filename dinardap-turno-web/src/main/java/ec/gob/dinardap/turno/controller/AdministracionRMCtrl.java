package ec.gob.dinardap.turno.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.turno.constante.EstadoTurnoEnum;
import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.dto.AgendadaAtendidasDto;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TurnoServicio;

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

	private List<RegistroMercantil> registroMercantil;
	private Integer registroMercantilId;
	private Date fecha;
	private Date fechaInicio;
	private Date fechaFin;
	private List<AgendadaAtendidasDto> reporteTurnos;
	private String validador;
	private Turno turno;

	@PostConstruct
	protected void init() {
		registroMercantil = new ArrayList<>();
		registroMercantil = registroMercantilServicio.obtenerRegistros(TipoEntidadEnum.RM.getTipo());
		reporteTurnos = new ArrayList<>();
		Turno turno = new Turno();
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
			if (turno == null)
				return false;
			else
				return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void ciudadanoAtendido() {
		try {
			Short atendido = EstadoTurnoEnum.ATENDIDO.getEstado();
			Short agendado = EstadoTurnoEnum.AGENDADO.getEstado();
			
			if (buscarCiudadanosAtendidos(validador) == false) {
				String mensaje = getBundleMensaje("sin.informacion", null);
				addErrorMessage(null, mensaje, null);

			} else if (buscarCiudadanosAtendidos(validador) == true) {
				System.out.println("obj" + turno.getEstado());
				if (turno.getEstado() == atendido) {
					String mensaje = getBundleMensaje("error.atendido", null);
					addErrorMessage(null, mensaje, null);
				}
				if (turno.getEstado() == agendado) {
					turno.setEstado(atendido);
					if (turnoServicio.actualizarAtendido(turno) == true) {
						String mensaje = getBundleMensaje("ciudadano.atendido", null);
						addInfoMessage(mensaje, null);
					} else {
						String mensaje = getBundleMensaje("error.actualizar.ciudadano", null);
						addErrorMessage(null, mensaje, null);
					}
				}
			}

		} catch (Exception e) {
			String mensaje = getBundleMensaje("sin.informacion", null);
			addErrorMessage(null, mensaje, null);
			e.printStackTrace();

		}

	}
}
