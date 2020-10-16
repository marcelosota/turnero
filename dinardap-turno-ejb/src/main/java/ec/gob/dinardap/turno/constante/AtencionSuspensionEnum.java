package ec.gob.dinardap.turno.constante;


	public enum AtencionSuspensionEnum {

		ATENCION((short) 1), SUSPENSION((short) 2);
		
		private Short atencionSuspension;

		private AtencionSuspensionEnum(Short atencionSuspension) {
			this.atencionSuspension = atencionSuspension;
		}

		public Short getatencionSuspension() {
			return atencionSuspension;
		}

		public void setatencionSuspension(Short atencionSuspension) {
			this.atencionSuspension = atencionSuspension;
		}
		
		public static AtencionSuspensionEnum obtenerAtencionSuspensionPorCodigo(Short atencionSuspension) {
			AtencionSuspensionEnum valor = null;
			for (AtencionSuspensionEnum e : AtencionSuspensionEnum.values()) {
				if (e.getatencionSuspension() == atencionSuspension) {
					valor = e;
					break;
				}
			}
			return valor;
		}
	}

