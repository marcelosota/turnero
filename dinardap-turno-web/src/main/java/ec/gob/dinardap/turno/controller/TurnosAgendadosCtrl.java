package ec.gob.dinardap.turno.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

import ec.gob.dinardap.turno.constante.TipoEntidadEnum;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import ec.gob.dinardap.turno.servicio.TurnoServicio;

@Named("turnosAgendadosCtrl")
@ViewScoped
public class TurnosAgendadosCtrl extends BaseCtrl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3284549859204851975L;

	@EJB
	private RegistroMercantilServicio registroMercantilServicio;
	
	@EJB
	private TurnoServicio turnoServicio; 
	
	private List<Turno> turno;
	private List<Turno> filtro;
	private Date fechaDesde;
	private Date fechaHasta;
	private Date maxima;
	private Date minima;
	private List<RegistroMercantil> registroMercantil;
	private RegistroMercantil registroSeleccionado;
	private Integer registroMercantilId;
	
	@PostConstruct
	protected void init() {
		registroMercantil = new ArrayList<RegistroMercantil>();
		if(Short.parseShort(getHttpServletRequest().getSession().getAttribute("tipoEntidad").toString()) == TipoEntidadEnum.RM.getTipo()) {
			registroMercantil.add(registroMercantilServicio.findByPk(Integer.parseInt(getHttpServletRequest().getSession().getAttribute("institucion").toString())));
		}else
			setRegistroMercantil(registroMercantilServicio.obtenerRegistros(TipoEntidadEnum.RM.getTipo()));
		maxima = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 4, 25);
		minima = cal.getTime();
	}
	
	public void buscar() {
		turno = new ArrayList<Turno>();
		registroSeleccionado = registroMercantilServicio.findByPk(getRegistroMercantilId());
		//if(turno != null)
		//	turno.clear();
		if(getFechaDesde() != null && getFechaHasta() != null && getRegistroMercantilId() != null && getFechaDesde().compareTo(getFechaHasta()) <= 0)
			turno = turnoServicio.turnosAgendados(getRegistroMercantilId(), getFechaDesde(), getFechaHasta());
		
	}

	public List<Turno> getTurno() {
		return turno;
	}
	
	public void preProcessPDF(Object document) {
		Document doc = (Document) document;
		doc.setPageSize(PageSize.A4.rotate());
	}

	public void setTurno(List<Turno> turno) {
		this.turno = turno;
	}

	public List<Turno> getfiltro() {
		return filtro;
	}

	public void setfiltro(List<Turno> filtro) {
		this.filtro = filtro;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Date getMaxima() {
		return maxima;
	}

	public void setMaxima(Date maxima) {
		this.maxima = maxima;
	}

	public Date getMinima() {
		return minima;
	}

	public void setMinima(Date minima) {
		this.minima = minima;
	}

	public List<RegistroMercantil> getRegistroMercantil() {
		return registroMercantil;
	}

	public void setRegistroMercantil(List<RegistroMercantil> registroMercantil) {
		this.registroMercantil = registroMercantil;
	}

	public RegistroMercantil getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(RegistroMercantil registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public Integer getRegistroMercantilId() {
		return registroMercantilId;
	}

	public void setRegistroMercantilId(Integer registroMercantilId) {
		this.registroMercantilId = registroMercantilId;
	}

}
