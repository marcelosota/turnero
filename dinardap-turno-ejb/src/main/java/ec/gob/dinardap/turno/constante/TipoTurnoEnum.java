package ec.gob.dinardap.turno.constante;


public enum TipoTurnoEnum {	

		RM((short) 1), DINARDAP((short) 0);
		private Short tipo;

		private TipoTurnoEnum(Short tipo) {
			this.tipo = tipo;
		}

		public Short getTipo() {
			return tipo;
		}

		public void setTipo(Short tipo) {
			this.tipo = tipo;
		}
		
		public static TipoTurnoEnum obtenerTipoPorCodigo(Short tipo) {
			TipoTurnoEnum valor = null;
			for (TipoTurnoEnum e : TipoTurnoEnum.values()) {
				if (e.getTipo() == tipo) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}
