package ec.gob.dinardap.turno.constante;


public enum TipoEntidadEnum {	

		RM((short) 1), DINARDAP((short) 0);
		private Short tipo;

		private TipoEntidadEnum(Short tipo) {
			this.tipo = tipo;
		}

		public Short getTipo() {
			return tipo;
		}

		public void setTipo(Short tipo) {
			this.tipo = tipo;
		}
		
		public static TipoEntidadEnum obtenerTipoPorCodigo(Short tipo) {
			TipoEntidadEnum valor = null;
			for (TipoEntidadEnum e : TipoEntidadEnum.values()) {
				if (e.getTipo() == tipo) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}
