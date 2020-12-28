package ec.gob.dinardap.turno.constante;


	public enum TipoAtencionEnum {

		FERIADO_NACIONAL((short) 1), FERIADO_LOCAL((short) 2), 
		RECUPERACION_NACIONAL((short) 3), RECUPERACION_LOCAL((short) 4), 
		SUSPENSION_TEMPORAL_NACIONAL((short) 5), SUSPENSION_TEMPORAL_LOCAL((short) 6);
		
		private Short tipoAtencion;

		private TipoAtencionEnum(Short tipoAtencion) {
			this.tipoAtencion = tipoAtencion;
		}

		public Short getTipoAtencion() {
			return tipoAtencion;
		}

		public void setTipoAtencion(Short tipoAtencion) {
			this.tipoAtencion = tipoAtencion;
		}
		
		public static TipoAtencionEnum obtenerTipoAtencionPorCodigo(Short tipoAtencion) {
			TipoAtencionEnum valor = null;
			for (TipoAtencionEnum e : TipoAtencionEnum.values()) {
				if (e.getTipoAtencion() == tipoAtencion) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}

