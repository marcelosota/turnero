package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import java.util.List;

@Local
public interface RegistroMercantilServicio extends GenericService<RegistroMercantil, Integer> {

    public List<RegistroMercantil> getRegistrosMercantiles();

}
