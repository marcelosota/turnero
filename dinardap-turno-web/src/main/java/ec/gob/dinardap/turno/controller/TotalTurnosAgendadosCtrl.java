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

import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.dto.TurnosAgendadosDto;

@Named("totalTurnosAgendadosCtrl")
@ViewScoped
public class TotalTurnosAgendadosCtrl extends BaseCtrl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3284549859204851975L;

	@EJB
	private TurnoDao turnoDao; 
	
	private List<TurnosAgendadosDto> turno;
	private Date fechaDesde;
	private Date fechaHasta;
	private Date maxima;
	private Date minima;
	
	@PostConstruct
	protected void init() {
		maxima = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 4, 25);
		minima = cal.getTime();
	}
	
	public void buscar() {
		turno = new ArrayList<TurnosAgendadosDto>();
		if(getFechaDesde() != null && getFechaHasta() != null && getFechaDesde().compareTo(getFechaHasta()) <= 0)
			turno = turnoDao.totalTurnos(getFechaDesde(), getFechaHasta());
		
	}

	public void preProcessPDF(Object document) {
		Document doc = (Document) document;
		doc.setPageSize(PageSize.A4.rotate());
	}
	
	public List<TurnosAgendadosDto> getTurno() {
		return turno;
	}

	public void setTurno(List<TurnosAgendadosDto> turno) {
		this.turno = turno;
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

}
