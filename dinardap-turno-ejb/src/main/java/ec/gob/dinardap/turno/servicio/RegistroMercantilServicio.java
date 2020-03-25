package ec.gob.dinardap.turno.servicio;

import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;

@Local
public interface RegistroMercantilServicio extends GenericService<RegistroMercantil, Integer> {

    public List<RegistroMercantil> obtenerRegistrosMercantiles();

    public List<RegistroMercantil> obtenerRegistros(Short tipo);

    public List<RegistroMercantil> getRegistrosMercantiles();

}
