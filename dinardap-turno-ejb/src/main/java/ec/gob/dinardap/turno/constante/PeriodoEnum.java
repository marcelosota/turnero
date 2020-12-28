package ec.gob.dinardap.turno.constante;


public enum PeriodoEnum {	

		MENSUAL((short) 1), TRIMESTRAL((short) 3), SEMESTRAL((short) 6), ANUAL((short) 12);
		private Short periodo;

		private PeriodoEnum(Short periodo) {
			this.periodo = periodo;
		}

		public Short getPeriodo() {
			return periodo;
		}

		public void setPeriodo(Short periodo) {
			this.periodo = periodo;
		}
		
		public static PeriodoEnum obtenerPeriodoPorCodigo(Short periodo) {
			PeriodoEnum valor = null;
			for (PeriodoEnum e : PeriodoEnum.values()) {
				if (e.getPeriodo() == periodo) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}
