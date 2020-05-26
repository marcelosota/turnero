package ec.gob.dinardap.turno.dto;

import java.util.Date;

public class AgendadaAtendidasDto {
	private Integer registroMercantilId;
	private Date fecha;
	private String hora;
	private String agendada;
	private String atendido;
	private String pendiente;
	
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
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getAgendada() {
		return agendada;
	}
	public void setAgendada(String agendada) {
		this.agendada = agendada;
	}
	public String getAtendido() {
		return atendido;
	}
	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}
	public String getPendiente() {
		return pendiente;
	}
	public void setPendiente(String pendiente) {
		this.pendiente = pendiente;
	}
	
	
	
	
	
}
