package ec.gob.dinardap.turno.constante;

public enum InteroperabilidadEnum {
	RC("2901");
	
	private String paquete;

	private InteroperabilidadEnum(String paquete) {
		this.paquete = paquete;
	}

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}
	
	public static InteroperabilidadEnum obtenerPaquetePorCodigo(String paquete) {
		InteroperabilidadEnum valor = null;
		for(InteroperabilidadEnum e : InteroperabilidadEnum.values()) {
			if(e.getPaquete() == paquete) {
				valor = e;
				break;
			}
		}
		return valor;
	}

}
