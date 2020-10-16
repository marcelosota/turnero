package ec.gob.dinardap.turno.servicio;

import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.TipoAtencion;

@Local
public interface TipoAtencionServicio extends GenericService<TipoAtencion, Short> {

	public void guardarTipoAtencion(TipoAtencion tipoAtencion);
	public List<TipoAtencion> obtenerTipoAtencionPorEstado(Short estado);
}
