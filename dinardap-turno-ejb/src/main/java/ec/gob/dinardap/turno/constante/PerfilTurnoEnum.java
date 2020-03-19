package ec.gob.dinardap.turno.constante;

public enum PerfilTurnoEnum {
	
	DINARDAP("ControlDinardap"), RM("RegistroMercantil");
	
	private String perfil;

	private PerfilTurnoEnum(String perfil) {
		this.perfil = perfil;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
}
