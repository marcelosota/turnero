package ec.gob.dinardap.turno.constante;


	public enum EstadoTurnoEnum {

		AGENDADO((short) 1), ATENDIDO((short) 2), CANCELADO((short) 3);
		
		private Short estado;

		private EstadoTurnoEnum(Short estado) {
			this.estado = estado;
		}

		public Short getEstado() {
			return estado;
		}

		public void setEstado(Short estado) {
			this.estado = estado;
		}
		
		public static EstadoTurnoEnum obtenerEstadoPorCodigo(Short estado) {
			EstadoTurnoEnum valor = null;
			for (EstadoTurnoEnum e : EstadoTurnoEnum.values()) {
				if (e.getEstado() == estado) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}

