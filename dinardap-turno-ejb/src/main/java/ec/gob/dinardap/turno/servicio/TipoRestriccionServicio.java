package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import java.util.List;

@Local
public interface TipoRestriccionServicio extends GenericService<TipoRestriccion, Integer> {

    public List<TipoRestriccion> getTipoRestriccionList();

}
