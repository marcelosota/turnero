package ec.gob.dinardap.turno.servicio;

import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Atencion;

@Local
public interface AtencionServicio extends GenericService<Atencion, Integer> {
	
	public List<Atencion> obtenerVacacionesPorIstitucion(Integer registroMercantilId);

}
