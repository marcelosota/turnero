package ec.gob.dinardap.turno.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.dinardap.interoperadorv2.cliente.servicio.ServicioDINARDAP;
import ec.gob.dinardap.interoperadorv2.ws.ConsultarResponse;

@Named(value = "pruebaCtrl")
@ViewScoped
public class PruebaCtrl extends BaseCtrl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cedula;

	public void consultar() {
		ServicioDINARDAP ob = new ServicioDINARDAP();
		ConsultarResponse objWs;
		objWs = ob.obtenerDatosInteroperabilidad(getCedula(), "2639");
		if (objWs != null) {
			String valor = objWs.getPaquete().getEntidades().getEntidad().get(0).getFilas().getFila().get(0)
					.getColumnas().getColumna().get(3).getValor();
			System.out.println(valor);
		}
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}
