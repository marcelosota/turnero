package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;

@Local
public interface PlanificacionRegistroServicio extends GenericService<PlanificacionRegistro, Integer> {

    public PlanificacionRegistro getPlanificacionRegistro(Integer registroMercantilId);

}
