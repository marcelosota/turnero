package ec.gob.dinardap.turno.servicio;

import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.TipoVentanilla;

@Local
public interface TipoVentanillaServicio extends GenericService<TipoVentanilla, Integer> {

	public List<TipoVentanilla> obtenerTipoVentanillaPorEstado(Short estado);
	public void guardarTipoVentanilla(TipoVentanilla tipoVentanilla);
}
