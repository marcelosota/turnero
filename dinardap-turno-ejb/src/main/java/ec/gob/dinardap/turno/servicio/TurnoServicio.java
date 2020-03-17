package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Turno;

@Local
public interface TurnoServicio extends GenericService<Turno, Integer> {

}
