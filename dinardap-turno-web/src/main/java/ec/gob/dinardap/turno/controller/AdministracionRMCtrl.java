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

import ec.gob.dinardap.turno.constante.TipoTurnoEnum;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;

@Named(value = "administracionRMCtrl")
@ViewScoped
public class AdministracionRMCtrl extends BaseCtrl {

	private static final long serialVersionUID = 2441633867566660777L;

	@EJB
	private RegistroMercantilServicio registroMercantilServicio;

	private List<RegistroMercantil> registroMercantil;
	private Integer registroMercantilId;
	private Date fecha;
	private Date fechaInicio;
	private Date fechaFin;

	@PostConstruct
	protected void init() {
		registroMercantil = new ArrayList<RegistroMercantil>();		
		registroMercantil = registroMercantilServicio.obtenerRegistros(TipoTurnoEnum.RM.getTipo());

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
		// resumenBeneficio = new ArrayList<>();

	}

	public void consultar() {

	}
}
