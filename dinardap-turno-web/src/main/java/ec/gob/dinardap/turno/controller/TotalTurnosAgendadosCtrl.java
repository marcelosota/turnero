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
	private Date minimaF;
	private Date minima;
	private boolean flag;
	
	@PostConstruct
	protected void init() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 4, 25);
		minima = cal.getTime();
		minimaF = cal.getTime();
		//fechaDesde = cal.getTime();
		//fechaHasta = new Date();
		setFlag(false);
	}
	
	public void buscar() {
		turno = new ArrayList<TurnosAgendadosDto>();
		if(!isFlag())
			turno = turnoDao.totalTurnos(null, null);
		else {
			if(getFechaDesde() != null && getFechaHasta() != null && getFechaDesde().compareTo(getFechaHasta()) <= 0)
				turno = turnoDao.totalTurnos(getFechaDesde(), getFechaHasta());
		}
	}
	
	public void validarFecha() {
		if(getFechaHasta() != null && getFechaDesde().compareTo(getFechaHasta()) > 0)
			fechaHasta = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaDesde);
		minimaF = null;
		minimaF = cal.getTime();
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

	public Date getMinimaF() {
		return minimaF;
	}

	public void setMinimaF(Date minimaF) {
		this.minimaF = minimaF;
	}

	public Date getMinima() {
		return minima;
	}

	public void setMinima(Date minima) {
		this.minima = minima;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
