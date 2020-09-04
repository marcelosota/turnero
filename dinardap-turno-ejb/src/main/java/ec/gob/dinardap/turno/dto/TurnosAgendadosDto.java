package ec.gob.dinardap.turno.dto;

public class TurnosAgendadosDto {

	private Long cantidad;
	private String institucion;
	
	public TurnosAgendadosDto(Long cantidad, String institucion) {
		super();
		this.cantidad = cantidad;
		this.institucion = institucion;
	}
	
	public TurnosAgendadosDto() {
		// TODO Auto-generated constructor stub
	}

	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
}
